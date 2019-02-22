package schellekens.scorekeeper.service.types;

import com.google.gson.Gson;

/**
 * Created by bschellekens on 10/27/2017.
 */

public abstract class ApiResult {
    protected final Gson gson = new Gson();

    public enum DataTypes { Game, League, Score, Team }

    public abstract DataTypes getDataType();

    public abstract ApiResult Deserialize(String jsonString);
}
