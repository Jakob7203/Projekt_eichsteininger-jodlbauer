package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

public class YoutubeConvertMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_convert_menu);

        EditText editURL = findViewById(R.id.editURL);

        Button btnDownloadMP4 = findViewById(R.id.btnDownloadMP4);
        btnDownloadMP4.setOnClickListener((View v) -> {
            String[] videoUrl = editURL.getText().toString().split("v=");
            String videoID = videoUrl[1];
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
        });

        Button btnDownloadMP3 = findViewById(R.id.btnDownloadMP3);
        btnDownloadMP3.setOnClickListener((View v) -> {

        });
    }
}
