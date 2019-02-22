package schellekens.scorekeeper.service.types;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by bschellekens on 10/27/2017.
 */

public class Team extends ApiResult implements Parcelable {

    private int id;
    private int leagueId;
    private String leagueName;
    private String name;
    private String homeCity;
    private double latitude;
    private double longitude;

    public DataTypes getDataType() {
        return DataTypes.Team;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public Team Deserialize(String jsonString) {
        return gson.fromJson(jsonString, Team.class);
    }

    public static List<Team> DeserializeList(String jsonString) {
        Gson myGson = new Gson();
        return myGson.fromJson(jsonString, new TypeToken<List<Team>>() {
        }.getType());
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    public static final Parcelable.Creator<Team> CREATOR = new Creator<Team>() {
        public Team createFromParcel(Parcel source) {
            Team mTeam = new Team();
            mTeam.id = source.readInt();
            mTeam.leagueId = source.readInt();
            mTeam.leagueName = source.readString();
            mTeam.name = source.readString();
            mTeam.homeCity = source.readString();
            mTeam.latitude = source.readDouble();
            mTeam.longitude = source.readDouble();
            return mTeam;
        }

        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeInt(leagueId);
        parcel.writeString(leagueName);
        parcel.writeString(name);
        parcel.writeString(homeCity);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }
}