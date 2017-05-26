package ee.unapuu.herman.puzzlealarmclock;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by toks on 26.05.17.
 */

public class TurnUpTheLightsActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor lightSensor;
    private float LIGHT_THRESHOLD = 2500.0f;
    private MediaPlayer music;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_up_the_lights);
        playAlarmMusic();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("sensor", Float.toString(event.values[0]));
        float lightSensorReading = event.values[0];
        if (lightSensorReading > LIGHT_THRESHOLD) {
            //obviously pointed to the source
            stopAlarm();
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

    private void stopAlarm() {
        music.stop();
        //clear alarms here

        finish();

    }

    private void playAlarmMusic() {
        music = MediaPlayer.create(TurnUpTheLightsActivity.this, R.raw.shakeshakeshake);
        music.setLooping(true);
        music.start();
    }
}
