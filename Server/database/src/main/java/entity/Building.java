package entity;

//This table should never been modify unless someday we will decide we need to add more buildings. This table include all the buildings.
import javax.persistence.*;

@Entity
@Table(name = "building")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "Name")
    private String buildingname;

    @Column(name = "Abbreviation")
    private String abbreviation;

    @Column(name = "YearBuilt")
    private int yearbuilt;

    @Column(name = "Address")
    private String address;

    @Column(name = "Lattitude")
    private String lattitude;

    @Column(name = "Longitude")
    private String longitude;


    public Building(){}
    public Building(String buildingname,String abbreviation,int yearbuilt,String address,String lattitude,String longitude){
        this.buildingname = buildingname;
        this.address =address;
        this.abbreviation = abbreviation;
        this.yearbuilt = yearbuilt;
        this.longitude = longitude;
        this.lattitude = lattitude;
    }
    public int getId(){return id;}
    public String getBuildingName(){return buildingname;}
    public String getAbbreviation(){return abbreviation;}
    public int getYearBuilt(){return yearbuilt;}
    public String getAddress(){return address;}
    public String getLattitude(){return lattitude;}
    public String getLongitude(){return longitude;}

    public boolean equal(Building target)
    {
        if(this.address == target.getAddress() &&this.buildingname == target.getBuildingName()&&this.lattitude == target.getLattitude()&&
        this.longitude == target.getLongitude()&&this.yearbuilt == target.getYearBuilt()&& this.abbreviation == target.getAbbreviation())
            return true;
        else
            return false;
    }
    @Override
    public String toString(){
        return "id: "+id+"Building name:" + buildingname + "Abbreviation: "+ abbreviation+ "Year Built: "+ yearbuilt +"Address: " + address+"Lattitude: "+ lattitude+"Longitude: "+longitude+"\n";
    }
}
