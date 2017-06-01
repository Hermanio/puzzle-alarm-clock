package ee.unapuu.herman.puzzlealarmclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ee.unapuu.herman.puzzlealarmclock.alarmtypes.ShakeShakeShakeActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmtypes.SwipingActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmtypes.TrueOrFalseActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmtypes.TurnUpTheLightsActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmtypes.WalkAroundActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmtypes.WhoIsTalkingActivity;

/**
 * Created by toks on 29.05.17.
 */

public class AlarmDemoActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_demo);
        findViewById(R.id.shakeShakeShakeDemo).setOnClickListener(this);
        findViewById(R.id.swipeDemo).setOnClickListener(this);
        findViewById(R.id.trueOrFalseDemo).setOnClickListener(this);
        findViewById(R.id.turnUpTheLightsDemo).setOnClickListener(this);
        findViewById(R.id.walkAroundDemo).setOnClickListener(this);
        findViewById(R.id.rickOrMortyDemo).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.shakeShakeShakeDemo:
                i = new Intent(this, ShakeShakeShakeActivity.class);
                startActivity(i);
                break;
            case R.id.swipeDemo:
                i = new Intent(this, SwipingActivity.class);
                startActivity(i);
                break;
            case R.id.trueOrFalseDemo:
                i = new Intent(this, TrueOrFalseActivity.class);
                startActivity(i);
                break;
            case R.id.turnUpTheLightsDemo:
                i = new Intent(this, TurnUpTheLightsActivity.class);
                startActivity(i);
                break;
            case R.id.walkAroundDemo:
                i = new Intent(this, WalkAroundActivity.class);
                startActivity(i);
                break;
            case R.id.rickOrMortyDemo:
                i = new Intent(this, WhoIsTalkingActivity.class);
                startActivity(i);
                break;
        }
    }
}
