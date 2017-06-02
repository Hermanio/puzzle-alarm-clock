package ee.unapuu.herman.puzzlealarmclock.alarmtypes;

import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.squareup.seismic.ShakeDetector;

import ee.unapuu.herman.puzzlealarmclock.AlarmActivity;
import ee.unapuu.herman.puzzlealarmclock.R;

/**
 * Created by toks on 26.05.17.
 */

public class ShakeShakeShakeActivity extends AlarmActivity implements ShakeDetector.Listener {

    private final int SHAKE_TARGET = 10;
    private TextView shakeMeTextView;
    private int shakeCount = 0;
    private ShakeDetector sd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goFullScreen();

        shakeMeTextView = (TextView) findViewById(R.id.shakeMeTextView);

        shakeMeTextView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_text_animation));

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sd = new ShakeDetector(this);
        sd.start(sensorManager);

        startAudioResource(R.raw.shakeshakeshake);

        if (savedInstanceState != null) {
            shakeCount = savedInstanceState.getInt("shakeCount");
            progressAnimation();
        }

    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        sd.stop();
        outState.putInt("shakeCount", shakeCount);
    }

    public void hearShake() {
        shakeCount++;
        if (shakeCount >= SHAKE_TARGET) {
            sd.stop();
            stopAlarmSuccessfully();
        } else {
            progressAnimation();
        }
    }

    public void progressAnimation() {
        getWindow().getDecorView().setBackgroundColor(Color.rgb(255, 255 - (25 * shakeCount), 255 - (25 * shakeCount)));
        shakeMeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36 + shakeCount * 3);
        shakeMeTextView.getAnimation().setDuration(500 / (shakeCount + 1));
    }


}
