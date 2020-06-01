package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ExperimentalAudioPlayer extends AppCompatActivity {
    Button play;
    Button pause;
    private String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experimental_audio_player);
        play=findViewById(R.id.play_audio);
        pause=findViewById(R.id.pause_audio);
        Uri uri = Uri.parse(Static_Access.uri);
        MediaPlayer player=MediaPlayer.create(this,uri);
        player.start();
    }
}
