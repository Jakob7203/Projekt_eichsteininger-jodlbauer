package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.CameraResponse;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

public class CameraSaver extends AppCompatActivity {
    String TAG = "TAG";
    String path;
    String uri;
    EditText title;
    Button saveAsMP3;
    Button saveAsMP4;
    String pathCameraAudio = "/sdcard/ca.json";
    String pathCameraVideo = "/sdcard/cv.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_saver);
        Intent i = getIntent();
        path=i.getStringExtra("PATH");
        uri=i.getStringExtra("URI");
        title=findViewById(R.id.cameraTitle);
        saveAsMP3=findViewById(R.id.camera_save_mp3);
        saveAsMP4=findViewById(R.id.camera_save_mp4);
        saveAsMP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList(Static_Access.cameraAudio);
                writeToFile(true);
            }
        });
        saveAsMP4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList(Static_Access.cameraVideos);
                writeToFile(false);
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
    private void addToList(List<CameraResponse> list)
    {
        if(!(title.getText().toString().trim().equals("")))
        {
            CameraResponse c = new CameraResponse(title.getText().toString(),uri,path);
            list.add(c);
            title.setText("");
            startActivity(new Intent(CameraSaver.this,MainActivity.class));
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
                String toWrite = gson.toJson(Static_Access.cameraAudio);
                Log.d(TAG, toWrite);
                out.print(toWrite);
                out.flush();
                out.close();
            } catch (Exception e) {
                Log.d(TAG, "write failed");
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
                Log.d(TAG, toWrite);
                out.print(toWrite);
                out.flush();
                out.close();
            } catch (Exception e) {
                Log.d(TAG, "write failed");
            }
        }

    }
}
