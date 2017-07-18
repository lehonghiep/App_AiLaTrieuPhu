package com.honghiep.ailatrieuphu;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by honghiep on 17/07/2017.
 */

public class CustomDialogYesNo extends Dialog implements View.OnClickListener {
    private ICustomDialogYesNo mInterf;
    private TextView tv_notify;

    public CustomDialogYesNo(@NonNull Context context, @StyleRes int themeResId,ICustomDialogYesNo iCustomDialogYesNo) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_yes_no);
        mInterf = iCustomDialogYesNo;
        tv_notify=findViewById(R.id.tv_notify);
        findViewById(R.id.btn_agree).setOnClickListener(this);
        findViewById(R.id.btn_disagree).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        dismiss();
        if (view.getId() == R.id.btn_agree) {
            mInterf.onClickAgree();
        }
    }
    public void setTextNotify(String notify){
        tv_notify.setText(notify);
    }

    public interface ICustomDialogYesNo {
        void onClickAgree();
    }
}
