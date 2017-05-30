package ee.unapuu.herman.puzzlealarmclock.alarmresult;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import ee.unapuu.herman.puzzlealarmclock.AlarmTarget;

/**
 * Created by toks on 30.05.17.
 */

public class AlarmClearingHelper {

    public static void clearAlarm(Context applicationContext) {

        SharedPreferences prefs = applicationContext.getSharedPreferences("alarmPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("alarmHour", -1);
        editor.putInt("alarmMinute", -1);
        editor.apply();
    }

    public static void clearAlarmEvents(Context context) {
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmTarget.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        try {
            alarmMgr.cancel(pendingIntent);
        } catch (Exception e) {
            Log.e("cancel", "AlarmManager update was not canceled. " + e.toString());
        }
        //alarmMgr.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
    }

}
