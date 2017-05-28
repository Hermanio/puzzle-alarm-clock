package ee.unapuu.herman.puzzlealarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

import ee.unapuu.herman.puzzlealarmclock.alarmtypes.ShakeShakeShakeActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmtypes.SwipingActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmtypes.TrueOrFalseActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmtypes.TurnUpTheLightsActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmtypes.WalkAroundActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmtypes.WhoIsTalkingActivity;

/**
 * Created by toks on 25.05.17.
 */

public class AlarmTarget extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //clear alarm
        //fire proper intent in switch/case
        Log.d("test", "alarm went off");
        Toast.makeText(context, "Alarm went off", Toast.LENGTH_SHORT).show();


        Random random = new Random();
        int alarmType = random.nextInt(6);
        Intent i;
        switch (alarmType) {
            case 0:
                i = new Intent(context, ShakeShakeShakeActivity.class);
                context.startActivity(i);
                break;
            case 1:
                i = new Intent(context, SwipingActivity.class);
                context.startActivity(i);
                break;
            case 2:
                i = new Intent(context, TrueOrFalseActivity.class);
                context.startActivity(i);
                break;
            case 3:
                i = new Intent(context, TurnUpTheLightsActivity.class);
                context.startActivity(i);
                break;
            case 4:
                i = new Intent(context, WalkAroundActivity.class);
                context.startActivity(i);
                break;
            case 5:
                i = new Intent(context, WhoIsTalkingActivity.class);
                context.startActivity(i);
                break;
        }
    }
}
