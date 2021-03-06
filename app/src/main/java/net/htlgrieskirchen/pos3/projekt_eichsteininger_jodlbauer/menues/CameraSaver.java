package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.CameraResponse;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playlists.CameraAudioList;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playlists.CameraVideoList;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

public class CameraSaver extends AppCompatActivity {
    private String uri;
    private EditText title;
    private Button saveAsMP3;
    private Button saveAsMP4;
    private String pathCameraAudio = "/sdcard/project_eichsteininger_jodlbauer/ca.json";
    private String pathCameraVideo = "/sdcard/project_eichsteininger_jodlbauer/cv.json";
    private LinearLayout linearLayout;
    private boolean edit;
    private boolean mp3list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_saver);
        linearLayout =findViewById(R.id.ll_cs);
        Intent i = getIntent();
        uri=i.getStringExtra("URI");
        edit=i.getBooleanExtra("EDIT",false);
        mp3list=i.getBooleanExtra("LIST",false);
        title=findViewById(R.id.cameraTitle);
        if(edit)
        {
            title.setText(i.getStringExtra("TITLE"));
        }
        saveAsMP3=findViewById(R.id.camera_save_mp3);
        saveAsMP4=findViewById(R.id.camera_save_mp4);
        if(Static_Access.mode.equals("light"))
        {
            title.setHintTextColor(Color.parseColor("#0d0d0d"));
            title.setTextColor(Color.parseColor("#0d0d0d"));
            saveAsMP3.setTextColor(Color.parseColor("#0d0d0d"));
            saveAsMP4.setTextColor(Color.parseColor("#0d0d0d"));
            saveAsMP3.setBackgroundResource(R.drawable.round_button_light);
            saveAsMP4.setBackgroundResource(R.drawable.round_button_light);

        }
        else
        {
            title.setHintTextColor(Color.parseColor("#f2f2f2"));
            title.setTextColor(Color.parseColor("#f2f2f2"));
            saveAsMP3.setTextColor(Color.parseColor("#f2f2f2"));
            saveAsMP4.setTextColor(Color.parseColor("#f2f2f2"));
            saveAsMP3.setBackgroundResource(R.drawable.round_button_dark);
            saveAsMP4.setBackgroundResource(R.drawable.round_button_dark);
        }
        saveAsMP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList(Static_Access.cameraAudios);
                writeToFile(true);
            }
        });
        saveAsMP4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList(Static_Access.cameraVideos);
                writeToFile(false);
                if(edit)
                {
                    writeToFile(true);
                }
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
    private void addToList(List<CameraResponse> list)
    {
        if(!(title.getText().toString().trim().equals("")))
        {
            CameraResponse c = new CameraResponse(title.getText().toString().trim(),uri);
            list.add(c);
            Collections.sort(list);
            if(edit)
            {
                if(mp3list)
                {
                    startActivity(new Intent(CameraSaver.this, CameraAudioList.class));
                }
                else
                {
                    startActivity(new Intent(CameraSaver.this, CameraVideoList.class));
                }
            }
            else
            {
                startActivity(new Intent(CameraSaver.this,MainActivity.class));
            }
        }
        else
        {
            Toast.makeText(this, "Invalid Title", Toast.LENGTH_LONG);
        }
    }

    private void writeToFile(boolean audio) {
        if(audio)
        {
            try {
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(pathCameraAudio)));
                Gson gson = new Gson();
                String toWrite = gson.toJson(Static_Access.cameraAudios);
                out.print(toWrite);
                out.flush();
                out.close();
            } catch (Exception e) {
            }
        }
        else
        {
            try {
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(pathCameraVideo)));
                Gson gson = new Gson();
                String toWrite = gson.toJson(Static_Access.cameraVideos);
                out.print(toWrite);
                out.flush();
                out.close();
            } catch (Exception e) {
            }
        }

    }
}
