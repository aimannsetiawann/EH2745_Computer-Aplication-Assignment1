package project1;

public class EnergyConsumer {
	
	private String rdfID;
	private String Name;
	private String ECEC;
	private String BrdfID;
	
	private double P;
	private double Q;
	
	public EnergyConsumer(String ECrdfID,String ECname ,String EnergyConsEC, String ECP, String ECQ, String BaseVrdfID) {
		
		try {
			rdfID = ECrdfID;
			Name = ECname;
			ECEC= EnergyConsEC;
			BrdfID=BaseVrdfID;
					
			P=Double.parseDouble(ECP);
			Q=Double.parseDouble(ECQ);
		}
		
		catch (NumberFormatException ex) {}
		
	}
	
	public String GetrdfID() {return rdfID;}
	public String GetnamerdfID() {return Name;}
	public String GetECrdfID() {return ECEC;}
	public String GetBVrdfID() {return BrdfID;}
	
	public double GetP() {return P;}
	public double GetQ() {return Q;}
	


	
}

