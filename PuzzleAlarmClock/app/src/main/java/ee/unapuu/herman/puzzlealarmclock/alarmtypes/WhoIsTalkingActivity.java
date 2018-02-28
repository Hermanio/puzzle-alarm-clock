package ee.unapuu.herman.puzzlealarmclock.alarmtypes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import ee.unapuu.herman.puzzlealarmclock.AlarmActivity;
import ee.unapuu.herman.puzzlealarmclock.R;
import ee.unapuu.herman.puzzlealarmclock.alarmresult.ScreamingSunActivity;

/**
 * Created by toks on 26.05.17.
 */

public class WhoIsTalkingActivity extends AlarmActivity {

    private String correctAnswer;

    private ImageView rickImage;
    private ImageView mortyImage;
    private TextView headingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goFullScreen();
        setContentView(R.layout.activity_who_is_talking);
        rickImage = (ImageView) findViewById(R.id.rickImageView);
        mortyImage = (ImageView) findViewById(R.id.mortyImageView);
        headingText = (TextView) findViewById(R.id.rickormortyTitle);

        if (savedInstanceState == null) {

            generateCorrectAnswer();

        } else {
            correctAnswer = savedInstanceState.getString("correctAnswer");
        }
        runAnimations();
        playAudioTrack();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("correctAnswer", correctAnswer);
    }


    private void runAnimations() {


        rickImage.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim));
        mortyImage.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim));
        headingText.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim));

    }

    private void playAudioTrack() {
        int[] rickAudioTracks = {R.raw.rickdialog1, R.raw.rickdialog2, R.raw.rickdialog3};
        int[] mortyAudioTracks = {R.raw.mortydialog1, R.raw.mortydialog2, R.raw.mortydialog3};

        if (correctAnswer.equals("Rick")) {
            startAudioResource(getRandomAudioTrack(rickAudioTracks));
        } else {
            startAudioResource(getRandomAudioTrack(mortyAudioTracks));
        }

    }


    private void generateCorrectAnswer() {
        if (Math.random() < 0.5) {
            correctAnswer = "Rick";
        } else {
            correctAnswer = "Morty";
        }
    }

    private void startPenalty() {
        stopAudioResource();
        Intent i = new Intent(this, ScreamingSunActivity.class);
        startActivity(i);
        finish();
    }


    public void handleChoice(View view) {
        switch (view.getId()) {
            case R.id.rickImageView:
                if (correctAnswer.equals("Rick")) {
                    stopAlarmSuccessfully();
                } else {
                    startPenalty();
                }
                break;
            case R.id.mortyImageView:
                if (correctAnswer.equals("Morty")) {
                    stopAlarmSuccessfully();
                } else {
                    startPenalty();
                }
                break;
        }
    }

    private int getRandomAudioTrack(int[] audioTracks) {
        int index = new Random().nextInt(audioTracks.length);
        return audioTracks[index];
    }
}
