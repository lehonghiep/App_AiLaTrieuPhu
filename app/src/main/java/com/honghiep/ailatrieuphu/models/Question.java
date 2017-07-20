package com.honghiep.ailatrieuphu.models;

/**
 * Created by honghiep on 15/07/2017.
 */

public class Question {
    private int level;
    private String ask;
    private String ra;
    private String rb;
    private String rc;
    private String rd;
    private int trueCase;
    private int questionID;

    public Question(int level, String ask, String ra, String rb, String rc, String rd, int trueCase,int questionID) {
        this.level = level;
        this.ask = ask;
        this.ra = ra;
        this.rb = rb;
        this.rc = rc;
        this.rd = rd;
        this.trueCase = trueCase;
        this.questionID=questionID;
    }

    public int getLevel() {
        return level;
    }

    public String getAsk() {
        return ask;
    }

    public String getRa() {
        return ra;
    }

    public String getRb() {
        return rb;
    }

    public String getRc() {
        return rc;
    }

    public String getRd() {
        return rd;
    }

    public int getTrueCase() {
        return trueCase;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }
}
