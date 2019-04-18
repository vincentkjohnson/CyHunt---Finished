package entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usergame")
public class UserGames {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToMany(mappedBy = "userGameses")
	private List<User> users;

	@Column(name = "date")
	private String date;

	@ManyToMany(mappedBy = "userGameses")
	private List<GameLocations> gameLocations;

	@Column(name = "points")
	private int point;

	public UserGames(){}
	public int getId() {
		return this.id;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPoint() {
		return point;
	}

	public void addPoint(int point) {
		this.point += point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public void reducePoint(int point) {
		this.point -= point;
	}

	public List<GameLocations> getGameLocations(){return this.gameLocations;}
	public void setGameLocations(List<GameLocations> gameLocations){this.gameLocations = gameLocations;}
}
