package roh.funfacts;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import butterknife.ButterKnife;
import butterknife.InjectView;
import roh.funfacts.R;

public class SuggestionActivity extends ActionBarActivity {
    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.suggestButton) Button suggestButton;
    @InjectView(R.id.suggestion_email) EditText email;
    @InjectView(R.id.suggestion_txt) EditText suggestion;
    private ProgressDialog pdialog;
    private String sender, textMessage, subject, username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        ButterKnife.inject(this);
        getWindow().setWindowAnimations(android.R.style.Animation_Translucent);


        subject = "Sugestão de Frase";
        username = "llamaze.code@gmail.com";
        password = Utils.decryptIt("Q40I1AJ/sdVsz5zmTFuoHg==");


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        suggestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sender = email.getText().toString();
                textMessage = "Enviado por: " + sender + "\n\n" + "Sugestão: ";
                textMessage += suggestion.getText().toString();
                String sug = suggestion.getText().toString();
                if (isValidEmailAddress(sender)) {
                    if(!sug.isEmpty()) {
                        if (isConnectingToInternet()) {
                            new SendEmailAsyncTask().execute();
                        } else {
                            Toast.makeText(SuggestionActivity.this, getString(R.string.check_connection_message), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SuggestionActivity.this, getString(R.string.error_sugestion_blank), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SuggestionActivity.this, getString(R.string.error_email_black), Toast.LENGTH_LONG).show();
                }


            }
        });
    }





    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public void cleanFields(){
        email.setText("");
        email.requestFocus();
        suggestion.setText("");
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
        Mail m = new Mail(username, password);
        boolean enviou = false;


        public SendEmailAsyncTask() {
            String[] toArr = {"llamaze.code@gmail.com"};
            m.setTo(toArr);
            m.setFrom(sender);
            m.setSubject(subject);
            m.setBody(textMessage);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdialog = ProgressDialog.show(SuggestionActivity.this, getString(R.string.please_wait), getString(R.string.sending_suggestion), true, false);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            pdialog.dismiss();
            if(enviou){
                Toast.makeText(SuggestionActivity.this, getString(R.string.suggestion_sent_message), Toast.LENGTH_LONG).show();
                cleanFields();
            } else {
                Toast.makeText(SuggestionActivity.this, getString(R.string.error_sending_suggestion), Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected Boolean doInBackground(Void... params) {


            try {
                if(m.send()){
                    enviou = true;
                }
            } catch (Exception e) {
                enviou = false;
                Log.e("MainActivity", "Could not send email", e);
            }
            return null;
        }


    }
}
