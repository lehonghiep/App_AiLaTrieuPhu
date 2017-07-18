package com.honghiep.ailatrieuphu.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.honghiep.ailatrieuphu.models.Question;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ducnd on 7/10/17.
 */

public class ManagerAiLaTrieuPhu {
    private static final String TAG = ManagerAiLaTrieuPhu.class.getSimpleName();
    private static final String DATA_NAME = "Question";

    private String pathRoot;
    private Context mContext;

    private SQLiteDatabase managerSql;

    public ManagerAiLaTrieuPhu(Context context) {
        //cach lay duong dan external app
        mContext = context;
        pathRoot = Environment.getDataDirectory() +
                File.separator +
                "data" + File.separator +
                context.getPackageName() +
                File.separator + "database";
        inits();

    }

    private void openDatabase() {
        if (managerSql == null ||
                !managerSql.isOpen()) {
            managerSql = SQLiteDatabase
                    .openDatabase(pathRoot
                                    + File.separator +
                                    DATA_NAME, null,
                            SQLiteDatabase.OPEN_READWRITE);
        }

    }

    private void closeDatabse() {
        if (managerSql != null && managerSql.isOpen()) {
            managerSql.close();
        }
    }


    private void inits() {
        File file = new File(pathRoot + File.separator
                + DATA_NAME);
        if (file.exists()) {
            return;
        }
        file.getParentFile().mkdir();
        try {
            InputStream in = mContext.getAssets()
                    .open(DATA_NAME);
            OutputStream out =
                    new FileOutputStream(file);
            byte b[] = new byte[1024];
            int le = in.read(b);
            while (le >= 0) {
                out.write(b, 0, le);
                le = in.read(b);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public List<Question> get15Question() {
        List<Question> questions = new ArrayList<>();
        openDatabase();

        String sql = "SELECT * FROM ( SELECT * FROM Question ORDER BY RANDOM() ) GROUP BY level ORDER BY level ASC";
        String[] sle = new String[]{
                "level", "id", "ra"
        };
        Cursor cursor =
                managerSql.rawQuery(sql, null);
        if (cursor == null) {
            return null;
        }
        String name[] = cursor.getColumnNames();
        for (String s : name) {
            Log.d(TAG, "name: " + s);
        }

        int indexAsk = cursor.getColumnIndex("question");
        int indexRa = cursor.getColumnIndex("casea");
        int indexRb = cursor.getColumnIndex("caseb");
        int indexRc = cursor.getColumnIndex("casec");
        int indexRd = cursor.getColumnIndex("cased");
        int indexlevel = cursor.getColumnIndex("level");
        int indexTruecase = cursor.getColumnIndex("truecase");
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String ask = cursor.getString(indexAsk);
            String ra = cursor.getString(indexRa);
            String rb = cursor.getString(indexRb);
            String rc = cursor.getString(indexRc);
            String rd = cursor.getString(indexRd);
            int level = cursor.getInt(indexlevel);
            int truecase = cursor.getInt(indexTruecase);
            Log.d(TAG, "ask: " + ask);
            Log.d(TAG, "ra: " + ra);
            Log.d(TAG, "rb: " + rb);
            Log.d(TAG, "rc: " + rc);
            Log.d(TAG, "rd: " + rd);
            Log.d(TAG, "level: " + level);
            Log.d(TAG, "truecase: " + truecase);
            Log.d(TAG, "=================");
            questions.add(new Question(level, ask, ra, rb, rc, rd, truecase));
            cursor.move(1);
        }


        closeDatabse();
        return questions;
    }

    public void insertUser(String name, int level, String money) {
        openDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("level", level);
        values.put("money", money);
        long indexInsert = managerSql.insert("user", null, values);
        Log.d(TAG, "insertUser index: " + indexInsert);

        closeDatabse();
    }

    public void updateUser(String name, String money) {
        openDatabase();


        closeDatabse();
    }

}
