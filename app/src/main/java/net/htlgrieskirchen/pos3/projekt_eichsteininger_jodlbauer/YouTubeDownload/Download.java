package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.YouTubeDownload;

import java.net.URL;

public class Download {

    private final URL url;
    private final String songTitle;
    private final double totalSize;

    public Download(URL url, String songTitle, double totalSize) {
        this.url = url;
        this.songTitle = songTitle;
        this.totalSize = totalSize;
    }

    public URL getUrl() {
        return this.url;
    }

    public String getSongTitle() {
        return this.songTitle;
    }

    public double getTotalSize() {
        return this.totalSize;
    }
}

