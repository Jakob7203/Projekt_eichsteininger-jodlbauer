package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class YoutubeConvertMenu extends AppCompatActivity {
    String TAG = "TAG";
    String durl = "";
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
            new YouTubeExtractor(this) {
                @Override
                public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                    if (ytFiles != null) {
                        int itag = 22;
                        durl  = ytFiles.get(itag).getUrl();
                        Log.d(TAG, durl);
                        Uri uri = Uri.parse(durl);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                }
            }.extract(videoID, true, true);
        });
        Button btnDownloadMP3 = findViewById(R.id.btnDownloadMP3);
        btnDownloadMP3.setOnClickListener((View v) -> {

        });
    }
}
