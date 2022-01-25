package mightydanp.industrialtech.api.common.jsonconfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MightyDanp on 1/18/2022.
 */
public class JsonConfigMultiFile {
    private final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private String jsonFolderLocation;
    private String jsonFolderName;
    public final Map<String, ?> jsonFiles = new HashMap<>();

    public void initiate(){

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

    public Map<String, ?> getJsonFiles() {
        return jsonFiles;
    }

    public Gson getGSON() {
        return GSON;
    }

    public void saveJsonObject(String name, JsonObject jsonConfig) {
        Path file = Paths.get(getJsonFolderLocation() + "/" + jsonFolderName + "/" + name + ".json");
        if(!getJsonObject(name).equals(jsonConfig)) {
            try {
                file.toFile().delete();
                Files.createDirectories(file.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(file)) {
                    String s = getGSON().toJson(jsonConfig);
                    bufferedwriter.write(s);
                }
            } catch (IOException ioexception) {
                IndustrialTech.LOGGER.error(name + " cannot be deleted /or created [{}]", file.getFileName().toString().replace(".json", ""), ioexception);
            }
        }
    }

    public JsonObject getJsonObject(String name) {
        JsonObject jsonObject = new JsonObject();
        Path file;
        if(name.contains(".json")){
            file = Paths.get(getJsonFolderLocation() + "/" + jsonFolderName + "/" + name);
        }else{
            file = Paths.get(getJsonFolderLocation() + "/" + jsonFolderName + "/" + name + ".json");
        }

        try {
            if (Files.exists(file)) {
                try (BufferedReader bufferedReader = Files.newBufferedReader(file)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                        //System.out.println(line);
                    }

                    jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();

                    return jsonObject;
                }
            }else{
                return new JsonObject();
            }
        } catch (IOException ioexception) {
            IndustrialTech.LOGGER.error("Couldn't read json {}", file.getFileName().toString().replace(".json", ""), ioexception);
        }
        return jsonObject;
    }
}