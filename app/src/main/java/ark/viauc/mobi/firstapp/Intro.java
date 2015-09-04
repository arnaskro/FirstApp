package ark.viauc.mobi.firstapp;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import java.util.*;

public class Intro extends Activity {

    // Debugging tags
    public static final String TAG = "ARK.debugger";
    public static final String TAG_ACTIVITIES = "ARK.activities";

    // Colors
    public static final String COLOR_BLUE = "BLUE";
    public static final String COLOR_ORANGE = "ORANGE";
    public static final String COLOR_GREEN = "GREEN";
    public static final String COLOR_RED = "RED";
    private static String ACTIVE_COLOR;

    // Preferences
    public static final String PREFERENCES = "PREFERENCES";
    public static final String PREF_COLOR = "PREF_COLOR";

    // Specific codes
    public static final String COLOR_EXISTS_CODE = "COLOR_EXISTS_CODE";
    public static final int COLOR_REQUEST_CODE = 7777;
    public static final String COLOR_RETURN_CODE = "7778";
    public static final int RESULT_TRUE = 1001;
    public static final int RESULT_FALSE = 1000;

    // Buttons
    private ArrayList<Button> grpButtons;
    private Button btnAbout, btnOptions, btnMail, btnBrowser, btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG_ACTIVITIES, "onCreate");
        setContentView(R.layout.activity_intro);

        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        ACTIVE_COLOR = prefs.getString(PREF_COLOR, COLOR_BLUE);

        createButtons();
        changeMainColor();
        setButtonListeners();
    }

    private void createButtons() {
        btnAbout = (Button) findViewById(R.id.about_button);
        btnOptions = (Button) findViewById(R.id.options_button);
        btnMail = (Button) findViewById(R.id.mail_button);
        btnBrowser = (Button) findViewById(R.id.browser_button);
        btnQuit = (Button) findViewById(R.id.quit_button);

        grpButtons = new ArrayList<>();
        grpButtons.add(btnAbout);
        grpButtons.add(btnOptions);
        grpButtons.add(btnBrowser);
        grpButtons.add(btnQuit);
        grpButtons.add(btnMail);
    }

    private void setButtonListeners() {

        // Button about
        btnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Browser button clicked");


            }
        });

        // Button about
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "About button clicked");


            }
        });

        // Button options
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Options button clicked");

                // Create explicit intent
                Intent inOptions = new Intent(Intro.this, OptionsActivity.class);

                // Put extra (The active color)
                inOptions.putExtra(COLOR_EXISTS_CODE, ACTIVE_COLOR);

                // Launch intent
                startActivityForResult(inOptions, COLOR_REQUEST_CODE);
            }
        });

        // Button quit
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == COLOR_REQUEST_CODE) {
            if(resultCode == RESULT_TRUE) {
                ACTIVE_COLOR = data.getExtras().getString(COLOR_RETURN_CODE);
                Log.d(TAG, "Active color changed: " + ACTIVE_COLOR);

                changeMainColor();
            }
        }
    }

    private void changeMainColor() {

        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(PREF_COLOR, ACTIVE_COLOR);
        edit.apply();

        for (Button btn: grpButtons)
            changeButtonBackground(btn);

    }

    public void changeButtonBackground(Button target) {
        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        ACTIVE_COLOR = prefs.getString(PREF_COLOR, COLOR_BLUE);

        switch (ACTIVE_COLOR) {
            case COLOR_BLUE: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_selector)); break;
            case COLOR_RED: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_selector)); break;
            case COLOR_ORANGE: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.orange_selector)); break;
            case COLOR_GREEN: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_selector)); break;
        }
    }

    protected void onStart() {
        super.onStart();
        Log.d(TAG_ACTIVITIES, "onStart");
    }

    protected void onResume() {
        super.onResume();
        Log.d(TAG_ACTIVITIES, "onResume");

        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        ACTIVE_COLOR = prefs.getString(PREF_COLOR, COLOR_BLUE);

        changeMainColor();
    }

    protected void onPause() {
        super.onPause();
        Log.d(TAG_ACTIVITIES, "onPause");

        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(PREF_COLOR, ACTIVE_COLOR);
        edit.apply();
    }

    protected void onRestart() {
        super.onRestart();
        Log.d(TAG_ACTIVITIES, "onRestart");
    }

    protected void onStop() {
        super.onStop();
        Log.d(TAG_ACTIVITIES, "onStop");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG_ACTIVITIES, "onDestroy");
    }

}
