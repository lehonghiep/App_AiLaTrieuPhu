package com.honghiep.ailatrieuphu;

import android.view.View;

import com.honghiep.ailatrieuphu.models.Question;

/**
 * Created by honghiep on 15/07/2017.
 */

public interface IQuestions {
    Question getData();
    int countQuestion();
    void openPlay();
    boolean getClickButtonFifty();
    boolean getClickButtonKhanGia();
    boolean getClickButtonSwith();
    boolean getClickButtonCall();
    void setClickButtonFifty(boolean isClickButtonFifty);
    void setClickButtonKhanGia(boolean clickButtonKhanGia);
    void setClickButtonSwith(boolean isClickButtonSwith);
    void setClickButtonCall(boolean isClickButtonCall);
}

