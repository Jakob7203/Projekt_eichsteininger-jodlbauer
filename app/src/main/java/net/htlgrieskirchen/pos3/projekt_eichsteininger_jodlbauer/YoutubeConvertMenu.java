package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class YoutubeConvertMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_convert_menu);

        PackageManager m = getPackageManager();
        PackageInfo p = null;
        try {
            p = m.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final String appPath = p.applicationInfo.dataDir;
        Log.d("DownloadTask", "AppPath: " + appPath);

        EditText editURL = findViewById(R.id.editURL);
        Button btnDownloadMP4 = findViewById(R.id.btnDownloadMP4);
        btnDownloadMP4.setOnClickListener((View v) -> {
            //entfällt bis auf Weiteres
        });
        Button btnDownloadMP3 = findViewById(R.id.btnDownloadMP3);
        btnDownloadMP3.setOnClickListener((View v) -> {
            Log.d("DownloadTask", "Button MP3-Download clicked");
            YoutubeVideoDownloadTask task = new YoutubeVideoDownloadTask();
            task.execute(parseYTURL(editURL.getText().toString()), appPath);
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
}
