
public class SewersNode {
	int index;
	double lon;
    double lat;
    String color;
    String type;
    
    public SewersNode(int index, double lon, double lat, String color, String type) {
    	this.index = index;
    	this.lon = lon;
    	this.lat = lat;
    	this.color = color;
    	this.type = type;	
    }
    
    public String createInsert() {
		return "insert into sewersNode values ("
    			+ index + ", "
    			+ lon + ", "
    			+ lat + ", \""
    			+ color + "\", \""
    			+ type + "\");";
    }
}
