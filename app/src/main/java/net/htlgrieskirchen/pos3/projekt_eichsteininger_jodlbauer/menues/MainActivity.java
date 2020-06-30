package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private String path = "/sdcard/project_eichsteininger_jodlbauer/preferences.txt";
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readFromFile();
        request();
        linearLayout = findViewById(R.id.ll_main);
        getSupportActionBar().setTitle("");
        ImageButton btnYouTube = findViewById(R.id.btnYouTube);
        ImageButton btnCamera = findViewById(R.id.btnCamera);
        if (Static_Access.mode.equals("light")) {
            btnCamera.setImageResource(R.drawable.camera_logo_light);
        } else {
            btnCamera.setImageResource(R.drawable.camera_logo_dark);
        }
        btnYouTube.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, YouTubeConvertMenu.class);
            startActivity(intent);
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CameraConvertMenu.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preferencesmenu, menu);
        ActionBar a = getSupportActionBar();
        InflaterHelper.inflateLayout(a, linearLayout);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.gotopreferces) {
            startActivity(new Intent(this, PreferencesMenu.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void request() {
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    Static_Access.RQ_CAMERA);
        } else {
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Static_Access.RQ_SDCARD);
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Static_Access.RQ_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finish();
                System.exit(0);
                //close app?
            } else {
            }
        }
        if (requestCode == Static_Access.RQ_SDCARD) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finish();
                System.exit(0);
                //close app?
            } else {
            }
        }
    }
    private void readFromFile() {
        try
        {
            File file = new File(path);
            String text = "";
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    text += (line);
                }
                br.close();
                text = text.replaceAll("\n", "");
            } catch (IOException e) {
            }
            Static_Access.mode = text;
        }
        catch (Exception e)
        {
        }
    }
}
