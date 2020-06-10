package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playlists;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.mediaplayers.ExperimentalAudioPlayer;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.CameraConvertMenu;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.ListAdapter;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;

import java.io.File;

public class CameraAudioList extends AppCompatActivity {
    private String TAG = "TAG";
    ListAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_audio_list);
        listView = findViewById(R.id.cameraaudiolv);
        adapter = new ListAdapter(this, R.layout.single_playable_media, Static_Access.cameraAudio);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                File f = new File(Static_Access.cameraAudio.get(pos).getPath());
                Log.d(TAG, Static_Access.cameraAudio.get(pos).getPath());
                Log.d(TAG, ""+f.exists());//somehow doesnt exist
                //if (f.exists()) {
                    Intent i = new Intent(CameraAudioList.this, ExperimentalAudioPlayer.class);
                    i.putExtra("URI", Static_Access.cameraAudio.get(pos).getUri());
                    startActivity(i);
//                }
//                else
//                {
//                    Toast.makeText(CameraAudioList.this, "It appears the File has been deleted", Toast.LENGTH_LONG).show();
//                    Static_Access.cameraAudio.remove(pos);
//                    adapter = new ListAdapter(CameraAudioList.this, R.layout.single_playable_media, Static_Access.cameraAudio);
//                    listView.setAdapter(adapter);
                //}
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.headermenu, menu);
        getSupportActionBar().setTitle("");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.onestepback) {
            startActivity(new Intent(this, CameraConvertMenu.class));//return to the Intent you came from
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
