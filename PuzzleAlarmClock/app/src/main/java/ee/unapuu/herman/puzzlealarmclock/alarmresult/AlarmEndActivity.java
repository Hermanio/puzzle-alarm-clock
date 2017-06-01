package ee.unapuu.herman.puzzlealarmclock.alarmresult;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import ee.unapuu.herman.puzzlealarmclock.AlarmActivity;
import ee.unapuu.herman.puzzlealarmclock.R;

/**
 * Created by toks on 28.05.17.
 */

public class AlarmEndActivity extends AlarmActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goFullScreen();
        setContentView(R.layout.activity_alarm_end);

        AlarmClearingHelper.clearAlarm(getApplicationContext());
        AlarmClearingHelper.clearAlarmEvents(getApplicationContext());


        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 2000);
    }
}
