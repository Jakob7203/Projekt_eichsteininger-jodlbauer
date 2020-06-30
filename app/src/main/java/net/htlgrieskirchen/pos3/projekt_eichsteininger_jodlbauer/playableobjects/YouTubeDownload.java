package net.htlgrieskirchen.pos3.projekt_eichsteininger_jodlbauer.playableobjects;

public class YouTubeDownload implements Comparable<YouTubeDownload>{
    private String title;
    private String path;
    private String link;

    public YouTubeDownload(String title, String path, String link) {
        this.title = title;
        this.path = path;
        this.link = link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int compareTo(YouTubeDownload o) {
        return this.title.compareTo(o.getTitle());
    }
}
