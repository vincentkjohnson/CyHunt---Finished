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

	// @ManyToOne(fetch = FetchType.LAZY, optional = false)
	// @JoinColumn(name = "user_id")
	// @OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne
	@JoinColumn
	private User user;

	@Column(name = "gameDate")
	private long gameDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="gameLocation_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
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

	public GameLocation getGameLocations(){return this.gameLocation;}

	public void setGameLocations(GameLocation gameLocations){this.gameLocation = gameLocations;}
}