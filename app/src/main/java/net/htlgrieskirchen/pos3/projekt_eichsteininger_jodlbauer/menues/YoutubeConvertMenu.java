package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;

public class YoutubeConvertMenu extends AppCompatActivity {
    private String TAG = "TAG";
    private String durl = "";
    private String path = "/sdcard/";
    private EditText editURL;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_convert_menu);
        linearLayout = findViewById(R.id.ll_ytcm);
        editURL = findViewById(R.id.editURL);
        editURL.setText("https://www.youtube.com/watch?v=0Wc2Og4vr2I");
        String url = editURL.getText().toString().trim();
        Button btnDownloadMP4 = findViewById(R.id.btnDownloadMP4);
        Button btnDownloadMP3 = findViewById(R.id.btnDownloadMP3);
        if(Static_Access.mode.equals("light"))
        {
            editURL.setTextColor(Color.parseColor("#0d0d0d"));
            btnDownloadMP4.setTextColor(Color.parseColor("#0d0d0d"));
            btnDownloadMP3.setTextColor(Color.parseColor("#0d0d0d"));
            btnDownloadMP3.setBackgroundResource(R.drawable.round_button_light);
            btnDownloadMP4.setBackgroundResource(R.drawable.round_button_light);
        }
        else
        {
            editURL.setTextColor(Color.parseColor("#f2f2f2"));
            btnDownloadMP4.setTextColor(Color.parseColor("#f2f2f2"));
            btnDownloadMP3.setTextColor(Color.parseColor("#f2f2f2"));
            btnDownloadMP3.setBackgroundResource(R.drawable.round_button_dark);
            btnDownloadMP4.setBackgroundResource(R.drawable.round_button_dark);
        }
        btnDownloadMP4.setOnClickListener((View v) -> {

        });
        btnDownloadMP3.setOnClickListener((View v) -> {
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
}
