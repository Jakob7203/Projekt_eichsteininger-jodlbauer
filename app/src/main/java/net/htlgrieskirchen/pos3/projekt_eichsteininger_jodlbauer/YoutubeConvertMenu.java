package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

public class YoutubeConvertMenu extends AppCompatActivity {
    String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_convert_menu);

        EditText editURL = findViewById(R.id.editURL);
        editURL.setText("https://www.youtube.com/watch?v=0Wc2Og4vr2I");
        Button btnDownloadMP4 = findViewById(R.id.btnDownloadMP4);
        btnDownloadMP4.setOnClickListener((View v) -> {
            String[] videoUrl = editURL.getText().toString().split("v=");
            String videoID = videoUrl[1];
            Log.d(TAG, videoID);
            if (videoID.contains("&")) {
                videoID = videoID.split("&")[0];
            }
            String format = "MP4";
            YoutubeVideoDownloadTask task = new YoutubeVideoDownloadTask();
            task.execute(videoID, format);
            String downloadUrl = null;
            try {
                downloadUrl = task.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            Uri uri = Uri.parse(downloadUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            Log.d(TAG, downloadUrl);
        });

        Button btnDownloadMP3 = findViewById(R.id.btnDownloadMP3);
        btnDownloadMP3.setOnClickListener((View v) -> {

        });
    }
}
