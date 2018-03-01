package ee.unapuu.herman.puzzlealarmclock.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by toks on 2.06.17.
 */

public class BackgroundResourcePlayer {

    private static MediaPlayer videoPlayer;
    private static MediaPlayer audioPlayer;


    public static void playAudioResource(final Context context, int resourceId, boolean isLooping) {

        if (audioPlayer != null) {
            if (audioPlayer.isPlaying()) {
                stopAudioResource();
            }
        }

        audioPlayer = new MediaPlayer();
        audioPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        // mediaPlayer = MediaPlayer.create(this, audioResourceId);

        audioPlayer.setLooping(isLooping);
        try {
            audioPlayer.setDataSource(context, Uri.parse("android.resource://ee.unapuu.herman.puzzlealarmclock/" + resourceId));
            audioPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }


        audioPlayer.start();


    }

    public static void playVideoResource(final Context context, int resourceId, boolean isLooping, int seekTime) {
        videoPlayer = MediaPlayer.create(context, resourceId);

        videoPlayer.start();


    }

    public static void stopAudioResource() {
        audioPlayer.stop();
        audioPlayer.reset();
    }

    public static void stopVideoResource() {
        videoPlayer.stop();
        videoPlayer.reset();
    }

    public static void addOnCompletionListenerToAudioEvent(MediaPlayer.OnCompletionListener onCompletionListener) {
        audioPlayer.setOnCompletionListener(onCompletionListener);
    }

    public static void addOnCompletionListenerToVideoEvent(MediaPlayer.OnCompletionListener onCompletionListener) {
        videoPlayer.setOnCompletionListener(onCompletionListener);
    }

    public static void removeOnCompletionListenerFromAudioEvent() {
        audioPlayer.setOnCompletionListener(null);
    }

    public static void removeOnCompletionListenerFromVideoEvent() {
        videoPlayer.setOnCompletionListener(null);
    }

    public static int getAudioTrackDuration() {
        if (audioPlayer != null) {
            return audioPlayer.getDuration();
        }
        return 0;
    }

    public static int getVideoTrackDuration() {
        if (videoPlayer != null) {
            return videoPlayer.getDuration();
        }
        return 0;
    }


}
