package ee.unapuu.herman.puzzlealarmclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by toks on 30.05.17.
 */

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(intent);
        finish();
    }
}