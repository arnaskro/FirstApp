package ark.viauc.mobi.firstapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.webkit.*;
import android.widget.*;

import java.util.ArrayList;

public class BrowserActivity extends Activity {

    private static final String DEFAULT_SITE = "http://kromelis.lt";
    private static String ACTIVE_COLOR;
    private WebView wv;

    // Buttons
    private ArrayList<Button> grpButtons;
    private Button btnMenu, btnBack, btnForward, btnReload, btnStop;
    private EditText field;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        SharedPreferences prefs = getSharedPreferences(Intro.PREFERENCES, MODE_PRIVATE);
        ACTIVE_COLOR = prefs.getString(Intro.PREF_COLOR, Intro.COLOR_BLUE);
        // Setup buttons
        setupButtons();

        // Setup button listeners
        setupButtonListeners();

        // Setup edit text field
        setupField();

        // Setup progress bar
        setupProgressBar();

        // Setup web view
        setupWebView();

        // Setup intent
        setupIntent();

        // Launch default site
        goToWebsite(DEFAULT_SITE);
    }

    private void setupIntent() {
        Intent anIntent = getIntent();
        String action = anIntent.getAction();
        Uri uri = anIntent.getData();

        Log.i(Intro.TAG, "Action: " + action);
        Log.i(Intro.TAG, "Type: " + uri);

        if (uri != null) {
            goToWebsite(uri.toString());
        }
    }

    private void setupProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setProgress(0);
    }

    private void setupField() {
        field = (EditText) findViewById(R.id.editText);

        field.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String url = field.getText().toString();

                    if (!(url.startsWith("http://")))
                        goToWebsite("http://" + url);
                    else
                        goToWebsite(url);

                    return true;
                }
                return false;
            }
        });
    }

    private void setupButtonListeners() {

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close activity
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.goBack();

                if (wv.canGoForward()) btnForward.setEnabled(true);
                else btnForward.setEnabled(false);

                if (wv.canGoBack()) btnBack.setEnabled(true);
                else btnBack.setEnabled(false);
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.goForward();

                if (wv.canGoForward()) btnForward.setEnabled(true);
                else btnForward.setEnabled(false);

                if (wv.canGoBack()) btnBack.setEnabled(true);
                else btnBack.setEnabled(false);
            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.reload();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.stopLoading();
            }
        });
    }

    private void goToWebsite(String url) {

        field.setText(url);

        Log.d(Intro.TAG, "**Request URL: " + url);

        wv.loadUrl(url);

        btnForward.setEnabled(false);

        if (wv.canGoBack()) btnBack.setEnabled(true);
        else btnBack.setEnabled(false);
    }

    private void setupButtons() {

        btnMenu = (Button) findViewById(R.id.menu_button);
        btnBack = (Button) findViewById(R.id.history_back_button);
        btnForward = (Button) findViewById(R.id.history_forward_button);
        btnReload = (Button) findViewById(R.id.reload_button);
        btnStop = (Button) findViewById(R.id.stop_button);

        grpButtons = new ArrayList<>();
        grpButtons.add(btnMenu);
        grpButtons.add(btnBack);
        grpButtons.add(btnForward);
        grpButtons.add(btnReload);
        grpButtons.add(btnStop);

        for (Button btn : grpButtons)
            changeButtonBackground(btn);

    }

    private void setupWebView() {

        wv = (WebView) findViewById(R.id.webView);
        wv.setWebViewClient(new WebViewClient());
        wv.setWebChromeClient(new WebChromeClient());

        wv.getSettings().setJavaScriptEnabled(true);

        wv.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);

                if ((progressBar.getVisibility() == View.VISIBLE) && (progress == 0 || progress == 100)) {
                    progressBar.setVisibility(View.GONE);
                    btnReload.setVisibility(View.VISIBLE);
                    btnStop.setVisibility(View.GONE);
                } else if ((progressBar.getVisibility() == View.GONE) && (progress > 0 && progress < 100)) {
                    progressBar.setVisibility(View.VISIBLE);
                    btnReload.setVisibility(View.GONE);
                    btnStop.setVisibility(View.VISIBLE);
                }

                if (wv.canGoForward()) btnForward.setEnabled(true);
                else btnForward.setEnabled(false);

                if (wv.canGoBack()) btnBack.setEnabled(true);
                else btnBack.setEnabled(false);
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
