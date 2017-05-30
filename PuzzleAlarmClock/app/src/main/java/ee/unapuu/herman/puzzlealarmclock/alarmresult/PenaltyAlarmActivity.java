package ee.unapuu.herman.puzzlealarmclock.alarmresult;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import ee.unapuu.herman.puzzlealarmclock.R;

/**
 * Created by toks on 29.05.17.
 */

public class PenaltyAlarmActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penalty_alarm);

        TextView wrongText = (TextView) findViewById(R.id.wrongTextView);
        wrongText.setVisibility(View.VISIBLE);
        wrongText.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim));
        getWindow().getDecorView().setBackgroundColor(Color.rgb(252, 173, 103));

        AlarmClearingHelper.clearAlarm(getApplicationContext());

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 2000);
    }
}
