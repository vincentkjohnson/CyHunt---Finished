package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER-USERGAMES", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "USERGAMES_ID", referencedColumnName = "ID")})
    private List<UserGames> userGameses;

    @Column(name = "totalpoints")
    private int points;

    public User(){
    }


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserGames> getUserGameses(){return userGameses;}
    public void setUserGameses(List<UserGames> userGameses){this.userGameses = userGameses;}
    public String getUserName() {
        return username;
    }

    public void setUser_name(String user_name) {
        this.username = user_name;
    }

    public int getPoints(){return this.points;}
    public void setPoints(int points){this.points=points;}
    public void addPoints(int points){this.points =this.points+points;}
    public void reducePoint(int point) {
        this.points -= point;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_name='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
