package mightydanp.techapi.common.jsonconfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IJsonConfig<T> {
    public Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    public String jsonFolderLocation = "";
    public String jsonFolderName = "";
    public Map<String, T> registryMap = new HashMap<>();

    public void initiate() {

    }

    public void initiateClient(){

    }

    public void saveJsonObject(String name, JsonObject jsonConfig){

    }

    public JsonObject getJsonObject(String name){
        return null;
    }

    public List<T> getAllValues() {
        return new ArrayList<>(registryMap.values());
    }

    public void register(T fluidStateIn){

    }

    public T fromJsonObject(JsonObject jsonObjectIn){
        return null;
    }

    public void setJsonFolderLocation(String jsonFolderLocationIn) {
        jsonFolderLocation = jsonFolderLocationIn;
    }

    public String getJsonFolderLocation() {
        return jsonFolderLocation;
    }

    public String getJsonFolderName() {
        return jsonFolderName;
    }

    public void setJsonFolderName(String jsonFolderName) {
        this.jsonFolderName = jsonFolderName;
    }

    public Gson getGSON() {
        return GSON;
    }

    public String fixesToName(Pair<String, String> fixes){
        String prefix = fixes.getFirst().replace("_", "");
        String suffix = fixes.getSecond().replace("_", "");
        String name = "";

        if(!prefix.equals("") && !suffix.equals("")){
            name = prefix + "_" + suffix;
        }

        if(prefix.equals("") && !suffix.equals("")){
            name = suffix;
        }

        if(!prefix.equals("") && suffix.equals("")){
            name = prefix;
        }

        return name;
    }
}