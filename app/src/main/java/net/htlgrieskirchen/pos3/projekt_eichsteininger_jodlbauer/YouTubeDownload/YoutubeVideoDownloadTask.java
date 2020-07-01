package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.YouTubeDownload;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.YouTubeConvertMenu;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.YouTubeSaver;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Static_Access;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.YouTubeDownload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collections;

import static android.content.Context.NOTIFICATION_SERVICE;

public class YoutubeVideoDownloadTask extends AsyncTask<String, String, String> {
    private Context c;
    private Activity a;
    private static final String CHANNEL_ID = "channel";

    public YoutubeVideoDownloadTask(Context c, Activity a) {
        this.c = c;
        this.a = a;
    }

    @Override
    protected String doInBackground(String... parameters) {
        //get download path
        Log.d("DownloadTask", "Task started");

        String path = Utils.formatDownloadPath("/sdcard/project_eichsteininger_jodlbauer/youtube_audio/");
        File dir = new File(path);
        Log.d("TAG", dir.getAbsolutePath());
        if (dir.exists() && dir.isDirectory()) System.out.println("Found " + path + ".\n");
        else {
            Log.d("DownloadTask", "Task ended. Cause: DirectoryPath not found");
            System.out.print("Could not locate " + path + "\n");
            Utils.exit(2);
        }

        Song song = null;
        String songTitle = null;
        boolean notify = true;
        try {
            song = new Song(parameters[0]);
            String alternativeSongTitle=parameters[2];
            if(alternativeSongTitle.equals(""))
            {
                songTitle = song.getDownload().getSongTitle();
            }
            else
            {
                songTitle=parameters[2];
            }
            songTitle=songTitle.replaceAll("|"," ").replaceAll("  "," ");
            String mp3File = Utils.getFilePath(path, songTitle);
            File f = new File(mp3File);
            if (!f.exists()) {
                Static_Access.youTubeAudios.add(new YouTubeDownload(songTitle, path,parameters[0]));
                Collections.sort(Static_Access.youTubeAudios);
                writeToFile();
                Utils.download(song.getDownload(), mp3File, YouTubeSaver.prgDownload);
            } else {
                notify = false;
                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(c, "A file with that name already exists", Toast.LENGTH_LONG).show();
                        YouTubeSaver.prgDownload.setVisibility(View.INVISIBLE);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (notify) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(YouTubeSaver.menuInstance.getApplicationContext(), parameters[1])
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                    .setContentTitle("Download finished!")
                    .setContentText("Successfully downloaded " + songTitle)
                    .setAutoCancel(true);
            int notificationId = 1;
            NotificationManager notificationManager = (NotificationManager) YouTubeSaver.menuInstance.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, mBuilder.build());
            c.startActivity(new Intent(c, YouTubeConvertMenu.class));
        }
        return "";
    }
    private void writeToFile()
    {
        Log.d("TAG", "are you getting used?");
        try {
            @SuppressLint("SdCardPath") PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("/sdcard/project_eichsteininger_jodlbauer/yta.json")));
            Gson gson = new Gson();
            String toWrite = gson.toJson(Static_Access.youTubeAudios);
            out.print(toWrite);
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.d("TAG", "the writing fucked up");
        }
    }
}
