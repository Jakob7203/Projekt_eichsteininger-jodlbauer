package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.YouTubeDownload;

import android.app.NotificationManager;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.YoutubeConvertMenu;

import java.io.File;
import java.io.IOException;
import static android.content.Context.NOTIFICATION_SERVICE;
public class YoutubeVideoDownloadTask extends AsyncTask<String, String, String> {

    private static final String CHANNEL_ID = "channel";

    @Override
    protected String doInBackground(String... parameters) {
        //get download path
        Log.d("DownloadTask", "Task started");
        String path = Utils.formatDownloadPath("/sdcard/");
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
        try {
            song = new Song(parameters[0]);
            songTitle = song.getDownload().getSongTitle();
            String mp3File = Utils.getFilePath(path, songTitle);
            Utils.download(song.getDownload(), mp3File);
        } catch (IOException e) {
            e.printStackTrace();
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.menues.YoutubeConvertMenu.menuInstance.getApplicationContext(), parameters[1])
                .setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setContentTitle("Download finished!")
                .setContentText("Successfully downloaded " + songTitle)
                .setAutoCancel(true);
        int notificationId = 1;
        NotificationManager notificationManager = (NotificationManager) YoutubeConvertMenu.menuInstance.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, mBuilder.build());
        return "";
    }
}
