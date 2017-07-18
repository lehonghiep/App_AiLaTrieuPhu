package com.honghiep.ailatrieuphu;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

/**
 * Created by honghiep on 16/07/2017.
 */

public class CustomDialogIsReady extends Dialog implements View.OnClickListener{
    private ICustomDialogIsReady mInterf;
    public CustomDialogIsReady(@NonNull Context context){
        super(context);
        inits();
        this.mInterf= (ICustomDialogIsReady) context;
    }
    public CustomDialogIsReady(@NonNull Context context, int themResId, ICustomDialogIsReady mInterf) {
        super(context);
        inits();
        this.mInterf=mInterf;
    }
    private void inits(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_isready);

        findViewById(R.id.btn_ready).setOnClickListener(this);
        findViewById(R.id.btn_notready).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if(v.getId()==R.id.btn_ready){
            mInterf.onClickOkReady();
        }
    }
    public interface ICustomDialogIsReady {
        void onClickOkReady();
    }
}
