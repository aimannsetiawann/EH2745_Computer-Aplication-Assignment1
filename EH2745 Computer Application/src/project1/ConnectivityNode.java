package project1;

public class ConnectivityNode {
	
	private String rdfID;
	private String ecrdfID;
	private int NUM;
	
	
	public ConnectivityNode (String CNrdfid , int num,  String ECrdfID){
		rdfID = CNrdfid;
		NUM = num;
		ecrdfID=ECrdfID;
		
	}
	
	public String GetCNrdfID(){ return rdfID; }
	public String GetCNECrdfID(){ return ecrdfID;} 
	public int GetCNNUM(){ return NUM; }

}
