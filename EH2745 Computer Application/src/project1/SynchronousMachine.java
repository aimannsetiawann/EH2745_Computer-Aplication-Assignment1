package project1;

public class SynchronousMachine {

	private String rdfID;
	private String Name;
	private String EC;
	private double PowerRating;
	private String RC;
	private String GU;
	private double P;
	private double Q;
	private String BV;
	
	public SynchronousMachine (String syncMLrdfID,String syncMLname,String syncMLrating,String ECrDf,String RCrDf,String GUrDf,String syncMP,String syncMQ, String BaseVSMrdfID) {
		
		try {
		rdfID = syncMLrdfID;
		Name = syncMLname;
		EC= ECrDf;
		PowerRating=Double.parseDouble(syncMLrating);
		GU=GUrDf;
		RC=RCrDf;
		P=Double.parseDouble(syncMP);
		Q=Double.parseDouble(syncMQ);
		BV=BaseVSMrdfID;
	
		}
		
		catch (NumberFormatException ex) {}
		
	}
	
	public String GetSMrdfID(){ return rdfID; }
	public String GetSMName(){ return Name; }
	public double GetSMRating(){ return PowerRating; }
	public String GetSMEC(){ return EC; }
	public String GetSMRC(){ return RC; }
	public String GetSMGU(){ return GU; }
	public String GetBVID(){ return BV; }
	public double GetSMP (){ return P; }
	public double GetSMQ(){ return Q; }
}
