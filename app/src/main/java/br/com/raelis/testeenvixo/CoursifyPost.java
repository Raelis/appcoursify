package br.com.raelis.testeenvixo;

import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;

@JsonAdapter(CoursifyPostDeserializer.class)
public class CoursifyPost implements Serializable {

    private int id;
    private String titleRendered;
    private String status;
    private int featuredMedia;
    private String contentRendered;
    private CoursifyMedia media;

    public CoursifyPost(int id, String titleRendered, String status, int featuredMedia, String contentRendered) {
        this.id = id;
        this.titleRendered = titleRendered;
        this.status = status;
        this.featuredMedia = featuredMedia;
        this.contentRendered = contentRendered;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFeaturedMedia() {
        return featuredMedia;
    }

    public void setFeaturedMedia(int featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    public String getContentRendered() {
        return contentRendered;
    }

    public void setContentRendered(String contentRendered) {
        this.contentRendered = contentRendered;
    }

    public CoursifyMedia getMedia() {
        return media;
    }

    public void setMedia(CoursifyMedia media) {
        this.media = media;
    }
}
