package com.honghiep.ailatrieuphu;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;

/**
 * Created by honghiep on 20/07/2017.
 */

public class CustomDialogGoiNguoiThan extends Dialog implements View.OnClickListener {
    ICustomDialogGoiNguoiThan mInterf;
    public CustomDialogGoiNguoiThan(@NonNull Context context, @StyleRes int themeResId,ICustomDialogGoiNguoiThan mInterf) {
        super(context,themeResId);
        this.mInterf=mInterf;
        setContentView(R.layout.custom_dialog_goinguoithan);
        findViewById(R.id.iv_ngobaochau).setOnClickListener(this);
        findViewById(R.id.iv_anhxtanh).setOnClickListener(this);
        findViewById(R.id.iv_levanlan).setOnClickListener(this);
        findViewById(R.id.iv_khongminh).setOnClickListener(this);
        findViewById(R.id.iv_billgate).setOnClickListener(this);
        findViewById(R.id.iv_markzukerbeg).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        dismiss();
        mInterf.onClickFriends(view);
    }

    public interface ICustomDialogGoiNguoiThan{
        void onClickFriends(View view);
    }
}
