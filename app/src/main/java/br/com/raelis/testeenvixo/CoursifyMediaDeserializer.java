package br.com.raelis.testeenvixo;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class CoursifyMediaDeserializer implements JsonDeserializer<CoursifyMedia> {

    @Override
    public CoursifyMedia deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        String titleRendered = jsonObject.get("title").getAsJsonObject().get("rendered").getAsString();
        String imageURL = jsonObject.get("media_details").getAsJsonObject().get("sizes").getAsJsonObject().get("medium").getAsJsonObject().get("source_url").getAsString();
        //Object mediaDetailsSizes =
        return new CoursifyMedia(id, titleRendered, imageURL);
    }
}
