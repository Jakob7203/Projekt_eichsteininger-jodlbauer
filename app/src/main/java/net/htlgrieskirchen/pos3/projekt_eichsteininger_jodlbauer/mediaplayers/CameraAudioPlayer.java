package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.mediaplayers;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.CameraResponse;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playlists.CameraAudioList;

public class CameraAudioPlayer extends AppCompatActivity {
    private static final String TAG = CameraAudioPlayer.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: entered");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experimental_audio_player);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation != Configuration.ORIENTATION_PORTRAIT) {
            finish();
            return;
        }
        handleIntent();
    }

    private void handleIntent() {
        Log.d(TAG, "handleIntent: entered");
        Intent intent = getIntent();
        if (intent == null) return;
        CameraAudioButtonFragment rightFragment = (CameraAudioButtonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.rightfrag);
        CameraResponse item = Static_Access.currentAudio;
        rightFragment.play(item, this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.headermenu, menu);
        ActionBar a = getSupportActionBar();
        InflaterHelper.inflateHeader(a);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.onestepback) {
            startActivity(new Intent(this, CameraAudioList.class));//return to the Intent you came from
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

//    private Button play;
//    private Button pause;
//    private String uri;
//    private String TAG = "TAG";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_experimental_audio_player);
//        Intent i = getIntent();
//        uri=i.getStringExtra("URI");
//        play = findViewById(R.id.play_audio);
//        pause = findViewById(R.id.pause_audio);
//        Uri u = Uri.parse(uri);
//        MediaPlayer player = MediaPlayer.create(this, u);
//        player.start();
//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!player.isPlaying())
//                {
//                    player.start();
//                }
//            }
//        });
//        pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(player.isPlaying())
//                {
//                    player.stop();
//                }
//            }
//        });
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.headermenu, menu);
//        getSupportActionBar().setTitle("");
//        return super.onCreateOptionsMenu(menu);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.onestepback) {
//            startActivity(new Intent(this, CameraAudioList.class));//return to the Intent you came from
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
