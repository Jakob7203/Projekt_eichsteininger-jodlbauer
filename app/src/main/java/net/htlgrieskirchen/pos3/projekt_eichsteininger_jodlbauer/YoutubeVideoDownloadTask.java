package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer;

import android.os.AsyncTask;
import android.util.Log;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.YouTubeDownload.Utils;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.YouTubeDownload.Song;

import java.io.File;
import java.io.IOException;


public class YoutubeVideoDownloadTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... parameters) {
        //get download path
        Log.d("DownloadTask", "Task started");
        String path = Utils.formatDownloadPath("/Downloads");
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) System.out.println("Found " + path + ".\n");
        else {
            Log.d("DownloadTask", "Task ended. Cause: DirectoryPath not found");
            System.out.print("Could not locate " + path + "\n");
            Utils.exit(2);
        }

        Song song = null;
        try {
            song = new Song(parameters[0]);
            String songTitle = song.getDownload().getSongTitle();
            String mp3File = Utils.getFilePath(path, songTitle);
            Utils.download(song.getDownload(), mp3File);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
