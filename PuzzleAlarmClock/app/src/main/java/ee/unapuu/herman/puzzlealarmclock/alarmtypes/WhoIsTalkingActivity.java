package ee.unapuu.herman.puzzlealarmclock.alarmtypes;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import ee.unapuu.herman.puzzlealarmclock.AlarmActivity;
import ee.unapuu.herman.puzzlealarmclock.R;
import ee.unapuu.herman.puzzlealarmclock.alarmresult.AlarmEndActivity;

/**
 * Created by toks on 26.05.17.
 */

public class WhoIsTalkingActivity extends AlarmActivity {

    private String correctAnswer;
    private MediaPlayer dialog;

    private ImageView rickImage;
    private ImageView mortyImage;
    private TextView headingText;
    private ImageView screamingSunImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goFullScreen();

        setContentView(R.layout.activity_who_is_talking);

        rickImage = (ImageView) findViewById(R.id.rickImageView);
        mortyImage = (ImageView) findViewById(R.id.mortyImageView);
        screamingSunImage = (ImageView) findViewById(R.id.screamingSunImageView);

        headingText = (TextView) findViewById(R.id.rickormortyTitle);

        generateCorrectAnswer();
        runAnimations();
        playAudioTrack();
    }

    private void runAnimations() {


        rickImage.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim));
        mortyImage.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim));
        headingText.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim));

    }

    private void playAudioTrack() {
        if (correctAnswer.equals("Rick")) {
            dialog = MediaPlayer.create(WhoIsTalkingActivity.this, R.raw.rickdialog1);
        } else {
            dialog = MediaPlayer.create(WhoIsTalkingActivity.this, R.raw.mortydialog1);
        }
        dialog.setLooping(true);
        dialog.start();
    }

    private void generateCorrectAnswer() {
        if (Math.random() < 0.5) {
            correctAnswer = "Rick";
        } else {
            correctAnswer = "Morty";
        }
    }

    private void startPenalty() {
        screamingSunImage.setVisibility(View.VISIBLE);
        screamingSunImage.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.screaming_sun_shake));

        rickImage.clearAnimation();
        mortyImage.clearAnimation();
        headingText.clearAnimation();
        rickImage.setVisibility(View.GONE);
        mortyImage.setVisibility(View.GONE);
        headingText.setVisibility(View.GONE);

        dialog.stop();
        dialog.setLooping(false);
        dialog = MediaPlayer.create(WhoIsTalkingActivity.this, R.raw.screamingsun);
        final int[] playCount = {0};
        dialog.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            int maxPlayCount = 2;
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (playCount[0] < maxPlayCount) {
                    playCount[0]++;
                    mp.seekTo(0);
                    mp.start();
                } else {
                    stopAlarmSuccessfully();
                }
            }
        });
        dialog.start();
    }

    private void stopAlarmSuccessfully() {
        dialog.stop();
        //clear alarms here
        Intent i = new Intent(this, AlarmEndActivity.class);
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
}
