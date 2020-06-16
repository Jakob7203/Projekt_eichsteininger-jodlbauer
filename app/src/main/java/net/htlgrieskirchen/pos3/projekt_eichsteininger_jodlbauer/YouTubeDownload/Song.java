package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.YouTubeDownload;

import java.io.IOException;

public class Video {

    private final Download download;

    public Video(String youTubeUrl) throws IOException {
        String converter = Utils.convertUrl(youTubeUrl);
        this.download = Utils.getDownload(converter);
    }

    public Download getDownload() {
        return this.download;
    }
}
