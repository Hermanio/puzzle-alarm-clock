package ee.unapuu.herman.puzzlealarmclock.alarmresult;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import ee.unapuu.herman.puzzlealarmclock.AlarmActivity;
import ee.unapuu.herman.puzzlealarmclock.R;

/**
 * Created by toks on 1.06.17.
 */

public class ScreamingSunActivity extends AlarmActivity {
    private final int SOUND_CLIP_PLAY_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goFullScreen();
        setContentView(R.layout.activity_screaming_sun);

        startAudioResource(R.raw.screamingsun);

        ImageView screamingSunImage = (ImageView) findViewById(R.id.screamingSunImageView);
        screamingSunImage.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.screaming_sun_shake));
        new Handler().postDelayed(new Runnable() {
            public void run() {
                stopAudioResource();
                AlarmClearingHelper.clearAlarm(getApplicationContext());
                AlarmClearingHelper.clearAlarmEvents(getApplicationContext());
                finish();
            }
        }, mediaPlayer.getDuration() * SOUND_CLIP_PLAY_COUNT);
    }
}
