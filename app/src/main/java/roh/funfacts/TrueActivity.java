package roh.funfacts;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TrueActivity extends ActionBarActivity {

    public static final String TAG = TrueActivity.class.getSimpleName();
    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.relativeLayout) RelativeLayout relativeLayout;
    @InjectView(R.id.layout_application) RelativeLayout layoutApplication;
    @InjectView(R.id.factTextView) TextView factLable;
    @InjectView(R.id.showFactButton) Button showFactButton;
    @InjectView(R.id.imageView) ImageView imageView;

    private FactBook mFactBook = new FactBook();
    private ColorWheel mColorWheel = new ColorWheel();
    private FaceWheel mFaceWheel = new FaceWheel();
    private String[] mfacts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true);
        ButterKnife.inject(this);
        getWindow().setWindowAnimations(android.R.style.Animation_Translucent);
        mfacts = getResources().getStringArray(R.array.frases);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        View.OnClickListener listener = new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                String fact = mFactBook.getFact(mfacts);

                factLable.setText(fact);
                int color = mColorWheel.getColor();

                relativeLayout.setBackgroundColor(color);
                layoutApplication.setBackgroundColor(color);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));

                int face = mFaceWheel.getFace();
                imageView.setImageDrawable(getResources().getDrawable(face));

            }
        };
        showFactButton.setOnClickListener(listener);

        if(isFirstTime()){
            AlertDialog alertDialog = new AlertDialog.Builder(TrueActivity.this).create();
            alertDialog.setTitle(getString(R.string.titulo_janela_novidade));
            alertDialog.setMessage(getString(R.string.novidade_janela_texto));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fun_facts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_about:
                startActivity(new Intent(TrueActivity.this, AboutActivity.class));
                break;
            case R.id.action_search:
                Bitmap bitmap2 = screenShot(relativeLayout);
                shareFrase(bitmap2);
                break;
            case R.id.action_rate:
                rateApp();
                break;
            case R.id.action_suggest:
                startActivity(new Intent(TrueActivity.this, SuggestionActivity.class));
                break;
            default:
                return true;

        }


        return super.onOptionsItemSelected(item);
    }



    public void shareFrase(Bitmap bitmap){
        // Save this bitmap to a file.

        File cache = getApplicationContext().getExternalCacheDir();
        Long tsLong = System.currentTimeMillis()/1000;
        String fileName = tsLong.toString() + ".png";
        File sharefile = new File(cache, fileName);
        try {
            FileOutputStream out = new FileOutputStream(sharefile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {

        }



        // Now send it out to share
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + sharefile));
        try {
            String sharedPhrase = getResources().getString(R.string.share_phrase);
            startActivity(Intent.createChooser(share, sharedPhrase));
        } catch (Exception e) {

        }
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


    private boolean MyStartActivity(Intent aIntent) {
        try
        {
            startActivity(aIntent);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }

    //On click event for rate this app button
    public void rateApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Try Google play
        intent.setData(Uri.parse("market://details?id=llamaze.com.br.true"));
        if (!MyStartActivity(intent)) {
            //Market (Google play) app seems not installed, let's try to open a webbrowser
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=llamaze.com.br.true"));
            if (!MyStartActivity(intent)) {
                //Well if this also fails, we have run out of options, inform the user.
                Toast.makeText(this, getString(R.string.error_open_market), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }




}
