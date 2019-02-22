package schellekens.scorekeeper.service.types;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by bschellekens on 10/27/2017.
 */

public class League extends ApiResult {

    private int id;
    private String name;

    public DataTypes getDataType(){
        return DataTypes.League;
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

    @Override
    public League Deserialize(String jsonString) {
        return gson.fromJson(jsonString, League.class);
    }

    @Override
    public String toString(){
        return gson.toJson(this);
    }

    public static List<League> DeserializeList(String jsonString){
        Gson myGson = new Gson();
        return myGson.fromJson(jsonString, new TypeToken<List<League>>(){}.getType());
    }
}