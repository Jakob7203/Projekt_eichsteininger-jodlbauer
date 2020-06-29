package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects;

import android.util.Log;

import com.google.gson.Gson;


public class CameraResponse implements Comparable<CameraResponse>{
    private String title;
    private String path;
    private String uri;

    public CameraResponse(String title, String uri, String path) {
        this.title=title;
        this.uri=uri;
        this.path=path;
    }
    public CameraResponse(String fromJSON)
    {
        Gson gson = new Gson();
        CameraResponse temp =gson.fromJson(fromJSON,CameraResponse.class);
        this.title=temp.getTitle();
        this.path=temp.getPath();
        this.uri=temp.getUri();
        temp=null;
    }
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String toJSON()
    {
        String toReturn="";
        Gson gson = new Gson();
        toReturn=gson.toJson(this);
        Log.d("TAG", toReturn);
        return toReturn;
    }

    @Override
    public int compareTo(CameraResponse o) {
        return this.getTitle().compareTo(o.getTitle());
    }
}
