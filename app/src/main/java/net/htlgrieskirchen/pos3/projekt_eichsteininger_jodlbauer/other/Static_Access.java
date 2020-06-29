package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.CameraResponse;
import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.YouTubeDownload;

import java.util.ArrayList;
import java.util.List;

public class Static_Access {
    public static List<CameraResponse> cameraVideos = new ArrayList<>();
    public static List<CameraResponse> cameraAudios = new ArrayList<>();
    public static CameraResponse currentAudio = null;
    public static CameraResponse currentVideo = null;
    public static List<YouTubeDownload> youTubeAudios = new ArrayList<>();
    public static YouTubeDownload currentYTAudio = null;
    public static String mode ="light";
    public static final int RQ_SDCARD = 707;
    public static final int RQ_CAMERA = 987;

}
