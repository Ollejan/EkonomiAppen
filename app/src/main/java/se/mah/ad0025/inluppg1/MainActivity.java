package se.mah.ad0025.inluppg1;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Huvudaktivitet som startar applikationen.
 * @author Jonas Dahlström 5/10-14
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("myloginprefs", Context.MODE_PRIVATE);
        FragmentManager fragmentManager = getFragmentManager();
        DatabaseController databaseController = new DatabaseController(this);
        new LoginController(prefs, fragmentManager, databaseController, new WelcomeFragment(), new LoginFragment(), new CreateFragment(), new MenuFragment());
    }
}
