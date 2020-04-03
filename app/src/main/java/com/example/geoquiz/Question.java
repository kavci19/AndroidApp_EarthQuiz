package com.example.geoquiz;

public class Question {

    private int mTextResID;
    private boolean mAnswerTrue;

    public Question(int TextResID, boolean AnswerTrue) {

        mAnswerTrue = AnswerTrue;
        mTextResID = TextResID;


    }

    public int getTextResID() {
        return mTextResID;
    }

    public void setTextResID(int textResID) {
        mTextResID = textResID;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
