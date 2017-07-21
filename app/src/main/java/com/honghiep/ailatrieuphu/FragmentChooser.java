package com.honghiep.ailatrieuphu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.honghiep.ailatrieuphu.models.Question;

/**
 * Created by honghiep on 15/07/2017.
 */

public class FragmentChooser extends Fragment implements View.OnClickListener {
    private IFragmentChooser mInterf;
    private Animation anim_btn_choigame, anim_btn_diem_cao, anim_btn_huong_dan;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mInterf = (IFragmentChooser) context;
        anim_btn_choigame = AnimationUtils.loadAnimation(context, R.anim.animation_btn_choigame);
        anim_btn_diem_cao = AnimationUtils.loadAnimation(context, R.anim.animation_btn_diemcao);
        anim_btn_huong_dan = AnimationUtils.loadAnimation(context, R.anim.animation_btn_huongdan);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chooser, container, false);
        Button btn_choigame = view.findViewById(R.id.btn_choigame);
        btn_choigame.startAnimation(anim_btn_choigame);
        btn_choigame.setOnClickListener(this);
        Button btn_diemcao = view.findViewById(R.id.btn_diemcao);
        btn_diemcao.startAnimation(anim_btn_diem_cao);
        btn_diemcao.setOnClickListener(this);
        Button btn_huongdan = view.findViewById(R.id.btn_huongdan);
        btn_huongdan.startAnimation(anim_btn_huong_dan);
        btn_huongdan.setOnClickListener(this);
        SounManager.playSoundBackGroud(context);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choigame:
                mInterf.onClickPlayGame();
                break;
            case R.id.btn_diemcao:
                break;
            case R.id.btn_huongdan:
                break;
            default:
                break;
        }
    }

    public interface IFragmentChooser {
        void onClickPlayGame();
    }
}
