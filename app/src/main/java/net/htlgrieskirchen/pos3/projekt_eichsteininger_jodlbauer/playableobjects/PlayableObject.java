package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects;

import android.util.Log;

import com.google.gson.Gson;

import java.time.LocalDate;

public  class PlayableObject {
    private String title;
    private String created;
    private String path;

    public PlayableObject()
    {

    }

    public PlayableObject(String title, String path)
    {
        this.title = title;
        this.path = path;
        this.created=LocalDate.now().toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String toJSON()
    {
        String toReturn="";
        Gson gson = new Gson();
        toReturn=gson.toJson(this);
        Log.d("TAG", toReturn);
        return toReturn;
    }
}
