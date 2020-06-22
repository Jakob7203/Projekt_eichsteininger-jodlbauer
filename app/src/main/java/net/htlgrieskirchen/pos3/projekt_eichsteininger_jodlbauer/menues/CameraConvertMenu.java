package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.R;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.InflaterHelper;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.CameraResponse;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playlists.CameraAudioList;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playlists.CameraVideoList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraConvertMenu extends AppCompatActivity {
    private String TAG = "TAG";
    private String pathCameraAudio = "/sdcard/ca.json";
    private String pathCameraVideo = "/sdcard/cv.json";
    private static final int RQ_CAMERA = 987;
    private static final int RQ_SDCARD = 707;
    private Button mp3_lib;
    private Button mp4_lib;
    private ImageButton record;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_convert_menu);
        linearLayout = findViewById(R.id.ll_ccm);
        request();
        readFromFile();
        mp3_lib = findViewById(R.id.camera_lib_mp3);
        mp4_lib = findViewById(R.id.camera_lib_mp4);
        record = findViewById(R.id.record);
        if(Static_Access.mode.equals("light"))
        {
            record.setImageResource(R.drawable.camera_button_light);
            mp3_lib.setTextColor(Color.parseColor("#0d0d0d"));
            mp4_lib.setTextColor(Color.parseColor("#0d0d0d"));
            mp3_lib.setBackgroundResource(R.drawable.round_button_light);
            mp4_lib.setBackgroundResource(R.drawable.round_button_light);
        }
        else
        {
            record.setImageResource(R.drawable.camera_button_dark);
            mp3_lib.setTextColor(Color.parseColor("#f2f2f2"));
            mp4_lib.setTextColor(Color.parseColor("#f2f2f2"));
            mp3_lib.setBackgroundResource(R.drawable.round_button_dark);
            mp4_lib.setBackgroundResource(R.drawable.round_button_dark);
        }
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, RQ_CAMERA);
                }
            }
        });
        mp3_lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CameraConvertMenu.this, CameraAudioList.class));
            }
        });
        mp4_lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CameraConvertMenu.this, CameraVideoList.class));
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RQ_CAMERA && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            Log.d(TAG, videoUri.toString());
            String filePath = "file://" + getFilesDir()+File.separator+getPath(this,videoUri);//check if you can convert this back to uri
            Intent i = new Intent(this, CameraSaver.class);
            i.putExtra("PATH",filePath);
            i.putExtra("URI",videoUri.toString());
            Log.d(TAG, filePath);
            startActivity(i);
        }
    }

    public void request() {
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    RQ_CAMERA);
        } else {
            Log.d(TAG, "permission for Camera already granted");
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RQ_SDCARD);
        } else {
            Log.d(TAG, "permission for SD-Card already granted");
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
        if (requestCode == RQ_SDCARD) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "permission denied");
            } else {
                Log.d(TAG, "permission granted");
            }
        }
    }


    private void readFromFile() {
        try {
            File file = new File(pathCameraAudio);
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
                Log.d(TAG, "read failed");
            }
            try {
                List<CameraResponse> tempcaudio = new ArrayList<>();
                JSONObject jsonObject = new JSONObject(text);
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = (JSONObject) jsonArray.get(i);
                    String title = o.getString("title");
                    String created = o.getString("created");
                    String path = o.getString("path");
                    String uri = o.getString("uri");
                    tempcaudio.add(new CameraResponse(title, created, path, uri));
                }
                Static_Access.cameraAudio = tempcaudio;
            } catch (Exception e) {
                Log.d(TAG, "Something went wrong");
            }
            file = new File(pathCameraVideo);
            text = "";
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    text += (line);
                }
                br.close();
                text = "{\"list\":" + text + "}";
            } catch (IOException e) {
                Log.d(TAG, "read failed");
            }
            try {
                List<CameraResponse> tempcvideo = new ArrayList<>();
                JSONObject jsonObject = new JSONObject(text);
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = (JSONObject) jsonArray.get(i);
                    String title = o.getString("title");
                    String created = o.getString("created");
                    String path = o.getString("path");
                    String uri = o.getString("uri");
                    tempcvideo.add(new CameraResponse(title, created, path, uri));
                }
                Static_Access.cameraVideos = tempcvideo;
            } catch (Exception e) {
                Log.d(TAG, "Something went wrong");
            }
        }
        catch (Exception e)
        {
            Log.d(TAG, "Files dont exist yet");
        }
    }
    //wird eig. nicht gebraucht//eig schon
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        Log.i("URI",uri+"");
        String result = uri+"";
        if (isKitKat && (result.contains("media.documents"))) {
            String[] ary = result.split("/");
            int length = ary.length;
            String imgary = ary[length-1];
            final String[] dat = imgary.split("%3A");
            final String docId = dat[1];
            final String type = dat[0];
            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
            } else if ("audio".equals(type)) {
            }
            final String selection = "_id=?";
            final String[] selectionArgs = new String[] {
                    dat[1]
            };
            return getDataColumn(context, contentUri, selection, selectionArgs);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    //wird eig nicht gebraucht
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}
