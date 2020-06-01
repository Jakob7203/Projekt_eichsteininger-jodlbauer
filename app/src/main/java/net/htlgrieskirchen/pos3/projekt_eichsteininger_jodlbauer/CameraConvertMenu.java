package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CameraConvertMenu extends AppCompatActivity {
    String TAG = "TAG";
    private static final int RQ_CAMERA = 987;
    Button mp3_converter;
    Button mp4_converter;
    Button record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_convert_menu);
        request();
        mp3_converter = findViewById(R.id.camera_mp3);
        mp4_converter = findViewById(R.id.camera_mp4);
        record = findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, RQ_CAMERA);
                }
            }
        });
        mp3_converter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Static_Access.uri.equals(""))) {
                    Intent i = new Intent(CameraConvertMenu.this, ExperimentalAudioPlayer.class);
                    startActivity(i);
                }
            }
        });
        mp4_converter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Static_Access.uri.equals(""))) {
                    Intent i = new Intent(CameraConvertMenu.this, ExperimentalVideoViewer.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RQ_CAMERA && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            Static_Access.uri = videoUri.toString();//save this string to access the video
            //videoView.setVideoURI(videoUri);
        }
    }

    public void request() {
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // RQ_CAMERA ist just any constant value to identify the request
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    RQ_CAMERA);
        } else {
            Log.d(TAG, "permission already granted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RQ_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "permission denied");
            } else {
                Log.d(TAG, "permission granted");
            }
        }
    }
}
