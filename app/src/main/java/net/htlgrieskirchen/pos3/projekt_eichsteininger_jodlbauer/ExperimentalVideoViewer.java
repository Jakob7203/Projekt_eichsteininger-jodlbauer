package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class ExperimentalVideoViewer extends AppCompatActivity {
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_viewer);
        videoView=findViewById(R.id.videoView);
        String u = "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(u);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
    }
}
