package br.com.raelis.testeenvixo;

import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonAdapter(CoursifyCategoryDeserializer.class)
public class CoursifyCategory implements Serializable {

    private int id;
    private String name;
    private ArrayList<CoursifyPost> posts = new ArrayList<>();

    public CoursifyCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CoursifyPost> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<CoursifyPost> posts) {
        this.posts = posts;
    }
}
