package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playlists;

import android.content.Intent;
import android.os.Bundle;
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
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.mediaplayers.CameraAudioPlayer;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.mediaplayers.YouTubeAudioButtonFragment;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.mediaplayers.YouTubeAudioPlayer;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.YouTubeConvertMenu;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.YouTubeEditDialog;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.YouTubeDownload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class YouTubeAudioList extends AppCompatActivity implements  YouTubeAudioFragment.OnSelectionChangedListener{

    private YouTubeAudioButtonFragment rightFragment;
    private boolean showRight = false;
    private LinearLayout linearLayout;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_audio_list);
        linearLayout = findViewById(R.id.ll_yal);
        initializeView();
        lv = findViewById(R.id.lvya);
        registerForContextMenu(lv);
        //checking if files exist
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Static_Access.currentYTAudio=Static_Access.youTubeAudios.get(position);
                String path = Static_Access.currentYTAudio.getPath()+Static_Access.currentYTAudio.getTitle()+".mp3";//exists
                File f = new File(path);
                try {
                    if (f.exists()) {
                        startActivity(new Intent(YouTubeAudioList.this, YouTubeAudioPlayer.class));
                    } else {
                        delete(Static_Access.currentYTAudio);
                    }
                }
                catch (Exception e)
                {
                    delete(Static_Access.currentYTAudio);
                    Toast.makeText(YouTubeAudioList.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void delete(YouTubeDownload y)
    {
        Toast.makeText(YouTubeAudioList.this, "The File has been deleted", Toast.LENGTH_LONG).show();
        Static_Access.youTubeAudios.remove(y);
        writeToFile();
        setAdapter();
    }
    public void setAdapter()
    {
        lv.setAdapter(new net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.YouTubeAdapter(
                YouTubeAudioList.this,
                R.layout.single_playable_media,
                Static_Access.youTubeAudios) {
        });
    }
    private void initializeView() {
        rightFragment = (YouTubeAudioButtonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.yt_frag_right);
        showRight = rightFragment != null && rightFragment.isInLayout();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        int viewId = v.getId();
        if (viewId == R.id.lvya) {
            getMenuInflater().inflate(R.menu.contextmenu, menu);
            ListView tlv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            YouTubeDownload obj = (YouTubeDownload) tlv.getItemAtPosition(acmi.position);
            Static_Access.currentYTAudio=obj;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_c) {
            YouTubeEditDialog youTubeEditDialog = new YouTubeEditDialog(this);
            youTubeEditDialog.show();
            return true;
        }
        if (item.getItemId() == R.id.delete_c) {
            String path = (Static_Access.currentYTAudio.getPath());
            Static_Access.youTubeAudios.remove(Static_Access.currentYTAudio);
            writeToFile();
            File file = new File(path);
            file.delete();
            if (file.exists()){
                try {
                    file.getCanonicalFile().delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (file.exists()){
                    deleteFile(file.getName());
                }
            }
            setAdapter();
            return true;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onSelectionChanged(YouTubeDownload item) {
        if (showRight) rightFragment.play(item, this);
        else callRightActivity(item);
    }

    private void callRightActivity(YouTubeDownload item) {
        Intent intent = new Intent(this, CameraAudioPlayer.class);
        Static_Access.currentYTAudio=item;
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
            try {
                rightFragment.killMP();
            }
            catch(Exception e)
            {

            }
            startActivity(new Intent(this, YouTubeConvertMenu.class));//return to the Intent you came from
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public static void writeToFile()
    {
        try {
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("/sdcard/project_eichsteininger_jodlbauer/yta.json")));
            Gson gson = new Gson();
            String toWrite = gson.toJson(Static_Access.youTubeAudios);
            out.print(toWrite);
            out.flush();
            out.close();
        } catch (Exception e) {
        }
    }
}