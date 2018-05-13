package project1;

public class Substation {
	

	private String SubsRDFID;
	private String Name;
	private String Region;
	
	
	public Substation(String SubsrdfID, String Subsname, String SubRsrdfID) {
		
		try {
		SubsRDFID = SubsrdfID;
		Name = Subsname;
		Region=SubRsrdfID;
		
		}
		
		catch (NumberFormatException ex) {}
		
	}
	
	public String SubsRDFID(){ return SubsRDFID; }
	public String GetName(){ return Name; }
	public String SubsRegion(){ return Region; }

}
