package br.com.raelis.testeenvixo;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.JsonAdapter;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

@JsonAdapter(CoursifyMediaDeserializer.class)
public class CoursifyMedia implements Serializable {

    private int id;
    private String titleRendered;
    private String imageURL;
    private Drawable image;

    public CoursifyMedia(int id, String titleRendered, String imageURL) {
        this.id = id;
        this.titleRendered = titleRendered;
        this.imageURL = imageURL;
        image = LoadImageFromWebOperations(imageURL);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleRendered() {
        return titleRendered;
    }

    public void setTitleRendered(String titleRendered) {
        this.titleRendered = titleRendered;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
