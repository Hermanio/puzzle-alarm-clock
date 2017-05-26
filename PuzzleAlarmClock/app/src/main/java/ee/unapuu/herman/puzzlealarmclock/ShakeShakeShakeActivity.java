package ee.unapuu.herman.puzzlealarmclock;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.seismic.ShakeDetector;

/**
 * Created by toks on 26.05.17.
 */

public class ShakeShakeShakeActivity extends Activity implements ShakeDetector.Listener {

    private TextView shakeMeTextView;
    private MediaPlayer music;
    private int shakeCount = 0;
    private final int SHAKE_TARGET = 10;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_me);

        shakeMeTextView = (TextView) findViewById(R.id.shakeMeTextView);
        shakeMeTextView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fast_shake_animation));

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);

        playAlarmMusic();
    }

    public void hearShake() {
        shakeCount++;
        if (shakeCount >= SHAKE_TARGET) {
            //shaking done, stop this
            stopAlarm();
        } else {
            getWindow().getDecorView().setBackgroundColor(Color.rgb(255, 255-(25*shakeCount), 255-(25*shakeCount)));
            shakeMeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36 + shakeCount*3);
        }
        Log.d("shake", Integer.toString(shakeCount));
        Toast.makeText(this, "Don't shake me, bro! ", Toast.LENGTH_SHORT).show();
    }

    private void stopAlarm() {
        music.stop();
        //clear alarms here

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
