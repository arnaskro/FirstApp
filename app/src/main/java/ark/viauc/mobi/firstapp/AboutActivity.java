package ark.viauc.mobi.firstapp;


import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends Activity {

    private static String ACTIVE_COLOR;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        SharedPreferences prefs = getSharedPreferences(Intro.PREFERENCES, MODE_PRIVATE);
        ACTIVE_COLOR = prefs.getString(Intro.PREF_COLOR, Intro.COLOR_BLUE);

        btnBack = (Button) findViewById(R.id.go_back);

        changeButtonBackground(btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close activity
                finish();
            }
        });

    }

    private void changeButtonBackground(Button target) {
        switch (ACTIVE_COLOR) {
            case Intro.COLOR_BLUE: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_selector)); break;
            case Intro.COLOR_RED: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_selector)); break;
            case Intro.COLOR_ORANGE: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.orange_selector)); break;
            case Intro.COLOR_GREEN: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_selector)); break;
        }
    }

}
