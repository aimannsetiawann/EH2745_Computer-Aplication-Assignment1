package project1;

public class Basevoltage {
	
	private String BVrdfID;
	private double BaseV;
	
	
	public Basevoltage(String BaseVrdfID, String BaseVname) {
		
		try {
		BVrdfID = BaseVrdfID;
		BaseV = Double.parseDouble(BaseVname);
		
		}
		
		catch (NumberFormatException ex) {}
		
	}
	
	public double GetBV(){ return BaseV; }
	public String GetrdfID(){ return BVrdfID; }
	
	
}
