package br.com.raelis.testeenvixo;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class CoursifyPostDeserializer implements JsonDeserializer<CoursifyPost> {

    @Override
    public CoursifyPost deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();

        String titleRendered = jsonObject.get("title").getAsJsonObject().get("rendered").getAsString();

        String status = jsonObject.get("status").getAsString();
        int featured_media = jsonObject.get("featured_media").getAsInt();
        String content_rendered = jsonObject.get("content").getAsJsonObject().get("rendered").getAsString();
        return new CoursifyPost(id, titleRendered, status, featured_media, content_rendered);
    }
}
