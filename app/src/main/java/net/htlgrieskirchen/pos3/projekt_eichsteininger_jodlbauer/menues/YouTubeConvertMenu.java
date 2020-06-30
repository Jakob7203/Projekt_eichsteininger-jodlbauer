package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.YouTubeDownload;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playlists.YouTubeAudioList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YouTubeConvertMenu extends AppCompatActivity {
    private LinearLayout linearLayout;
    private Button ytLib;
    private Button convertNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_convert_menu);
        linearLayout=findViewById(R.id.ll_ycm);
        ytLib=findViewById(R.id.yt_audio_lib);
        convertNew=findViewById(R.id.convert_new_yt);
        if(Static_Access.mode.equals("light"))
        {
            ytLib.setTextColor(Color.parseColor("#0d0d0d"));
            ytLib.setBackgroundResource(R.drawable.round_button_light);
            convertNew.setTextColor(Color.parseColor("#0d0d0d"));
            convertNew.setBackgroundResource(R.drawable.round_button_light);
        }
        else
        {
            ytLib.setTextColor(Color.parseColor("#f2f2f2"));
            ytLib.setBackgroundResource(R.drawable.round_button_dark);
            convertNew.setTextColor(Color.parseColor("#f2f2f2"));
            convertNew.setBackgroundResource(R.drawable.round_button_dark);
        }
        readFromFile();
        ytLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YouTubeConvertMenu.this, YouTubeAudioList.class));
            }
        });
        convertNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YouTubeConvertMenu.this, YouTubeSaver.class));
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
            startActivity(new Intent(this, MainActivity.class));//return to the Intent you came from
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void readFromFile() {
        try {
            File file = new File("/sdcard/project_eichsteininger_jodlbauer/yta.json");
            String text = "";
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    text += (line);
                }
                br.close();
                text = "{\"list\":" + text + "}";
            } catch (IOException e) {
            }
            try {
                List<YouTubeDownload> youTubeDownloads = new ArrayList<>();
                JSONObject jsonObject = new JSONObject(text);
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = (JSONObject) jsonArray.get(i);
                    String title = o.getString("title");;
                    String path = o.getString("path");
                    String link = o.getString("link");
                    youTubeDownloads.add(new YouTubeDownload(title, path,link));
                }
                Static_Access.youTubeAudios = youTubeDownloads;
            } catch (Exception e) {
            }
        }
        catch (Exception e)
        {
        }
    }
}
