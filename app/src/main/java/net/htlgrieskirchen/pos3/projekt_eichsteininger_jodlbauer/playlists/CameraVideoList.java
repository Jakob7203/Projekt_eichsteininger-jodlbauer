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
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.CameraConvertMenu;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.CameraSaver;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.FileUtils;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.ListAdapter;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.CameraResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CameraVideoList extends AppCompatActivity {
    private String TAG = "TAG";
    private ListAdapter adapter;
    private ListView listView;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_video_list);
        linearLayout = findViewById(R.id.ll_cvl);
        listView = findViewById(R.id.cvlv);
        adapter = new ListAdapter(this, R.layout.single_playable_media, Static_Access.cameraVideos);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                CameraResponse c = Static_Access.cameraVideos.get(pos);
                String path = FileUtils.getPath(CameraVideoList.this, Uri.parse(c.getUri()));
                File f = new File(path);
                Toast.makeText(CameraVideoList.this, f.getPath() , Toast.LENGTH_LONG).show();
                /*if(f.exists())
                {
                    Intent i = new Intent(CameraVideoList.this, CameraVideoViewer.class);
                    i.putExtra("URI", Static_Access.cameraVideos.get(pos).getUri());
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(CameraVideoList.this, "The File has been deleted", Toast.LENGTH_LONG).show();
                    Static_Access.cameraVideos.remove(c);
                    writeToFile();
                    listView.setAdapter(new net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.ListAdapter(
                            CameraVideoList.this,
                            R.layout.single_playable_media,
                            Static_Access.cameraVideos) {
                    });
                }*/
            }
        });

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
            startActivity(new Intent(this, CameraConvertMenu.class));//return to the Intent you came from
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        int viewId = v.getId();
        if (viewId == R.id.cvlv) {
            getMenuInflater().inflate(R.menu.contextmenu, menu);
            ListView tlv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            CameraResponse obj = (CameraResponse) tlv.getItemAtPosition(acmi.position);
            Static_Access.currentVideo=obj;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_c) {
            Static_Access.cameraVideos.remove(Static_Access.currentVideo);
            writeToFile();
            Intent i = new Intent(this, CameraSaver.class);
            i.putExtra("URI",Static_Access.currentVideo.getUri());
            i.putExtra("EDIT",true);
            i.putExtra("LIST",false);
            i.putExtra("TITLE",Static_Access.currentVideo.getTitle());
            startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.delete_c) {
            String path = FileUtils.getPath(this, Uri.parse(Static_Access.currentVideo.getUri()));
            File f = new File(path);
            Static_Access.cameraVideos.remove(Static_Access.currentVideo);
            writeToFile();
            f.delete();
            listView.setAdapter(new net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.ListAdapter(
                    this,
                    R.layout.single_playable_media,
                    Static_Access.cameraVideos) {
            });
            return true;
        }
        return super.onContextItemSelected(item);
    }
    private void writeToFile()
    {
        try {
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("/sdcard/project_eichsteininger_jodlbauer/cv.json")));
            Gson gson = new Gson();
            String toWrite = gson.toJson(Static_Access.cameraVideos);
            Log.d(TAG, toWrite);
            out.print(toWrite);
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.d(TAG, "write failed");
        }
    }
}

