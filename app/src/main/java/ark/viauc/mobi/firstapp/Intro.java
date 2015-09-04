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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG_ACTIVITIES, "onCreate");
        setContentView(R.layout.activity_intro);
    }

    protected void onStart() {
        super.onStart();
        Log.d(TAG_ACTIVITIES, "onStart");
    }

    protected void onResume() {
        super.onResume();
        Log.d(TAG_ACTIVITIES, "onResume");
    }

    protected void onPause() {
        super.onPause();
        Log.d(TAG_ACTIVITIES, "onPause");
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
