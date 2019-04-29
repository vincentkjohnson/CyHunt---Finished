package cyhunter.database.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class GameLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "gameDate")
    private long gameDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="building_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Building building;

    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name="userGame_id")
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // private UserGame userGame;

    public int getId(){return this.id;}

    public long getDate(){return this.gameDate;}

    public void setDate(long date){this.gameDate =date;}

    public Building getBuilding(){return this.building; }

    public void setBuilding(Building bldg){this.building = bldg;}

    // public UserGame getUserGame(){return  this.userGame;}
    // public void setUserGame(UserGame ugame){this.userGame = ugame;}
}