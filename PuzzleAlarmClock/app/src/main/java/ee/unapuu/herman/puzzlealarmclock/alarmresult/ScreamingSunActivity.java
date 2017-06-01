package ee.unapuu.herman.puzzlealarmclock.alarmresult;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import ee.unapuu.herman.puzzlealarmclock.AlarmActivity;
import ee.unapuu.herman.puzzlealarmclock.R;

/**
 * Created by toks on 1.06.17.
 */

public class ScreamingSunActivity extends AlarmActivity {
    private static int SCREAMING_SUN_DURATION = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screaming_sun);

        startAudioResource(R.raw.screamingsun);

        ImageView screamingSunImage = (ImageView) findViewById(R.id.screamingSunImageView);
        screamingSunImage.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.screaming_sun_shake));
        new Handler().postDelayed(new Runnable() {
            public void run() {
                stopAudioResource();
                finish();
            }
        }, SCREAMING_SUN_DURATION);
    }
}
