package cyhunter.database.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usergame")
public class UserGame {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn
	private User user;

	@Column(name = "gameDate")
	private long gameDate;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn
	private GameLocation gameLocation;

	@Column(name = "points")
	private int point;

	public UserGame(){}

	public int getId() {
		return this.id;
	}

	public long getDate() {
		return this.gameDate;
	}

	public void setDate(long date) {
		this.gameDate = date;
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

	public GameLocation getGameLocation(){return this.gameLocation;}

	public void setGameLocation(GameLocation gameLocations){this.gameLocation = gameLocations;}

	public User getUser() { return this.user; }

	public void setUser(User u) { this.user = u; }
}