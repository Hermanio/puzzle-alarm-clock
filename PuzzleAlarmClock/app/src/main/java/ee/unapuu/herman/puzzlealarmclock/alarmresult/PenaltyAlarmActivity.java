package ee.unapuu.herman.puzzlealarmclock.alarmresult;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import ee.unapuu.herman.puzzlealarmclock.AlarmActivity;
import ee.unapuu.herman.puzzlealarmclock.R;

/**
 * Created by toks on 29.05.17.
 */

public class PenaltyAlarmActivity extends AlarmActivity {

    private final int SOUND_CLIP_PLAY_COUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goFullScreen();
        setContentView(R.layout.activity_penalty_alarm);

        TextView wrongText = (TextView) findViewById(R.id.wrongTextView);
        wrongText.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.unacceptable_shake_animation));

        startAudioResource(R.raw.unacceptable);
        AlarmClearingHelper.clearAlarm(getApplicationContext());
        AlarmClearingHelper.clearAlarmEvents(getApplicationContext());

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                stopAudioResource();
                finish();
            }
        }, mediaPlayer.getDuration() * SOUND_CLIP_PLAY_COUNT);
    }
}
