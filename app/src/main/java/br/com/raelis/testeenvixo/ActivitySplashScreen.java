package br.com.raelis.testeenvixo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.JsonReader;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicializa o singleton
        // Estava dando muitos bugs para mandar objetos para outras activities, por isso tive que
        // usar.
        new AppData();

        setContentView(R.layout.splash_screen);

        /*
         * Exibindo splash.
         */
        AsyncTask.execute(() -> {
            // Carrega os dados da HomePage via REST API
            ArrayList<CoursifyCategory> categories = readCategories();
            for(CoursifyCategory cat : categories) {
                List<CoursifyPost> posts = readCategoryPosts(cat.getId(), 5);
                int[] mediaIDs = new int[posts.size()];
                for(int i=0; i<mediaIDs.length; i++) {
                    mediaIDs[i] = posts.get(i).getFeaturedMedia();
                }
                HashMap<Integer, CoursifyMedia> medias = readMedia(mediaIDs);
                for(int i=0; i<mediaIDs.length; i++) {
                    CoursifyMedia m = medias.get(posts.get(i).getFeaturedMedia());
                    posts.get(i).setMedia(m);
                }
                cat.getPosts().addAll(posts);
            }

            Intent i = new Intent(ActivitySplashScreen.this,
                    ActivityHomePage.class);
            // Objeto categories muito grande para ser mandado via putExtra.
            // Estava dando muitos bugs, por isso criei um singleton temporariamente
            AppData.categories = categories;
            startActivity(i);

            // Fecha esta activity
            finish();
        });

    }

    //Lê medias
    public HashMap<Integer, CoursifyMedia> readMedia(int[] IDs) {
        try {
            String stringIDs = "";
            for(int i : IDs) {
                stringIDs += i+",";
            }
            stringIDs = stringIDs.substring(0,stringIDs.length()-1);

            // Create URL
            URL githubEndpoint = new URL("https://blog.coursify.me/wp-json/wp/v2/media/?include=" + stringIDs);

            // Create connection
            HttpsURLConnection myConnection =
                    (HttpsURLConnection) githubEndpoint.openConnection();

            if (myConnection.getResponseCode() == 200) {
                // Success
                InputStream responseBody = myConnection.getInputStream();
                String json = convertStreamToString(responseBody);

                Gson g = new Gson();

                Type listType = new TypeToken<ArrayList<CoursifyMedia>>(){}.getType();
                ArrayList<CoursifyMedia> listMedias = g.fromJson(json, listType);

                HashMap<Integer, CoursifyMedia> map = new HashMap<>();
                for(CoursifyMedia m : listMedias) {
                    map.put(m.getId(),m);
                }

                return map;
            } else {
                // Error handling code goes here
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    //Lê posts dado ID de categoria e número máximo de posts para recuperar
    public ArrayList<CoursifyPost> readCategoryPosts(int categoryID, int maxPostsToRetrieve) {
        try {
            // Create URL
            URL githubEndpoint = new URL("https://blog.coursify.me/wp-json/wp/v2/posts?categories="+categoryID+"&per_page="+maxPostsToRetrieve);

            // Create connection
            HttpsURLConnection myConnection =
                    (HttpsURLConnection) githubEndpoint.openConnection();

            if (myConnection.getResponseCode() == 200) {
                // Success
                InputStream responseBody = myConnection.getInputStream();
                String json = convertStreamToString(responseBody);

                Gson g = new Gson();

                Type listType = new TypeToken<ArrayList<CoursifyPost>>(){}.getType();
                ArrayList<CoursifyPost> listPosts = g.fromJson(json, listType);

                return listPosts;
            } else {
                // Error handling code goes here
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //Lê categorias
    public ArrayList<CoursifyCategory> readCategories() {
        try {
            // Create URL
            URL githubEndpoint = new URL("https://blog.coursify.me/wp-json/wp/v2/categories/");

            // Create connection
            HttpsURLConnection myConnection =
                    (HttpsURLConnection) githubEndpoint.openConnection();

            if (myConnection.getResponseCode() == 200) {
                // Success
                InputStream responseBody = myConnection.getInputStream();
                String json = convertStreamToString(responseBody);

                Gson g = new Gson();

                Type listType = new TypeToken<ArrayList<CoursifyCategory>>(){}.getType();
                ArrayList<CoursifyCategory> listCategories = g.fromJson(json, listType);

                return listCategories;
            } else {
                // Error handling code goes here
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}