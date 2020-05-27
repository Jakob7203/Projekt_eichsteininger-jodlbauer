package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnYouTube = findViewById(R.id.btnYouTube);
        btnYouTube.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, YoutubeConvertMenu.class);
            startActivity(intent);
        });
        Button btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
