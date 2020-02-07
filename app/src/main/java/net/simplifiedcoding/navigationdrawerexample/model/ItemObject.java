package net.simplifiedcoding.navigationdrawerexample.model;

public class ItemObject {

    private String content;
    private String f;
    private String g;
    private String e;
    private String imageResource;

    public ItemObject(String content, String imageResource,String f,String g,String e) {
        this.content = content;
        this.imageResource = imageResource;
        this.f = f;
        this.g = g;
        this.e = e;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setf(String f) {
        this.f = f;
    }
    public void setg(String g) {
        this.g = g;
    }
    public void sete(String e) {
        this.e = e;
    }

    public String getImageResource() {
        return imageResource;
    }
    public String getf() {
        return f;
    }
    public String gete() {
        return e;
    }
    public String getg() {
        return g;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }
}
