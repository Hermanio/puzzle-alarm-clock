package ee.unapuu.herman.puzzlealarmclock.alarmtypes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.widget.TextView;

import ee.unapuu.herman.puzzlealarmclock.AlarmActivity;
import ee.unapuu.herman.puzzlealarmclock.R;
import ee.unapuu.herman.puzzlealarmclock.alarmresult.AlarmEndActivity;

/**
 * Created by toks on 26.05.17.
 */

public class TurnUpTheLightsActivity extends AlarmActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor lightSensor;
    private float LIGHT_THRESHOLD = 2500.0f;
    private ConstraintLayout viewLayout;
    private TextView shineALightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goFullScreen();

        setContentView(R.layout.activity_turn_up_the_lights);
        startAudioResource(R.raw.shinealightloop);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        viewLayout = (ConstraintLayout) findViewById(R.id.lightsLayout);
        shineALightTextView = (TextView) findViewById(R.id.shineALightTextView);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("sensor", Float.toString(event.values[0]));
        float lightSensorReading = event.values[0];
        int adjustedLightValue;
        if (lightSensorReading > LIGHT_THRESHOLD) {
            //obviously pointed to the source
            stopAlarmSuccessfully();
        } else {
            adjustedLightValue = Math.round(lightSensorReading / 10);
            viewLayout.setBackgroundColor(Color.rgb(adjustedLightValue, adjustedLightValue, adjustedLightValue));
            shineALightTextView.setTextColor(Color.rgb(255-adjustedLightValue,255-adjustedLightValue,255-adjustedLightValue));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        mSensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    private void stopAlarmSuccessfully() {
        stopAudioResource();
        Intent i = new Intent(this, AlarmEndActivity.class);
        startActivity(i);
        finish();

    }


}
