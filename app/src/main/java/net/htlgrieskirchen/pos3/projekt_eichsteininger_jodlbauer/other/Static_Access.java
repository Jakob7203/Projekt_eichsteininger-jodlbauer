package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.other;

import net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects.CameraResponse;

import java.util.ArrayList;
import java.util.List;

public class Static_Access {
    public static List<CameraResponse> cameraVideos = new ArrayList<>();
    public static List<CameraResponse> cameraAudio = new ArrayList<>();
    public static CameraResponse currentAudio = null;
    public static String mode ="light";
}
