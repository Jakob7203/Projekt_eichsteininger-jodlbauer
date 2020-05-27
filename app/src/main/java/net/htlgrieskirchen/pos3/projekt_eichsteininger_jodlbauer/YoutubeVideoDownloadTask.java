package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YoutubeVideoDownloadTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... parameters) {
        //.com/VideoID/Format/...
        //Example: "https://free-mp3-mp4-youtube.p.rapidapi.com/jfxcJH_OWgE/MP4/spinner/2196f3/100/box-button/2196f3/tiny-button/Download/FFFFFF/yes/FFFFFF/none"
        String apiUrl = "https://free-mp3-mp4-youtube.p.rapidapi.com/" + parameters[0] + "/" + parameters[1] + "/spinner/2196f3/100/box-button/2196f3/tiny-button/Download/FFFFFF/yes/FFFFFF/none";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(apiUrl)
                .get()
                .addHeader("x-rapidapi-host", "free-mp3-mp4-youtube.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "c58485f4b3mshd00bd15ab7fe81ap17b4b4jsne8a3655f9a71")
                .build();

        String downloadURL = "";
        try {
            Response response = client.newCall(request).execute();
            JSONObject obj = new JSONObject(response.body().string());
            downloadURL = obj.getString("url");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return downloadURL;
    }
}
