package cyhunter.database.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserGame> userGames;

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

    public List<UserGame> getUserGame(){return userGames;}

    public void setUserGame(List<UserGame> userGame){this.userGames = userGame;}

    public String getUserName() {
        return username;
    }

    public void setUser_name(String user_name) {
        this.username = user_name;
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
