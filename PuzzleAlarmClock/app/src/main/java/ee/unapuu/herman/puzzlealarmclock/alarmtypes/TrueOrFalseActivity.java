package ee.unapuu.herman.puzzlealarmclock.alarmtypes;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    private boolean correctAnswer;
    private String randomStatement;
    private VideoView backgroundVideoView;
    private int countdownDuration;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goFullScreen();

        setContentView(R.layout.activity_true_or_false);

        statementText = (TextView) findViewById(R.id.statementTextView);
        trueButton = (Button) findViewById(R.id.trueButton);
        falseButton = (Button) findViewById(R.id.falseButton);

        initialiseQuestions();
        if (savedInstanceState != null) {
            randomStatement = savedInstanceState.getString("statement");
            correctAnswer = savedInstanceState.getBoolean("statementAnswer");
            statementText.setText(randomStatement);
        } else {
            chooseRandomQuestion();

        }

        //startAudioResource(getGenericAudioResource());
        backgroundVideoView = (VideoView) findViewById(R.id.videoBackgroundCountDown);
        Uri video = Uri.parse("android.resource://ee.unapuu.herman.puzzlealarmclock/" + R.raw.countdown);
        backgroundVideoView.setVideoURI(video);
        backgroundVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //mp.setLooping(true);
                mediaPlayer = mp;
                int seekToPosition = 0;

                if (savedInstanceState != null) {
                    seekToPosition = savedInstanceState.getInt("currentPosition");
                    mediaPlayer.seekTo(seekToPosition);
                }

                backgroundVideoView.start();

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                        @Override
                                                        public void onCompletion(MediaPlayer mp) {
                                                            stopAudioResource();
                                                            stopAlarmWithPenalty();
                                                            finish();
                                                        }
                                                    }
                );

            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mediaPlayer.setOnCompletionListener(null);
        int currentPosition = mediaPlayer.getCurrentPosition();
        outState.putInt("currentPosition", currentPosition);
        Log.d("curpos", Integer.toString(currentPosition));
        outState.putString("statement", randomStatement);
        outState.putBoolean("statementAnswer", correctAnswer);
    }

    private void initialiseQuestions() {
        loadQuestionsIntoMemory();

    }

    private void chooseRandomQuestion() {
        Random random = new Random();
        List<String> statements = new ArrayList<>(statementAnswerPairs.keySet());
        String newStatement = statements.get(random.nextInt(statements.size()));
        while (newStatement.equals(randomStatement)) {
            newStatement = statements.get(random.nextInt(statements.size()));
        }
        randomStatement = newStatement;
        correctAnswer = statementAnswerPairs.get(randomStatement);

        Log.d("nul", randomStatement + " " + correctAnswer);
        statementText.setText(randomStatement);

    }


    public void handleAnswer(View view) {
        switch (view.getId()) {
            case R.id.trueButton:
                Log.d("id", "true got pressed");
                handleScoring(correctAnswer, true);
                break;
            case R.id.falseButton:
                Log.d("id", "false got pressed");
                handleScoring(correctAnswer, false);
                break;
        }
    }

    public void handleScoring(boolean correct, boolean userInput) {
        if (correct == userInput) {
            correctAnswerCount++;
            if (correctAnswerCount >= CORRECT_ANSWER_THRESHOLD) {
                handler.removeCallbacksAndMessages(null);
                stopAlarmSuccessfully();
            } else {
                chooseRandomQuestion();
            }
        } else {
            handler.removeCallbacksAndMessages(null);
            stopAlarmWithPenalty();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("statements.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void loadQuestionsIntoMemory() {
        try {
            statementAnswerPairs = new HashMap<>();
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            Iterator<?> keys = obj.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                Log.d("stuff", key + " " + obj.getBoolean(key));
                statementAnswerPairs.put(key, obj.getBoolean(key));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
