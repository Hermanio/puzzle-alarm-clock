package ee.unapuu.herman.puzzlealarmclock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ee.unapuu.herman.puzzlealarmclock.alarmresult.AlarmClearingHelper;
import ee.unapuu.herman.puzzlealarmclock.alarmresult.ScreamingSunActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmtypes.WalkAroundActivity;

public class MainActivity extends Activity {


    private TextView alarmInfoTextView;
    private int alarmHour;
    private int alarmMinute;

    private TextView alarmTextView;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //debugging
        Intent intent = new Intent(MainActivity.this, ScreamingSunActivity.class);
        startActivity(intent);

        alarmTextView = (TextView) findViewById(R.id.alarmTimeDisplay);
        alarmTextView.setText("9:00");

        prefs = getApplicationContext().getSharedPreferences("alarmPrefs", Context.MODE_PRIVATE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateAlarmInfo();
    }

    public void setAlarmTime(int hour, int minute) {
        Log.d("time", Integer.toString(hour) + ":" + Integer.toString(minute));
        alarmHour = hour;
        alarmMinute = minute;
        if (alarmMinute < 9) {
            alarmTextView.setText(alarmHour + ":0" + alarmMinute);
        } else {
            alarmTextView.setText(alarmHour + ":" + alarmMinute);
        }

        setAlarm();

    }

    public void openAlarmDialog(final View view) {
        //Intent intent = new Intent(MainActivity.this, SetAlarmActivity.class);
        // startActivity(intent);
        //if alarm not set, open alarm set dialog,
        //else open dialog with options edit or cancel
        if (isAlarmSet()) {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
            builderSingle.setTitle("Pick an action");

            builderSingle.setItems(new CharSequence[]
                            {"Change alarm time", "Dismiss alarm"},
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    Toast.makeText(getApplicationContext(), "clicked 1", Toast.LENGTH_SHORT).show();
                                    openTimePicker(view);
                                    break;
                                case 1:
                                    Toast.makeText(getApplicationContext(), "clicked 2", Toast.LENGTH_SHORT).show();
                                    AlarmClearingHelper.clearAlarm(getApplicationContext());
                                    AlarmClearingHelper.clearAlarmEvents(getApplicationContext());
                                    updateAlarmInfo();
                                    break;
                            }
                        }
                    });
            builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builderSingle.show();
        } else

        {
            openTimePicker(view);
        }

    }

    public void updateAlarmInfo() {
        alarmInfoTextView = (TextView) findViewById(R.id.alarmTimeDisplay);
        if (alarmInfoTextView == null) {
            Log.d("test", "wtf");
        }
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("alarmPrefs", Context.MODE_PRIVATE);

        alarmHour = sharedPref.getInt("alarmHour", -1);
        alarmMinute = sharedPref.getInt("alarmMinute", -1);

        if (alarmHour == -1 || alarmMinute == -1) {
            alarmInfoTextView.setText("No alarm set!");
        } else {
            final java.util.Calendar c = java.util.Calendar.getInstance();
            int hour = c.get(java.util.Calendar.HOUR_OF_DAY);
            int minute = c.get(java.util.Calendar.MINUTE);
            if (alarmMinute <= 9) {
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

    public boolean isAlarmSet() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("alarmPrefs", Context.MODE_PRIVATE);

        alarmHour = sharedPref.getInt("alarmHour", -1);
        alarmMinute = sharedPref.getInt("alarmMinute", -1);

        if (alarmHour == -1 || alarmMinute == -1) {
            return false;
        }
        return true;
    }

    public void openTimePicker(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void setAlarm() {
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
    }

    public void fireAlarm(Calendar alarmTime) {
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmTarget.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);


    }
}
