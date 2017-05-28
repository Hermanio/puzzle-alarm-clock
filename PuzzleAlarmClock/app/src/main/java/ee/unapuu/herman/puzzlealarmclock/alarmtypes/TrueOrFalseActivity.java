package ee.unapuu.herman.puzzlealarmclock.alarmtypes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ee.unapuu.herman.puzzlealarmclock.R;

/**
 * Created by toks on 28.05.17.
 */

public class TrueOrFalseActivity extends Activity {
    private TextView statementText;
    private Button trueButton;
    private Button falseButton;

    private HashMap<String, Boolean> statementAnswerPairs;
    private Boolean correctAnswer;
    private String randomStatement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    stopAlarm();
                } else {
                    Toast.makeText(this, "you dun goofd", Toast.LENGTH_SHORT).show();
startPenalty();
                }
                break;
            case R.id.falseButton:
                Log.d("id", "false got pressed");
                if (correctAnswer == Boolean.FALSE) {
                    Toast.makeText(this, "yes this is correct", Toast.LENGTH_SHORT).show();
                    stopAlarm();
                } else {
                    Toast.makeText(this, "you dun goofd", Toast.LENGTH_SHORT).show();
startPenalty();
                }
                break;

        }
    }

    private void playAlarm() {
        //play the alarm infinitely
    }

    private void startPenalty() {
        //start the penalty sound for n times because they effed up
        //also hide all elements and show text shaking like WROOOOOONG!
        statementText.setVisibility(View.GONE);
        trueButton.setVisibility(View.GONE);
        falseButton.setVisibility(View.GONE);
        findViewById(R.id.wrongTextView).setVisibility(View.VISIBLE);

        //replace with proper audio stop and event
        try {
            Thread.sleep(1000);
            stopAlarm();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void stopAlarm() {
        //stop the audio

        //maybe start new intent with text like "Have a nice day!" and with a beautiful sunshine for n seconds
        finish();
    }

}
