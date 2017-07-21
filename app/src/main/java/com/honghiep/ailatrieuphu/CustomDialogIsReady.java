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
    private Context context;
    public CustomDialogIsReady(@NonNull Context context){
        super(context);
        inits();
        this.mInterf= (ICustomDialogIsReady) context;
        this.context=context;
    }
    public CustomDialogIsReady(@NonNull Context context, int themResId, ICustomDialogIsReady mInterf) {
        super(context, themResId);
        inits();
        this.mInterf=mInterf;
        this.context=context;
    }
    private void inits(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_isready);

        findViewById(R.id.btn_ready).setOnClickListener(this);
        findViewById(R.id.btn_notready).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SounManager.destroySound();
        dismiss();
        if(v.getId()==R.id.btn_ready){
            mInterf.onClickOkReady();
        }else {
            SounManager.playSoundBackGroud(context);
        }
    }
    public interface ICustomDialogIsReady {
        void onClickOkReady();
    }

    @Override
    public void show() {
        super.show();
        SounManager.playSoundIsReady(context);
    }
}
