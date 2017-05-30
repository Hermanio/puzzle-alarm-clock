package ee.unapuu.herman.puzzlealarmclock.alarmtypes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.seismic.ShakeDetector;

import ee.unapuu.herman.puzzlealarmclock.AlarmActivity;
import ee.unapuu.herman.puzzlealarmclock.R;
import ee.unapuu.herman.puzzlealarmclock.alarmresult.AlarmEndActivity;

/**
 * Created by toks on 26.05.17.
 */

public class ShakeShakeShakeActivity extends AlarmActivity implements ShakeDetector.Listener {

    private TextView shakeMeTextView;
    private MediaPlayer music;
    private int shakeCount = 0;
    private final int SHAKE_TARGET = 10;

    private ShakeDetector sd;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goFullScreen();

        shakeMeTextView = (TextView) findViewById(R.id.shakeMeTextView);
        shakeMeTextView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fast_shake_animation));

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sd = new ShakeDetector(this);
        sd.start(sensorManager);

        playAlarmMusic();
    }

    public void hearShake() {
        shakeCount++;
        if (shakeCount >= SHAKE_TARGET) {
            //shaking done, stop this
            stopAlarmSuccessfully();
        } else {
            getWindow().getDecorView().setBackgroundColor(Color.rgb(255, 255-(25*shakeCount), 255-(25*shakeCount)));
            shakeMeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36 + shakeCount*3);
        }
        Log.d("shake", Integer.toString(shakeCount));
        Toast.makeText(this, "Don't shake me, bro! ", Toast.LENGTH_SHORT).show();
    }

    private void stopAlarmSuccessfully() {
        music.stop();
        //clear alarms here

        sd.stop();

        Intent i = new Intent(this, AlarmEndActivity.class);
        startActivity(i);
        finish();
    }

    private void playAlarmMusic() {

        music = MediaPlayer.create(ShakeShakeShakeActivity.this, R.raw.shakeshakeshake);
        music.setLooping(true);
        music.start();

        AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        //int originalVolume = am.getStreamVolume(AudioManager.USE_DEFAULT_STREAM_TYPE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }
}
