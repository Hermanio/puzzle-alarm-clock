package ee.unapuu.herman.puzzlealarmclock.alarmtypes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ee.unapuu.herman.puzzlealarmclock.AlarmActivity;
import ee.unapuu.herman.puzzlealarmclock.R;
import ee.unapuu.herman.puzzlealarmclock.alarmresult.AlarmEndActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmresult.PenaltyAlarmActivity;

/**
 * Created by toks on 28.05.17.
 */

public class TrueOrFalseActivity extends AlarmActivity {
    private TextView statementText;
    private Button trueButton;
    private Button falseButton;

    private HashMap<String, Boolean> statementAnswerPairs;
    private Boolean correctAnswer;
    private String randomStatement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goFullScreen();

        setContentView(R.layout.activity_true_or_false);

        statementText = (TextView) findViewById(R.id.statementTextView);
        trueButton = (Button) findViewById(R.id.trueButton);
        falseButton = (Button) findViewById(R.id.falseButton);

        initialiseQuestions();
        chooseRandomQuestion();

        playAlarm();
    }


    private void initialiseQuestions() {
        statementAnswerPairs = new HashMap<>();
        statementAnswerPairs.put("Dogs have 4 feet", Boolean.TRUE);
        statementAnswerPairs.put("The sun is actually not a star", Boolean.FALSE);
        statementAnswerPairs.put("Linux is the most popular server OS", Boolean.TRUE);
        statementAnswerPairs.put("The speed of light is 300m/s", Boolean.FALSE);
        statementAnswerPairs.put("All spiders are venomous", Boolean.FALSE);
        statementAnswerPairs.put("Every 60 seconds in Africa, a minute passes", Boolean.TRUE);
        statementAnswerPairs.put("The Great Wall of China is visible from space", Boolean.FALSE);
    }

    private void chooseRandomQuestion() {
        Random random = new Random();
        List<String> statements = new ArrayList<>(statementAnswerPairs.keySet());
        randomStatement = statements.get(random.nextInt(statements.size()));
        correctAnswer = statementAnswerPairs.get(randomStatement);

        Log.d("nul", randomStatement);
            statementText.setText(randomStatement);

    }

    public void handleAnswer(View view) {
        switch (view.getId()) {
            case R.id.trueButton:
                Log.d("id", "true got pressed");
                if (correctAnswer == Boolean.TRUE) {
                    Toast.makeText(this, "yes this is correct", Toast.LENGTH_SHORT).show();
                    stopAlarmSuccessfully();
                } else {
                    Toast.makeText(this, "you dun goofd", Toast.LENGTH_SHORT).show();
stopAlarmWithPenalty();
                }
                break;
            case R.id.falseButton:
                Log.d("id", "false got pressed");
                if (correctAnswer == Boolean.FALSE) {
                    Toast.makeText(this, "yes this is correct", Toast.LENGTH_SHORT).show();
                    stopAlarmSuccessfully();
                } else {
                    Toast.makeText(this, "you dun goofd", Toast.LENGTH_SHORT).show();
stopAlarmWithPenalty();
                }
                break;

        }
    }

    private void playAlarm() {
        //play the alarm infinitely
    }


    private void stopAlarmSuccessfully() {
        //stop the audio

        //maybe start new intent with text like "Have a nice day!" and with a beautiful sunshine for n seconds
        Intent i = new Intent(this, AlarmEndActivity.class);
        startActivity(i);
        finish();
    }

    private void stopAlarmWithPenalty() {
        Intent i = new Intent(this, PenaltyAlarmActivity.class);
        startActivity(i);
        finish();
    }

}
