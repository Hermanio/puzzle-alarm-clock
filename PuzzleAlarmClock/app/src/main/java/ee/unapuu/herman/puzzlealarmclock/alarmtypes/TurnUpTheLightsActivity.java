package ee.unapuu.herman.puzzlealarmclock.alarmtypes;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goFullScreen();

        setContentView(R.layout.activity_turn_up_the_lights);
        startAudioResource(R.raw.shakeshakeshake);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("sensor", Float.toString(event.values[0]));
        float lightSensorReading = event.values[0];
        if (lightSensorReading > LIGHT_THRESHOLD) {
            //obviously pointed to the source
            stopAlarmSuccessfully();
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
