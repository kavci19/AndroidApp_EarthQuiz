package com.example.geoquiz;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {



    private boolean mCorrectAnswer;
    private static final String IS_ANSWER_TRUE =  "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";
    private TextView mCheatAnswerTextView;
    private Button mShowAnswerButton;
    private String KEY_IS_CHEATER;
    private boolean isCheated = false;

    public static boolean wasAnswerShown(Intent i) {

        return i.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);

    }

    private void showAnswer() {

        if (mCorrectAnswer) {
            mCheatAnswerTextView.setText(R.string.true_button);
        }

        else {
            mCheatAnswerTextView.setText(R.string.false_button);
        }

    }

    private void setResultAnswerShown() {

        Intent i = new Intent(CheatActivity.this, MainActivity.class);
        isCheated = true;
        i.putExtra(EXTRA_ANSWER_SHOWN, isCheated );
        setResult(RESULT_OK, i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState !=null) {

            isCheated = savedInstanceState.getBoolean(KEY_IS_CHEATER);
            setResultAnswerShown();

        }

        mCorrectAnswer = getIntent().getBooleanExtra(IS_ANSWER_TRUE, false);

        mCheatAnswerTextView = findViewById(R.id.answer_text_view);

        mShowAnswerButton = findViewById(R.id.show_answer_button);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showAnswer();
                setResultAnswerShown();

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstance){

        super.onSaveInstanceState(savedInstance);

        Log.i("CheatActivity", "in onSaveInstanceState, saving " + isCheated);
        savedInstance.putBoolean(KEY_IS_CHEATER, isCheated );




    }

}
