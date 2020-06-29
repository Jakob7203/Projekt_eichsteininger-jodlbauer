package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects;

public class CameraResponse implements Comparable<CameraResponse>{
    private String title;
    private String uri;

    public CameraResponse(String title, String uri) {
        this.title=title;
        this.uri=uri;
    }

    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


    @Override
    public int compareTo(CameraResponse o) {
        return this.getTitle().compareTo(o.getTitle());
    }
}
