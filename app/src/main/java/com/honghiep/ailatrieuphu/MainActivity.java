package com.honghiep.ailatrieuphu;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.honghiep.ailatrieuphu.managers.ManagerAiLaTrieuPhu;
import com.honghiep.ailatrieuphu.models.Question;

import java.util.List;

public class MainActivity extends FragmentActivity implements IQuestions, CustomDialogIsReady.ICustomDialogIsReady, FragmentChooser.IFragmentChooser {
    private List<Question> questions;
    private Fragment fragment;
    private FragmentManager manager;
    private boolean isClickFifty, isClickKhanGia, isClickCall, isClickSwith;
    private ManagerAiLaTrieuPhu managerAiLaTrieuPhu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openChooser();
    }

    public void inits() {
        isClickFifty = true;
        isClickKhanGia = true;
        isClickCall = true;
        isClickSwith = true;

        managerAiLaTrieuPhu = new ManagerAiLaTrieuPhu(this);
        questions = managerAiLaTrieuPhu.get15Question();
    }

    @Override
    public void onBackPressed() {
        manager = getSupportFragmentManager();
        fragment =
                manager.findFragmentByTag(FragmentChooser.class.getName());
        if (fragment != null && fragment.isVisible()) {
            CustomDialogYesNo dialogYesNo = new CustomDialogYesNo(this, R.style.StypeDialog, new CustomDialogYesNo.ICustomDialogYesNo() {
                @Override
                public void onClickAgree() {
                    SounManager.destroySound();
                    MainActivity.super.onBackPressed();
                }
            });
            dialogYesNo.setTextNotify("Bạn có muốn thoát chương trình không");
            dialogYesNo.show();
            return;
        }
        fragment = manager.findFragmentByTag(FragmentPlay.class.getName());
        if (fragment != null && fragment.isVisible()) {
            CustomDialogYesNo dialogYesNo = new CustomDialogYesNo(this, R.style.StypeDialog, new CustomDialogYesNo.ICustomDialogYesNo() {
                @Override
                public void onClickAgree() {
                    SounManager.playSoundLose(MainActivity.this);
                    destroyAsycnTastInFragmentPlay();
                }
            });
            dialogYesNo.setTextNotify("Bạn muốn dừng cuộc chơi");
            dialogYesNo.show();

        }
    }

    private void destroyAsycnTastInFragmentPlay() {
        manager = getSupportFragmentManager();
        fragment = manager.findFragmentByTag(FragmentPlay.class.getName());
        if (fragment != null && fragment.isVisible()) {
            FragmentPlay play = ((FragmentPlay) fragment);
            play.destroyTime();
            play.destroyAnimAnswer();
            play.notifyGameOver();
        }
    }

    public void openChooser() {
        manager = getSupportFragmentManager();
        //kiem tra fragment login da ton tai trong fragmentmanager chua
        fragment =
                manager.findFragmentByTag(FragmentChooser.class.getName());
        //if fragment == null thi fragment chua co trong fragmentmanager
        if (fragment != null) {
            if (fragment.isVisible()) {

            } else {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content, fragment, FragmentChooser.class.getName());
//                transaction.addToBackStack(FragmentChooser.class.getName());
                transaction.commit();
            }
            return;
        }

        //tao ra login fragment va them vao
        fragment = new FragmentChooser();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content, fragment, FragmentChooser.class.getName());
//        transaction.addToBackStack(FragmentChooser.class.getName());
        transaction.commit();
    }

    public void openPlay() {
        manager = getSupportFragmentManager();

        fragment = new FragmentPlay();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content, fragment, FragmentPlay.class.getName());
//        transaction.addToBackStack(FragmentPlay.class.getName());
        transaction.commit();
    }

    @Override
    public boolean getClickButtonFifty() {
        return isClickFifty;
    }

    @Override
    public boolean getClickButtonKhanGia() {
        return isClickKhanGia;
    }

    @Override
    public boolean getClickButtonSwith() {
        return isClickSwith;
    }

    @Override
    public boolean getClickButtonCall() {
        return isClickCall;
    }

    @Override
    public void setClickButtonFifty(boolean isClickButtonFifty) {
        this.isClickFifty = isClickButtonFifty;
    }

    @Override
    public void setClickButtonKhanGia(boolean clickButtonKhanGia) {
        this.isClickKhanGia = clickButtonKhanGia;
    }

    @Override
    public void setClickButtonSwith(boolean isClickButtonSwith) {
        this.isClickSwith = isClickButtonSwith;
    }

    @Override
    public void setClickButtonCall(boolean isClickButtonCall) {
        this.isClickCall = isClickButtonCall;
    }

    @Override
    public void changeQuestionByIdAndLevel(int level, int id) {
        questions.add(0,managerAiLaTrieuPhu.getRePlaceQuestion(level,id));
        manager = getSupportFragmentManager();
        fragment = manager.findFragmentByTag(FragmentPlay.class.getName());
        if (fragment != null && fragment.isVisible()) {
            FragmentPlay play = ((FragmentPlay) fragment);
            play.destroyTime();
            play.destroyAnimAnswer();
        }
        openPlay();
    }

    @Override
    public Question getData() {
        if (questions.isEmpty()) {
            return null;
        }
        return questions.remove(0);
    }

    @Override
    public int countQuestion() {
        return questions.size();
    }

    @Override
    public void onClickOkReady() {
        inits();
        openPlay();
    }

    @Override
    public void onClickPlayGame() {
        CustomDialogIsReady dialogIsReady=
        new CustomDialogIsReady(this, R.style.StypeDialog, this);
        dialogIsReady.setCancelable(false);
        dialogIsReady.show();
    }
}
