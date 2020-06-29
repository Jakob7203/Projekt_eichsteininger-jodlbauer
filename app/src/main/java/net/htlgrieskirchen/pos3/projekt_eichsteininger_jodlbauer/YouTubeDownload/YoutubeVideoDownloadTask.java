package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.YouTubeDownload;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.YouTubeConvertMenu;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.YouTubeSaver;

import java.io.File;
import java.io.IOException;

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
            String mp3File = Utils.getFilePath(path, songTitle);
            File f = new File(mp3File);
            Log.d("NACHT", f.exists() + "");
            if (!f.exists()) {
                Utils.download(song.getDownload(), mp3File);
            } else {
                notify = false;
                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(c, "A file with that name already exists", Toast.LENGTH_LONG).show();
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
}
