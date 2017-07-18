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
}

