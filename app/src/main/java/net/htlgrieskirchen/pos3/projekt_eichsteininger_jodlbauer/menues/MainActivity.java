package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;

public class MainActivity extends AppCompatActivity {
    private String TAG = "TAG";
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout=findViewById(R.id.ll_main);
        getSupportActionBar().setTitle("");
        ImageButton btnYouTube = findViewById(R.id.btnYouTube);
        btnYouTube.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, YoutubeConvertMenu.class);
            startActivity(intent);
        });
        ImageButton btnCamera = findViewById(R.id.btnCamera);
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
}
