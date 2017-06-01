package ee.unapuu.herman.puzzlealarmclock.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

/**
 * Created by toks on 26.05.17.
 */

public class ActivityRecognizedService extends IntentService {

    public static final String WALKING_DETECTED = "ee.unapuu.herman.puzzlealarmclock.service.walking.DETECTED";
    private final int ACTIVITY_THRESHOLD = 1; //confidence rating limit
    private boolean ignoreFutureResults = false;
    private Context applicationContext;

    public ActivityRecognizedService() {
        super("ActivityRecognizedService");
    }

    public ActivityRecognizedService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent) && !ignoreFutureResults) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities());
        }

    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
        for (DetectedActivity activity : probableActivities) {
            switch (activity.getType()) {
                case DetectedActivity.IN_VEHICLE: {
                    Log.e("ActivityRecogition", "In Vehicle: " + activity.getConfidence());
                    break;
                }
                case DetectedActivity.STILL: {
                    Log.e("ActivityRecogition", "Still: " + activity.getConfidence());
                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    Log.e("ActivityRecogition", "On Bicycle: " + activity.getConfidence());
                    break;
                }
                case DetectedActivity.ON_FOOT:
                case DetectedActivity.RUNNING:
                case DetectedActivity.WALKING:
                    Log.e("ActivityRecogition", "Walking: " + activity.getConfidence());
                    if (activity.getConfidence() >= ACTIVITY_THRESHOLD) {
                        ignoreFutureResults = true; //solves issue with the service issuing multiple broadcasts in rapid succession
                        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(WALKING_DETECTED));
                    }
                    break;

                case DetectedActivity.UNKNOWN: {
                    Log.e("ActivityRecogition", "Unknown: " + activity.getConfidence());
                    break;
                }
            }
        }
    }
}