package ee.unapuu.herman.puzzlealarmclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ee.unapuu.herman.puzzlealarmclock.alarmtypes.SwipingActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmtypes.TrueOrFalseActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmtypes.WalkAroundActivity;

public class MainActivity extends Activity {


    private TextView alarmInfoTextView;
    private int alarmHour;
    private int alarmMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //debugging
        Intent intent = new Intent(MainActivity.this, WalkAroundActivity.class);
        //startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateAlarmInfo();
    }

    public void openSetAlarmView(View view) {
        Intent intent = new Intent(MainActivity.this, SetAlarmActivity.class);
        startActivity(intent);
    }

    public void updateAlarmInfo() {
        alarmInfoTextView = (TextView) findViewById(R.id.setAlarmInfo);
        if (alarmInfoTextView == null) {
            Log.d("test", "wtf");
        }
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("alarmPrefs", Context.MODE_PRIVATE);

        alarmHour = sharedPref.getInt("alarmHour", -1);
        alarmMinute = sharedPref.getInt("alarmMinute", -1);

        if (alarmHour == -1 || alarmMinute == -1) {
            alarmInfoTextView.setText("No alarm set");
        } else {
            final java.util.Calendar c = java.util.Calendar.getInstance();
            int hour = c.get(java.util.Calendar.HOUR_OF_DAY);
            int minute = c.get(java.util.Calendar.MINUTE);
            if (alarmMinute < 9) {
                alarmInfoTextView.setText(alarmHour + ":0" + alarmMinute);
            } else {
                alarmInfoTextView.setText(alarmHour + ":" + alarmMinute);
            }
        }
    }

    public void viewAlarmTypes(View view) {
        Intent i = new Intent(this, AlarmDemoActivity.class);
        startActivity(i);
    }
}
