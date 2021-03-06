package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.YouTubeDownload;

import android.view.View;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Utils {

    private static final String DELIMITER = "youtube";
    private static final String CONVERTER = "320";
    private static final String DOWNLOAD_BTN = "a.btn.btn-success.btn-lg";
    private static final String DOWNLOAD_LINK = "abs:href";
    private static final String PARAGRAPH = "p";
    private static final String FILE_FORMAT = ".mp3";

    private static final int SONG_TITLE = 2;
    private static final int SONG_TITLE_BEGIN_INDEX = 7;
    private static final int DOWNLOAD_SIZE = SONG_TITLE + 1;
    private static final int BUFFER = 1024;

    private static int percentage = 0;

    //remove "" or '\' from path
    public static String formatDownloadPath(String path) {
        path = path.replaceAll("\"", "");
        if (path.charAt(path.length()-1) == '\\') path = path.substring(0, path.length()-1);
        return path;
    }

    //get MP3 file path
    public static String getFilePath(String path, String title) {
        return path + title + FILE_FORMAT;
    }

    //get converter URL
    public static String convertUrl(String url) throws StringIndexOutOfBoundsException {
        int index = url.indexOf(DELIMITER);
        StringBuilder convertedUrl = new StringBuilder(url);
        return convertedUrl.insert(index, CONVERTER).toString();
    }

    //get download data
    public static Download getDownload(String url) throws IOException {
        Document pageSource = Jsoup.connect(url).get();
        String downloadUrl = pageSource.select(DOWNLOAD_BTN).first().attr(DOWNLOAD_LINK);
        String songTitle = pageSource.select(PARAGRAPH).get(SONG_TITLE).text();
        String downloadSize = pageSource.select(PARAGRAPH).get(DOWNLOAD_SIZE).text();
        return new Download(new URL(downloadUrl), parseSongTitle(songTitle), parseDownloadSize(downloadSize));
    }

    //parse song title from converter page source
    public static String parseSongTitle(String songTitle) {
        return songTitle.substring(SONG_TITLE_BEGIN_INDEX);
    }

    //parse download size from converter page source
    private static double parseDownloadSize(String downloadSize) {
        downloadSize = downloadSize.replaceAll("[^\\d.]", "");
        double totalBytes;
        try {
            totalBytes = Double.parseDouble(downloadSize) * 1000000D;
        } catch (NumberFormatException e) {
            totalBytes = 0;
        }
        return totalBytes;
    }

    //download file from download URL
    public static void download(Download download, String file, ProgressBar bar) throws IOException {
        double downloadStart = System.currentTimeMillis();

        InputStream in = download.getUrl().openStream();
        File mp3File = new File(file);
        FileOutputStream fos = new FileOutputStream(mp3File);
        double bytesDownloaded = 0;
        double totalSize = download.getTotalSize();
        int prevPercentage = -1;

        int length = -1;
        byte[] buffer = new byte[BUFFER];
        boolean downloadFinished = false;

        try{
            while ((length = in.read(buffer)) > -1) {
                fos.write(buffer, 0, length);
                bytesDownloaded += length;
                percentage = (int) ((bytesDownloaded / totalSize) * 100D);
                if (percentage != prevPercentage && percentage <= 100) downloadFinished = true;
                prevPercentage = percentage;
                updateProgressBar(bar, downloadFinished);
            }
        } catch (Exception e) {}


        fos.close();
        in.close();

        double downloadTime = (System.currentTimeMillis() - downloadStart) / 1000D;
        System.out.println("\n\n" + mp3File.getName() + " download successful (" + downloadTime + "s).\n");
    }

    //exit program
    public static void exit(int status) {
        System.out.println("Exiting program...");
        System.exit(status);
    }

    public static void updateProgressBar(ProgressBar bar, boolean downloadFinished) {
        if (downloadFinished) {
            bar.setVisibility(View.INVISIBLE);
        } else {
            bar.setProgress(percentage, true);
        }
    }
}
