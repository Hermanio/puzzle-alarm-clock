package ee.unapuu.herman.puzzlealarmclock.alarmtypes;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import ee.unapuu.herman.puzzlealarmclock.R;
import ee.unapuu.herman.puzzlealarmclock.misc.OnSwipeListener;

/**
 * Created by toks on 28.05.17.
 */

public class SwipingActivity extends Activity implements View.OnTouchListener {
    private final String TAG = "test";
    private final int SWIPE_ACTION_THRESHOLD = 10;
    private String nextSwipeAction;
    private final String[] swipeDirections = {"up", "down", "left", "right"};

    private int correctSwipeCounter = 0;

    private ConstraintLayout activityLayout;
    private GestureDetector gestureDetector;
    private TextView directionTextView;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiping);
        activityLayout = (ConstraintLayout) findViewById(R.id.swipingActivityLayout);
        gestureDetector = new GestureDetector(this, new OnSwipeListener() {
            @Override
            public boolean onSwipe(Direction direction) {
                Log.d(TAG, direction.name());
                checkSwipeResult(direction.name());
                return super.onSwipe(direction);
            }
        });

        activityLayout.setOnTouchListener(this);
        directionTextView = (TextView) findViewById(R.id.swipeDirectionTextView);
        setNextSwipeAction();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private void checkSwipeResult(String swipeDirection) {
        if (swipeDirection.equals(nextSwipeAction)) {
            correctSwipeCounter++;
            if (correctSwipeCounter >= SWIPE_ACTION_THRESHOLD) {
                stopAlarm();
            }
            animateResult(true);
            setNextSwipeAction();
        } else {
            animateResult(false);
            correctSwipeCounter--;
        }
    }

    private void  setNextSwipeAction() {

        Random random = new Random();
        int choiceIndex = random.nextInt(swipeDirections.length);
        nextSwipeAction = swipeDirections[choiceIndex];

        directionTextView.setText(nextSwipeAction);
    }

    private void stopAlarm() {
        finish();
    }

    private void animateResult(Boolean isCorrect) {
        if (isCorrect) {
            activityLayout.setBackgroundResource(R.drawable.ripple_drawable_correct);
            activityLayout.setPressed(true);
            activityLayout.setPressed(false);
        } else {
            activityLayout.setBackgroundResource(R.drawable.ripple_drawable_incorrect);
            activityLayout.setPressed(true);
            activityLayout.setPressed(false);
        }
    }
}
