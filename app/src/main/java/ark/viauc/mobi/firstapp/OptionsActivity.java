package ark.viauc.mobi.firstapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class OptionsActivity extends Activity {

    private Spinner spinner;
    private Bundle myBundle;
    private static String ACTIVE_COLOR;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        // Setup bundle
        setup_bundle();

        // Setup buttons
        setup_buttons();

        // Setup spinner
        setup_spinner();
    }

    private void setup_bundle() {
        myBundle = getIntent().getExtras();

        if (myBundle != null && myBundle.containsKey(Intro.COLOR_EXISTS_CODE)) {
            ACTIVE_COLOR = myBundle.getString(Intro.COLOR_EXISTS_CODE);
        }

    }

    private void setup_buttons() {

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

    private void setup_spinner() {

        spinner = (Spinner) findViewById(R.id.color_picker);

        if (ACTIVE_COLOR != null) {
            switch(ACTIVE_COLOR) {
                case Intro.COLOR_RED: spinner.setSelection(3); break;
                case Intro.COLOR_BLUE: spinner.setSelection(0); break;
                case Intro.COLOR_ORANGE: spinner.setSelection(1); break;
                case Intro.COLOR_GREEN: spinner.setSelection(2); break;
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosen_color = (String) parent.getItemAtPosition(position);

                Log.d("ARK.debugger", "New chosen color: " + chosen_color);

                Intent inReturn = new Intent();

                switch (chosen_color.toUpperCase()) {
                    case Intro.COLOR_RED:
                        inReturn.putExtra(Intro.COLOR_RETURN_CODE, Intro.COLOR_RED);
                        ACTIVE_COLOR = Intro.COLOR_RED;
                        break;
                    case Intro.COLOR_BLUE:
                        inReturn.putExtra(Intro.COLOR_RETURN_CODE, Intro.COLOR_BLUE);
                        ACTIVE_COLOR = Intro.COLOR_BLUE;
                        break;
                    case Intro.COLOR_ORANGE:
                        inReturn.putExtra(Intro.COLOR_RETURN_CODE, Intro.COLOR_ORANGE);
                        ACTIVE_COLOR = Intro.COLOR_ORANGE;
                        break;
                    case Intro.COLOR_GREEN:
                        inReturn.putExtra(Intro.COLOR_RETURN_CODE, Intro.COLOR_GREEN);
                        ACTIVE_COLOR = Intro.COLOR_GREEN;
                        break;
                    default:
                        inReturn.putExtra(Intro.COLOR_RETURN_CODE, Intro.COLOR_BLUE);
                        ACTIVE_COLOR = Intro.COLOR_BLUE;
                        break;
                }

                changeButtonBackground(btnBack);
                setResult(Intro.RESULT_TRUE, inReturn);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    public void changeButtonBackground(Button target) {
        switch (ACTIVE_COLOR) {
            case Intro.COLOR_BLUE: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_selector)); break;
            case Intro.COLOR_RED: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_selector)); break;
            case Intro.COLOR_ORANGE: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.orange_selector)); break;
            case Intro.COLOR_GREEN: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_selector)); break;
        }
    }

}
