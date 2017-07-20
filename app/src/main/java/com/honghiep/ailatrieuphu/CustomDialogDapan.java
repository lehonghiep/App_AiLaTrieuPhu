package com.honghiep.ailatrieuphu;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.TextView;

import com.honghiep.ailatrieuphu.R;

/**
 * Created by honghiep on 20/07/2017.
 */

public class CustomDialogDapan extends Dialog implements View.OnClickListener {
    private TextView tv_notify;
    public CustomDialogDapan(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.custom_dialog_dapan);
        tv_notify=findViewById(R.id.tv_notify);
        findViewById(R.id.btn_back).setOnClickListener(this);
    }
    public void setTextNotify(String notify){
        tv_notify.setText("Theo tôi đáp án là: " +notify);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
