package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer;

import android.app.NotificationManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.YouTubeDownload.Utils;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.YouTubeDownload.Song;

import java.io.File;
import java.io.IOException;

import static android.content.Context.NOTIFICATION_SERVICE;

public class YoutubeVideoDownloadTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... parameters) {
        String path = Utils.formatDownloadPath("/sdcard/");
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) System.out.println("Found " + path + ".\n");
        else {
            System.out.print("Could not locate " + path + "\n");
            Utils.exit(2);
        }

        String fileTitle = parameters[1];

        try {
            Song song = new Song(parameters[0]);
            String songTitle = song.getDownload().getSongTitle();
            if (fileTitle.trim().equals("")) fileTitle = songTitle;
            String mp3File = Utils.getFilePath(path, fileTitle);
            Utils.download(song.getDownload(), mp3File, YoutubeConvertMenu.prgDownload);
        } catch (IOException e) {
            e.printStackTrace();
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(YoutubeConvertMenu.menuInstance.getApplicationContext(), parameters[2])
                .setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setContentTitle("Download finished!")
                .setContentText("Successfully downloaded " + fileTitle)
                .setAutoCancel(true);
        int notificationId = 1;
        NotificationManager notificationManager = (NotificationManager) YoutubeConvertMenu.menuInstance.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, mBuilder.build());
        return "";
    }
}
