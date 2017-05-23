package ee.unapuu.herman.puzzlealarmclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.IntentService;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by toks on 23.05.17.
 */

public class SetAlarmActivity extends Activity {
    public int alarmHour;
    public int alarmMinute;
    private TextView alarmTextView;
    private TextView alarmTypeTextView;
    private String alarmType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        alarmTextView = (TextView) findViewById(R.id.alarmTimeDisplay);
        alarmTextView.setText("9:00");

        alarmTypeTextView = (TextView) findViewById(R.id.alarmTypeDisplay);
        alarmTypeTextView.setText("Rick OR Morty?");
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
        alarmTextView.setText(hour+":"+minute);

    }

    public void setAlarm(View view) {
        //todo: validation/defaults, set alarm time and type
        finish();
    }
}
