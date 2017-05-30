package ee.unapuu.herman.puzzlealarmclock;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by toks on 30.05.17.
 */

public class AlarmActivity extends Activity {
    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        Toast.makeText(this, "oh no you aint quittin me", Toast.LENGTH_SHORT).show();
    }

    protected void goFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shake_me);
    }
}
