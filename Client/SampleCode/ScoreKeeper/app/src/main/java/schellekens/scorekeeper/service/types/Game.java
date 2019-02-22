package schellekens.scorekeeper.service.types;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by bschellekens on 10/27/2017.
 */

public class Game extends ApiResult implements Parcelable {

    private int id;
    private int homeTeamId;
    private int visitingTeamId;
    private int hostingTeamId;
    private String homeTeamName;
    private String visitingTeamName;
    private String hostingTeamName;
    private long kickOff;
    private int duration;
    private int homeTeamScore;
    private int visitingTeamScore;
    private long lastUpdated;

    public DataTypes getDataType(){
        return DataTypes.Game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(int homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public int getVisitingTeamId() {
        return visitingTeamId;
    }

    public void setVisitingTeamId(int visitingTeamId) {
        this.visitingTeamId = visitingTeamId;
    }

    public int getHostingTeamId() {
        return hostingTeamId;
    }

    public void setHostingTeamId(int hostingTeamId) {
        this.hostingTeamId = hostingTeamId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getVisitingTeamName() {
        return visitingTeamName;
    }

    public void setVisitingTeamName(String visitingTeamName) {
        this.visitingTeamName = visitingTeamName;
    }

    public String getHostingTeamName() {
        return hostingTeamName;
    }

    public void setHostingTeamName(String hostingTeamName) {
        this.hostingTeamName = hostingTeamName;
    }

    public long getKickOff() {
        return kickOff;
    }

    public void setKickOff(long kickOff) {
        this.kickOff = kickOff;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getVisitingTeamScore() {
        return visitingTeamScore;
    }

    public void setVisitingTeamScore(int visitingTeamScore) {
        this.visitingTeamScore = visitingTeamScore;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Game Deserialize(String jsonString) {
        return gson.fromJson(jsonString, Game.class);
    }

    public static List<Game> DeserializeList(String jsonString){
        Gson myGson = new Gson();
        return myGson.fromJson(jsonString, new TypeToken<List<Game>>(){}.getType());
    }

    @Override
    public String toString(){
        return gson.toJson(this);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Game> CREATOR = new Creator<Game>() {
        public Game createFromParcel(Parcel source) {
            Game mGame = new Game();
            mGame.id = source.readInt();
            mGame.homeTeamId = source.readInt();
            mGame.visitingTeamId = source.readInt();
            mGame.hostingTeamId = source.readInt();
            mGame.homeTeamName = source.readString();
            mGame.visitingTeamName = source.readString();
            mGame.hostingTeamName = source.readString();
            mGame.kickOff = source.readLong();
            mGame.duration = source.readInt();
            mGame.homeTeamScore = source.readInt();
            mGame.visitingTeamScore = source.readInt();
            mGame.lastUpdated = source.readLong();
            return mGame;
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeInt(homeTeamId);
        parcel.writeInt(visitingTeamId);
        parcel.writeInt(hostingTeamId);
        parcel.writeString(homeTeamName);
        parcel.writeString(visitingTeamName);
        parcel.writeString(hostingTeamName);
        parcel.writeLong(kickOff);
        parcel.writeInt(duration);
        parcel.writeInt(homeTeamScore);
        parcel.writeInt(visitingTeamScore);
        parcel.writeLong(lastUpdated);
    }
}
