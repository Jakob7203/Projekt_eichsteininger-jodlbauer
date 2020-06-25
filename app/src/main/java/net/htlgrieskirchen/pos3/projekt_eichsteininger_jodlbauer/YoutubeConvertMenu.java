package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class YoutubeConvertMenu extends AppCompatActivity {

    private static final int RQ_SDCARD = 707;
    public static YoutubeConvertMenu menuInstance = null;
    String TAG = "SD-Card Permission";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_convert_menu);

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

        EditText editURL = findViewById(R.id.editURL);
        Button btnDownloadMP4 = findViewById(R.id.btnDownloadMP4);
        btnDownloadMP4.setOnClickListener((View v) -> {
            //entfällt bis auf Weiteres
        });
        Button btnDownloadMP3 = findViewById(R.id.btnDownloadMP3);
        btnDownloadMP3.setOnClickListener((View v) -> {
            Log.d("DownloadTask", "Button MP3-Download clicked");
            YoutubeVideoDownloadTask task = new YoutubeVideoDownloadTask();
            task.execute(parseYTURL(editURL.getText().toString()), CHANNEL_ID);
        });
    }

    private String parseYTURL(String videoID) {
        if(videoID.contains("&")) {
            videoID = videoID.split("&")[0];
        }
        if(videoID.contains("v=")) {
            videoID = videoID.split("v=")[1];
        } else {
            videoID = videoID.split("be/")[1];
        }
        Log.d("DownloadTask", "URL parsed: https://www.youtube.com/watch?v=" + videoID);
        return "https://www.youtube.com/watch?v=" + videoID;
    }

    public void request() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // RQ_CAMERA ist just any constant value to identify the request
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RQ_SDCARD);
        } else {
            Log.d(TAG, "permission for SD-Card already granted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RQ_SDCARD) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "permission denied");
            } else {
                Log.d(TAG, "permission granted");
            }
        }
    }
}
