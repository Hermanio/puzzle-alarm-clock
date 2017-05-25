package ee.unapuu.herman.puzzlealarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

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
    }
}
