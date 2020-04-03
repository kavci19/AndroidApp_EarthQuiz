package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private static final String TAG = "MainActivity";

    //key IDs for storing bundle data in key, value pairs
    private static final String KEY_INDEX = "index";
    private String KEY_IS_CHEATER;
    private String KEY_BOOLEAN_ARRAY_CHEATED_QUESTIONS;

    private static final String IS_ANSWER_TRUE =  "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;





    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private boolean[] mQuestionsCheated = new boolean[mQuestionBank.length];




    public static Intent newIntent(Context packageContext, Boolean isAnswerTrue){

        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(IS_ANSWER_TRUE, isAnswerTrue);
        return i;


    }






    private void checkAnswer(boolean userPressedTrue){

        boolean actualAnswer = mQuestionBank[mCurrentIndex].isAnswerTrue();


        mIsCheater = mQuestionsCheated[mCurrentIndex];

        int messageResID = 0;

        if (mIsCheater) messageResID = R.string.judgment_toast;
        else {


            if (userPressedTrue == actualAnswer) {
                messageResID = R.string.correct_toast;
            } else {
                messageResID = R.string.incorrect_toast;
            }

        }

        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT)
                .show();

    }

    private void updateQuestion(String direction) {


        if (direction.equals("next")) mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
        else if (direction.equals("previous")) mCurrentIndex = (mCurrentIndex-1)%mQuestionBank.length;


        int question = mQuestionBank[mCurrentIndex].getTextResID();
        mQuestionTextView.setText(question);
        mIsCheater = false;

    }

    private void initQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResID();
        mQuestionTextView.setText(question);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState!=null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            //mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER);
            mQuestionsCheated = savedInstanceState.getBooleanArray(KEY_BOOLEAN_ARRAY_CHEATED_QUESTIONS);
            //mIsCheater = mQuestionsCheated[mCurrentIndex];
            Log.d(TAG, "in onCreate, retrieving savedInstanceState, user cheated at " + mCurrentIndex + " is " + mQuestionsCheated[mCurrentIndex]);
        }


        Log.d(TAG, "onCreate(Bundle) called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cheatIntent = newIntent(MainActivity.this, mQuestionBank[mCurrentIndex].isAnswerTrue());
                startActivityForResult(cheatIntent, REQUEST_CODE_CHEAT);

            }
        });


        mQuestionTextView = findViewById(R.id.question_text_view);
        initQuestion();
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion("next");
            }
        });



        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mIsCheater = false;
                updateQuestion("next");
            }

        });





        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });


        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });


        mPrevButton = findViewById(R.id.previous_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion("previous");
            }
        });




    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState: saving cheated =" + mIsCheater);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_IS_CHEATER, mIsCheater);
        savedInstanceState.putBooleanArray(KEY_BOOLEAN_ARRAY_CHEATED_QUESTIONS, mQuestionsCheated);
    }



    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);


        //if the user cancelled the activity, return
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data==null) return;
        }

        mIsCheater = CheatActivity.wasAnswerShown(data);
        if (!mQuestionsCheated[mCurrentIndex]) {
            mQuestionsCheated[mCurrentIndex] = mIsCheater;
        }

        Log.d(TAG, "in onActivityResult, saving that user cheated at " + mCurrentIndex + " is " + mQuestionsCheated[mCurrentIndex]);

    }


}
