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

		@Column(name = "user_name")
		private String username;

		@Column(name = "date")
		private String date;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "Buildings")
	   private List<Building> buildingList;

	   @Column(name = "points")
       private int point;

	   public int getId(){return this.id;}
	   public String getUsername(){return this.username;}
	   public void setUsername(String username){this.username = username;}
	   public String getDate(){return this.date;}
	   public void setDate(String date){this.date =date;}
	   public int getPoint(){return point;}
	   public void addPoint(int point){this.point += point;}
	   public void setPoint(int point){this.point =point;}
	   public void reducePoint(int point){this.point -= point;}
	   public List<Building> getBuildingList(){return buildingList;}
	   public void setBuildingList(List<Building> buildingList){this.buildingList = buildingList;}
	   public void addBuilding(Building building){this.buildingList.add(building);}
	   public void removeBuilding(Building target){
	       List<Building> result = new ArrayList<>(buildingList);
	       for(Building building: buildingList)
           {
               if(building.equals(target)) {
                   result.remove(building);
               }
           }
	       buildingList = result;
	   }
	   public void cleanBuilding(){buildingList = new ArrayList<>();}
}
