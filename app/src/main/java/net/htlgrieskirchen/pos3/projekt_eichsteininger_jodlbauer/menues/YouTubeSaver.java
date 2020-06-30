package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.YouTubeDownload.YoutubeVideoDownloadTask;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;

public class YouTubeSaver extends AppCompatActivity {
    private EditText editURL;
    private EditText editTitle;
    private LinearLayout linearLayout;
    public static ProgressBar prgDownload = null;
    public static YouTubeSaver menuInstance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_saver);
        linearLayout = findViewById(R.id.ll_yts);
        editURL = findViewById(R.id.editURL);
        editTitle=findViewById(R.id.edit_YT_Title);
        prgDownload = findViewById(R.id.prgDownload);
        Button btnDownloadMP3 = findViewById(R.id.btnDownloadMP3);
        if(Static_Access.mode.equals("light"))
        {
            editURL.setHintTextColor(Color.parseColor("#0d0d0d"));
            editURL.setTextColor(Color.parseColor("#0d0d0d"));
            editTitle.setHintTextColor(Color.parseColor("#0d0d0d"));
            editTitle.setTextColor(Color.parseColor("#0d0d0d"));
            btnDownloadMP3.setTextColor(Color.parseColor("#0d0d0d"));
            btnDownloadMP3.setBackgroundResource(R.drawable.round_button_light);
        }
        else
        {
            editURL.setTextColor(Color.parseColor("#f2f2f2"));
            editURL.setHintTextColor(Color.parseColor("#f2f2f2"));
            editTitle.setTextColor(Color.parseColor("#f2f2f2"));
            editTitle.setHintTextColor(Color.parseColor("#f2f2f2"));
            btnDownloadMP3.setTextColor(Color.parseColor("#f2f2f2"));
            btnDownloadMP3.setBackgroundResource(R.drawable.round_button_dark);
        }
        try {
            Bundle extras = getIntent().getExtras();
            String value = extras.getString(Intent.EXTRA_TEXT);
            editURL.setText(value);
        }
        catch(Exception e)
        {
        }
        String CHANNEL_ID = "100";
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(
                NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        request();
        menuInstance = this;
        btnDownloadMP3.setOnClickListener((View v) -> {
            prgDownload.setVisibility(View.VISIBLE);
            YoutubeVideoDownloadTask task = new YoutubeVideoDownloadTask(this,this);
            final String url = (editURL.getText().toString().trim());
            boolean valid = true;
            try {
                if(url.contains("//www.youtube.com/watch?v=")||url.contains("//youtu.be/"))
                {
                    if(isConnectedToInternet())
                    {
                        task.execute((parseYTURL(url)), CHANNEL_ID, editTitle.getText().toString().trim());
                    }
                    else
                    {
                        Toast.makeText(menuInstance, "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    valid=false;
                }
            }
            catch(Exception e)
            {
                valid=false;
            }
            if(!valid)
            {
                Toast.makeText(menuInstance, "Invaldid YouTube-Link", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        try {
            Bundle extras = getIntent().getExtras();
            String value = extras.getString(Intent.EXTRA_TEXT);
            editURL.setText(value);
        }
        catch(Exception e)
        {
        }
    }
    @Override
    public void onPause(){
        super.onPause();
    }
    private String parseYTURL(String videoID) {
        if(videoID.contains("&")) {
            videoID = videoID.split("&")[0];
        }
        if(videoID.contains("v=")) {
            videoID = videoID.split("v=")[1];
        } if(videoID.contains("be/")) {
            videoID = videoID.split("be/")[1];
        }
        return "https://www.youtube.com/watch?v=" + videoID;
    }

    public void request() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // RQ_CAMERA ist just any constant value to identify the request
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Static_Access.RQ_SDCARD);
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Static_Access.RQ_SDCARD) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //close app?
            } else {
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.headermenu, menu);
        ActionBar a = getSupportActionBar();
        InflaterHelper.inflateLayout(a, linearLayout);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.onestepback) {
            startActivity(new Intent(this, MainActivity.class));//return to the Intent you came from
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean isConnectedToInternet()
    {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;
        }
        return connected;
    }
}