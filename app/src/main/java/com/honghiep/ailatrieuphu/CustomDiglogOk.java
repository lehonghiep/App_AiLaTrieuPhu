package com.honghiep.ailatrieuphu;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by honghiep on 17/07/2017.
 */

public class CustomDiglogOk extends Dialog implements View.OnClickListener {
    private TextView tv_notify;
    private ICustomDiglogOk mInterf;

    public CustomDiglogOk(@NonNull Context context, @StyleRes int themeResId, ICustomDiglogOk mInterf) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mInterf = mInterf;
        setContentView(R.layout.custom_dialog_ok);
        tv_notify = findViewById(R.id.tv_notify);
        findViewById(R.id.btn_ok).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        dismiss();
        mInterf.onClickOk();
    }
    public void setTextNotify(String notify){
        tv_notify.setText(notify);
    }

    public interface ICustomDiglogOk {
        void onClickOk();
    }
}
