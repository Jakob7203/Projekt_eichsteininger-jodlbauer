package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton btnYouTube = findViewById(R.id.btnYouTube);
        btnYouTube.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, YoutubeConvertMenu.class);
            startActivity(intent);
        });
        ImageButton btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CameraConvertMenu.class);
            startActivity(i);
        });

    }
}
