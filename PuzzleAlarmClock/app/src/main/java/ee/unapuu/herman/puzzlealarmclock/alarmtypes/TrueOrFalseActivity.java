package ee.unapuu.herman.puzzlealarmclock.alarmtypes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ee.unapuu.herman.puzzlealarmclock.AlarmActivity;
import ee.unapuu.herman.puzzlealarmclock.R;

/**
 * Created by toks on 28.05.17.
 */

public class TrueOrFalseActivity extends AlarmActivity {
    private final int CORRECT_ANSWER_THRESHOLD = 4;
    private int correctAnswerCount = 0;
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

        startAudioResource(getGenericAudioResource());
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
                handleScoring(correctAnswer, Boolean.TRUE);
                break;
            case R.id.falseButton:
                Log.d("id", "false got pressed");
                handleScoring(correctAnswer, Boolean.FALSE);
                break;
        }
    }

    public void handleScoring(Boolean correct, Boolean userInput) {
        if (correct == userInput) {
            correctAnswerCount++;
            if (correctAnswerCount >= CORRECT_ANSWER_THRESHOLD) {
                stopAlarmSuccessfully();
            } else {
                chooseRandomQuestion();
            }
        } else {
            stopAlarmWithPenalty();
        }
    }


}
