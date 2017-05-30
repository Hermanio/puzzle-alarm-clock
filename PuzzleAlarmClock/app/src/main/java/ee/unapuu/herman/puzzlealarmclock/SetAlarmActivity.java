package ee.unapuu.herman.puzzlealarmclock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * Created by toks on 23.05.17.
 */

public class SetAlarmActivity extends Activity {
    public int alarmHour = 9;
    public int alarmMinute = 0;
    private TextView alarmTextView;
    private TextView alarmTypeTextView;
    private String alarmType;

    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        alarmTextView = (TextView) findViewById(R.id.alarmTimeDisplay);
        alarmTextView.setText("9:00");

        alarmTypeTextView = (TextView) findViewById(R.id.alarmTypeDisplay);
        alarmTypeTextView.setText("Rick OR Morty?");

        prefs = getApplicationContext().getSharedPreferences("alarmPrefs", Context.MODE_PRIVATE);

    }

    public void openTimePicker(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void openAlarmTypeDialog(View view) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(SetAlarmActivity.this);
        builderSingle.setTitle("Select alarm type");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SetAlarmActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Rick OR Morty?");
        arrayAdapter.add("Hit the things");
        arrayAdapter.add("Shake shake shake");
        arrayAdapter.add("Turn the lights on");
        arrayAdapter.add("Walk around");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                alarmType = arrayAdapter.getItem(which);
                alarmTypeTextView.setText(alarmType);
                Log.d("pick", alarmType);
            }
        });
        builderSingle.show();
    }

    public void setAlarmTime(int hour, int minute) {
        Log.d("time", Integer.toString(hour)+":"+ Integer.toString(minute));
        alarmHour = hour;
        alarmMinute = minute;
        if (alarmMinute < 9) {
            alarmTextView.setText(alarmHour + ":0" + alarmMinute);
        } else {
            alarmTextView.setText(alarmHour + ":" + alarmMinute);
        }
    }

    public void setAlarm(View view) {
        //todo: validation/defaults, set alarm time and type
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("alarmHour", alarmHour);
        editor.putInt("alarmMinute", alarmMinute);
        editor.apply();
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int hour = c.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = c.get(java.util.Calendar.MINUTE);
        //c.set(Calendar.HOUR_OF_DAY, alarmHour);
        //c.set(Calendar.MINUTE, alarmMinute);

        if (hour < alarmHour) {
            //alarm is today in evening, just apply it for today!
            Log.d("test", "alarm today in evening");
            c.set(Calendar.HOUR_OF_DAY, alarmHour);
            c.set(Calendar.MINUTE, alarmMinute);
        } else if (hour == alarmHour) {
            //alarm is in the same hour, check minutes to be sure if it is today or tomorrow
            if (minute <= alarmMinute) {
                //today, carry on
                c.set(Calendar.HOUR_OF_DAY, alarmHour);
                c.set(Calendar.MINUTE, alarmMinute);
                Log.d("test", "alarm today in evening");

            } else {
                c.add(Calendar.DAY_OF_MONTH, 1);
                c.set(Calendar.HOUR_OF_DAY, alarmHour);
                c.set(Calendar.MINUTE, alarmMinute);
                Log.d("test", "alarm tomorrow");

            }
        } else {
            //alarm is obviously during the next day
            c.add(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR_OF_DAY, alarmHour);
            c.set(Calendar.MINUTE, alarmMinute);
            Log.d("test", "alarm tomorrow");

        }
        fireAlarm(c);

        finish();
    }

    public void fireAlarm(Calendar alarmTime) {
        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmTarget.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);

        Log.d("alarm", alarmMgr.getNextAlarmClock().toString());
    }
}
