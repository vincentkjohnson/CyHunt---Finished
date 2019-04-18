package entity;


import javax.persistence.*;
import java.util.List;

@Entity
public class GameLocations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private String date;

    @ManyToMany(mappedBy = "gameLocationsList")
    private List<Building> buildingList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "UserGames-GameLocations", joinColumns = {
            @JoinColumn(name = "GameLocationID", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "UseGamesID", referencedColumnName = "ID")})
    public List<UserGames> userGameses;

    public int getId(){return this.id;}
    public String getDate(){return this.date;}
    public void setDate(String date){this.date =date;}
    public List<Building> getBuildingList(){return this.buildingList; }
    public void setBuildingList(List<Building> buildingList){this.buildingList = buildingList;}
    public List<UserGames> getUserGameses(){return  this.userGameses;}
    public void setUserGameses(List<UserGames> userGameses){this.userGameses = userGameses;}


}
