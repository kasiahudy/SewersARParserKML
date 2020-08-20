
public class SewersPipe {
	int index;
    int startNodeIndex;
    int endNodeIndex;
    String color;
    
    public SewersPipe(int index, int startNodeIndex, int endNodeIndex, String color) {
    	this.index = index;
    	this.startNodeIndex = startNodeIndex;
    	this.endNodeIndex = endNodeIndex;
    	this.color = color;
    }
    
    public String createInsert() {
		return "insert into sewersPipe values ("
    			+ index + ", "
    			+ startNodeIndex + ", "
    			+ endNodeIndex + ", \""
    			+ color + "\");";
    }
}
