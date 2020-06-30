package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playlists;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.mediaplayers.CameraAudioButtonFragment;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.mediaplayers.CameraAudioPlayer;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.CameraConvertMenu;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.CameraSaver;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.FileUtils;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.CameraResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CameraAudioList extends AppCompatActivity implements  CameraAudioFragment.OnSelectionChangedListener{

    private CameraAudioButtonFragment rightFragment;
    private boolean showRight = false;
    private LinearLayout linearLayout;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_audio_list);
        linearLayout = findViewById(R.id.ll_cal);
        initializeView();
        lv = findViewById(R.id.calv);
        registerForContextMenu(lv);
        //checking if files exist
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CameraResponse c = Static_Access.cameraAudios.get(position);
                String path = FileUtils.getPath(CameraAudioList.this, Uri.parse(c.getUri()));
                try {
                    File f = new File(path);
                    if (f.exists()) {
                        Intent i = new Intent(CameraAudioList.this, CameraAudioPlayer.class);
                        i.putExtra("URI", Static_Access.cameraAudios.get(position).getUri());
                        startActivity(i);
                    } else {
                        delete(c);
                    }
                }
                catch (Exception e)
                {
                    delete(c);
                }
            }
        });
    }
    private void delete(CameraResponse c)
    {
        Toast.makeText(CameraAudioList.this, "The File has been deleted", Toast.LENGTH_LONG).show();
        Static_Access.cameraAudios.remove(c);
        writeToFile();
        lv.setAdapter(new net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.ListAdapter(
                CameraAudioList.this,
                R.layout.single_playable_media,
                Static_Access.cameraAudios) {
        });
    }
    private void initializeView() {
        rightFragment = (CameraAudioButtonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragRight);
        showRight = rightFragment != null && rightFragment.isInLayout();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        int viewId = v.getId();
        if (viewId == R.id.calv) {
            getMenuInflater().inflate(R.menu.contextmenu, menu);
            ListView tlv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            CameraResponse obj = (CameraResponse) tlv.getItemAtPosition(acmi.position);
            Static_Access.currentAudio=obj;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_c) {
            Static_Access.cameraAudios.remove(Static_Access.currentAudio);
            writeToFile();
            Intent i = new Intent(this, CameraSaver.class);
            i.putExtra("URI",Static_Access.currentAudio.getUri());
            i.putExtra("EDIT",true);
            i.putExtra("LIST",true);
            i.putExtra("TITLE",Static_Access.currentAudio.getTitle());
            startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.delete_c) {
            String path = FileUtils.getPath(this, Uri.parse(Static_Access.currentAudio.getUri()));
            File f = new File(path);
            Static_Access.cameraAudios.remove(Static_Access.currentAudio);
            writeToFile();
            f.delete();
            lv.setAdapter(new net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.ListAdapter(
                    this,
                    R.layout.single_playable_media,
                    Static_Access.cameraAudios) {
            });
            return true;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onSelectionChanged(CameraResponse item) {
        if (showRight) rightFragment.play(item, this);
        else callRightActivity(item);
    }

    private void callRightActivity(CameraResponse item) {
        Intent intent = new Intent(this, CameraAudioPlayer.class);
        Static_Access.currentAudio=item;
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.headermenu, menu);
        ActionBar a = getSupportActionBar();
        InflaterHelper.inflateLayout(a,linearLayout);
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
    private void writeToFile()
    {
        try {
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("/sdcard/project_eichsteininger_jodlbauer/ca.json")));
            Gson gson = new Gson();
            String toWrite = gson.toJson(Static_Access.cameraAudios);
            out.print(toWrite);
            out.flush();
            out.close();
        } catch (Exception e) {
        }
    }
}
