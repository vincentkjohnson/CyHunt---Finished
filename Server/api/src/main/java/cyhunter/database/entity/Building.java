/**
 * @author: Zechen Huang
 */package cyhunter.database.entity;

//This table should never been modify unless someday we will decide we need to add more buildings. This table include all the buildings.
import com.sun.org.apache.xpath.internal.operations.Equals;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "building")
public class Building {

    public Building(){
    }

    public Building(String buildingname,String abbreviation,int yearbuilt,String address,Double lattitude,Double longitude){
        this.buildingname = buildingname;
        this.address =address;
        this.abbreviation = abbreviation;
        this.yearbuilt = yearbuilt;
        this.longitude = longitude;
        this.lattitude = lattitude;
    }

    //this is the prime key of every building
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //all that below is the Attributes
    @Column(name = "Name")
    private String buildingname;

    @Column(name = "Abbreviation")
    private String abbreviation;

    @Column(name = "YearBuilt")
    private int yearbuilt;

    @Column(name = "Address")
    private String address;

    @Column(name = "Lattitude")
    private Double lattitude;

    @Column(name = "Longitude")
    private Double longitude;

    // @OneToMany(mappedBy = "gamelocationList")
    // private List<GameLocation> gameLocationsList;

    public int getId(){return id;}
    public String getBuildingName(){return buildingname;}
    public String getAbbreviation(){return abbreviation;}
    public int getYearBuilt(){return yearbuilt;}
    public String getAddress(){return address;}
    public Double getLattitude(){return lattitude;}
    public Double getLongitude(){return longitude;}

    // public List<GameLocation> getGameLocationsList(){return this.gameLocationsList;}

    // public void setGameLocationsList(List<GameLocation> gameLocationsList){this.gameLocationsList = gameLocationsList;}

    public boolean equal(Building target)
    {
        return this.address.equals(target.getAddress())
            && this.buildingname.equals(target.getBuildingName())
            && this.lattitude.equals(target.getLattitude())
            && this.longitude.equals(target.getLongitude())
            && this.yearbuilt == target.getYearBuilt()
            && this.abbreviation.equals(target.getAbbreviation());
    }

    @Override
    public String toString(){
        return "id: "+id+"Building name:" + buildingname + "Abbreviation: "+ abbreviation+ "Year Built: "+ yearbuilt +"Address: " + address+"Lattitude: "+ lattitude+"Longitude: "+longitude+"\n";
    }
}
