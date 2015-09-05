package ark.viauc.mobi.firstapp;

import android.app.*;
import android.content.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class MailActivity extends Activity {

    private static String ACTIVE_COLOR;
    private Button btnBack, btnSend;
    private EditText fldTO, fldSUBJECT, fldMESSAGE;
    protected String recipient, subject, text;

    // Mail
    private Session session;
    private ProgressDialog pdialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        SharedPreferences prefs = getSharedPreferences(Intro.PREFERENCES, MODE_PRIVATE);
        ACTIVE_COLOR = prefs.getString(Intro.PREF_COLOR, Intro.COLOR_BLUE);

        context = this;

        // Setup buttons
        setupButtons();

        // Setup fields
        setupFields();

        // Setup intents
        setupIntent();
    }

    private void setupIntent() {

      /*  anIntent.setType("text/plain");
        String action = anIntent.getAction();

        String recipient = null;
        String subject = null;
        String message = null;

        if (anIntent.getExtras() != null) {
            Log.i(Intro.TAG, "kazka turi");
        }

        if (anIntent.hasExtra(anIntent.EXTRA_EMAIL)) {
            recipient = anIntent.getStringExtra(anIntent.EXTRA_EMAIL);
            fldTO.setText(recipient);
            Log.i(Intro.TAG, "recipient found: " + recipient);
        }

        if (anIntent.hasExtra(Intent.EXTRA_SUBJECT)) {
            subject = anIntent.getStringExtra(Intent.EXTRA_SUBJECT);
            fldSUBJECT.setText(subject);
        }

        if (anIntent.hasExtra(Intent.EXTRA_TEXT)) {
            message = anIntent.getStringExtra(Intent.EXTRA_TEXT);
            fldMESSAGE.setText(message);
        }
        Log.i(Intro.TAG, "Action: " + action);

        if (recipient != null && subject != null & message != null)
            sendMail(recipient, subject, message);*/
    }

    private void setupFields() {
        fldTO = (EditText) findViewById(R.id.editText3);
        fldSUBJECT = (EditText) findViewById(R.id.editText2);
        fldMESSAGE = (EditText) findViewById(R.id.editText4);

        recipient = "";
        text = "";
        subject = "";

    }

    private void setupButtons() {
        btnBack = (Button) findViewById(R.id.back_button);
        btnSend = (Button) findViewById(R.id.send_button);

        changeButtonBackground(btnBack);
        changeButtonBackground(btnSend);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipient = fldTO.getText().toString();
                text = fldMESSAGE.getText().toString();
                subject = fldSUBJECT.getText().toString();

                sendMail(recipient, subject, text);
            }
        });
    }

    public void sendMail(String recipient, String subject, String text) {
        Log.i(Intro.TAG, "Send mail ----->");
        Log.i(Intro.TAG, "To: " + recipient);
        Log.i(Intro.TAG, "Subject: " + subject);
        Log.i(Intro.TAG, "Text: " + text);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sfirstapp@gmail.com", "spassword");
            }
        });

        pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();

    }



    public void changeButtonBackground(Button target) {
        switch (ACTIVE_COLOR) {
            case Intro.COLOR_BLUE: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_selector)); break;
            case Intro.COLOR_RED: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_selector)); break;
            case Intro.COLOR_ORANGE: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.orange_selector)); break;
            case Intro.COLOR_GREEN: target.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_selector)); break;
        }
    }

    class RetreiveFeedTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("sfirstapp@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                message.setSubject(subject);
                message.setContent(text, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pdialog.dismiss();

            fldTO.setText("");;
            fldSUBJECT.setText("");
            fldMESSAGE.setText("");

            Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
        }
    }

}
