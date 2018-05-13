package project1;

public class GeneratingUnit {
	
	private String rdfID;
	private String Name;
	private String EC;
	private double Pmax;
	private double Pmin;
	
	
	public GeneratingUnit (String GUsrdfID,String GUname,String GUMaxP,String GUMinP,String GUECrdfID) {
		
		try {
		rdfID = GUsrdfID;
		Name = GUname;
		EC= GUECrdfID;
		Pmax=Double.parseDouble(GUMaxP);
		Pmin=Double.parseDouble(GUMinP);
		
		}
		
		catch (NumberFormatException ex) {}
		
	}
	
	public String GetGUrdfID(){ return rdfID; }
	public String GetGUName(){ return Name; }
	public String GetGUEC(){ return EC; }
	public double GetGUPmax (){ return Pmax; }
	public double GetGUPmin(){ return Pmin; }

}
