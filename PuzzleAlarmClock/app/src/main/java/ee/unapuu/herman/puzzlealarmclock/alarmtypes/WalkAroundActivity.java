package ee.unapuu.herman.puzzlealarmclock.alarmtypes;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

import ee.unapuu.herman.puzzlealarmclock.AlarmActivity;
import ee.unapuu.herman.puzzlealarmclock.R;
import ee.unapuu.herman.puzzlealarmclock.service.ActivityRecognizedService;

/**
 * Created by toks on 26.05.17.
 */

public class WalkAroundActivity extends AlarmActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public GoogleApiClient mApiClient;

    private Intent intent;
    private PendingIntent pendingIntent;
    private boolean hasAlreadyStopped = false; //necessary because the API fires those intents in a weird way
    private IntentFilter f;

    private BroadcastReceiver onEvent = new BroadcastReceiver() {
        public void onReceive(Context context, Intent i) {
            Log.d("test", "something happened");
            //movement detected, can gracefully exit
            if (!hasAlreadyStopped) {
                hasAlreadyStopped = true;
                ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mApiClient, pendingIntent);
                mApiClient.disconnect();
                stopAlarmSuccessfully();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goFullScreen();

        setContentView(R.layout.activity_walk_around);

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();

        f = new IntentFilter(ActivityRecognizedService.WALKING_DETECTED);
        LocalBroadcastManager.getInstance(this).registerReceiver(onEvent, f);

        startAudioResource(R.raw.moveyourfeetloop);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mApiClient, pendingIntent);
        mApiClient.disconnect();
        mApiClient = null;

        LocalBroadcastManager.getInstance(this).unregisterReceiver(onEvent);


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        intent = new Intent(this, ActivityRecognizedService.class);
        pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mApiClient, 5000, pendingIntent);

        //todo: make app communicate with service using this pattern: https://github.com/commonsguy/cw-omnibus/tree/master/Service/Downloader/app/src/main/java/com/commonsware/android/downloader

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}