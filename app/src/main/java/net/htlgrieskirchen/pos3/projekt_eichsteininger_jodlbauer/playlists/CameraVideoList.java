package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playlists;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.mediaplayers.ExperimentalVideoViewer;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.CameraConvertMenu;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.ListAdapter;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;

public class CameraVideoList extends AppCompatActivity {
    private String TAG = "TAG";
    ListAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_video_list);
        listView = findViewById(R.id.camervideolv);
        adapter = new ListAdapter(this, R.layout.single_playable_media, Static_Access.cameraVideos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
//                File f = new File(Static_Access.cameraVideos.get(pos).getPath());
//                if (f.exists()) {
                    Intent i = new Intent(CameraVideoList.this, ExperimentalVideoViewer.class);
                    i.putExtra("URI", Static_Access.cameraVideos.get(pos).getUri());
                    startActivity(i);
//                }
//                else
//                {
//                    Toast.makeText(CameraVideoList.this, "It appears the Video has been deleted", Toast.LENGTH_LONG).show();
//                    Static_Access.cameraVideos.remove(pos);
//                    adapter = new ListAdapter(CameraVideoList.this, R.layout.single_playable_media, Static_Access.cameraVideos);
//                    listView.setAdapter(adapter);
//                }
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
