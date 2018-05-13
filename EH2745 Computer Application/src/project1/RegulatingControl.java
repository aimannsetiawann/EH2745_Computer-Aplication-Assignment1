package project1;

public class RegulatingControl {

	private String rdfID;
	private String Name;
	private double TargetValue;
	
	public RegulatingControl(String RCrdfID,String RCname ,String RCSSHname) {
		
		try {
			rdfID = RCrdfID;
			Name = RCname;
			TargetValue=Double.parseDouble(RCSSHname);
		}
		
		catch (NumberFormatException ex) {}
		
	}
	
	public String GetRCrdfID(){ return rdfID; }
	public String GetRCName(){ return Name; }
	public double GetRCTV(){ return TargetValue; }
	
}
