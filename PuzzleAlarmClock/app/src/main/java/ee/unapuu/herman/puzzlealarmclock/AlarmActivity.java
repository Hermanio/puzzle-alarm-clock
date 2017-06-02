package ee.unapuu.herman.puzzlealarmclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import ee.unapuu.herman.puzzlealarmclock.alarmresult.AlarmEndActivity;
import ee.unapuu.herman.puzzlealarmclock.alarmresult.PenaltyAlarmActivity;

/**
 * Created by toks on 30.05.17.
 */

public class AlarmActivity extends Activity {
    public static MediaPlayer mediaPlayer;
    public boolean isAlarmView = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        if (isAlarmView) {
            stopAlarmWithPenalty();
        }
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
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            // mediaPlayer = MediaPlayer.create(this, audioResourceId);

            mediaPlayer.setLooping(true);
            try {
                mediaPlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://ee.unapuu.herman.puzzlealarmclock/" + audioResourceId));
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }

        setVolumeToMax();
    }

    public void stopAudioResource() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = null;
    }

    //with this the user is unable to turn down the alarm volume
    private void setVolumeToMax() {

        final Handler h = new Handler();
        final int delay = 100; //milliseconds

        h.postDelayed(new Runnable() {
            public void run() {
                AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                am.setStreamVolume(AudioManager.STREAM_ALARM, am.getStreamMaxVolume(AudioManager.STREAM_ALARM), 0);
                h.postDelayed(this, delay);
            }
        }, delay);
    }

    public int getGenericAudioResource() {
        int[] audioTracks = {R.raw.nuke, R.raw.deathmetal};
        int index = new Random().nextInt(audioTracks.length);
        return audioTracks[index];
    }

    public void stopAlarmSuccessfully() {
        stopAudioResource();
        Intent i = new Intent(this, AlarmEndActivity.class);
        startActivity(i);
        finish();
    }

    public void stopAlarmWithPenalty() {
        stopAudioResource();
        Intent i = new Intent(this, PenaltyAlarmActivity.class);
        startActivity(i);
        finish();
    }
}
