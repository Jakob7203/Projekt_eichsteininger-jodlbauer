package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.mediaplayers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.MainActivity;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;

public class CameraVideoViewer extends AppCompatActivity {
    private VideoView videoView;
    private String uri;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_viewer);
        Intent i = getIntent();
        uri = i.getStringExtra("URI");
        videoView=findViewById(R.id.videoView);
        Uri u = Uri.parse(uri);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(u);
        videoView.start();
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
}
