package ee.unapuu.herman.puzzlealarmclock;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by toks on 30.05.17.
 */

public class AlarmActivity extends Activity {
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        Toast.makeText(this, "oh no you aint quittin me", Toast.LENGTH_SHORT).show();
    }

    protected void goFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shake_me);
    }

    public void startAudioResource(int audioResourceId) {

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, audioResourceId);
            mediaPlayer.setLooping(true);
            //mediaPlayer.start();
        }

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void stopAudioResource() {
        mediaPlayer.stop();
        mediaPlayer = null;
    }

    private void setVolumeToMax() {

        //todo: figure out if looping this every x seconds is a good idea

        AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        //int originalVolume = am.getStreamVolume(AudioManager.USE_DEFAULT_STREAM_TYPE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }
}
