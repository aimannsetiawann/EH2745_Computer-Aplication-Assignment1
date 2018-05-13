package project1;

public class CircuitBreaker {

	private String rdfID;
	private String Name;
	private String State;
	private String EC;
	private String BrdfID;
	private int NUM;
	private int CNNUM1;
	private int CNNUM2;
	
	public CircuitBreaker(int BreakerNum, String BreakerrdfID,String Breakername ,String BreakerEC,String BreakerState, String CBVrdfID, int BreakerCN1, int BreakerCN2) {
		
		try {
			rdfID = BreakerrdfID;
			Name=Breakername;
			NUM=BreakerNum;
			CNNUM1=BreakerCN1;
			CNNUM2=BreakerCN2;
			EC= BreakerEC;
			BrdfID=CBVrdfID;
			State=BreakerState;
			
		}
		
		catch (NumberFormatException ex) {}
		
	}
	
	public String GetrdfID() {return rdfID;}
	public int GetNUM(){ return NUM; }
	public String GetnamerdfID() {return Name;}
	public String GetECrdfID() {return EC;}
	public String GetState() {return State;}
	public String GetCBVrdfID() {return BrdfID;}
	public int GetCN1(){ return CNNUM1; }
	public int GetCN2(){ return CNNUM2; }
	
}
