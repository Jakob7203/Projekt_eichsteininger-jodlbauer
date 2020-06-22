package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Download;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other.Utils;

import java.io.IOException;

public class Song {
    private final Download download;

    public Song(String youTubeUrl) throws IOException {
        String converter = Utils.convertUrl(youTubeUrl);
        this.download = Utils.getDownload(converter);
    }

    public Download getDownload() {
        return this.download;
    }
}
