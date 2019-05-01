/**
 * @author: Zechen Huang
 */package cyhunter.database.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
public class GameLocation {
    //this is the prime key of every game location
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    //all that below is the Attributes

    @Column(name = "gameDate")
    private long gameDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="building_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Building building;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserGame> userGames;

    public int getId(){return this.id;}

    public long getDate(){return this.gameDate;}

    public void setDate(long date){this.gameDate =date;}

    public Building getBuilding(){return this.building; }

    public void setBuilding(Building bldg){this.building = bldg;}
}