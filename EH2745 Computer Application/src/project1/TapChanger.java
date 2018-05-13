package project1;

public class TapChanger {
	
	private String rdfID;
	private String Name;
	private double Step;
	
	public TapChanger(String tapChangerrdfID,String tapChangername ,String tapChangerstep) {
		
		try {
			rdfID =tapChangerrdfID;
			Name = tapChangername;
			Step = Double.parseDouble( tapChangerstep);
			
		}
		
		catch (NumberFormatException ex) {}
		
	}
	
	public String GetrdfID() {return rdfID;}
	public String GetnamerdfID() {return Name;}
	public double GetStep() {return Step;}
	
}
