package project1;

public class Terminal {
	
	private String rdfID;
	private int NUM;
	private String tcerdfID;
	private String tcnrdfID;
	private int termConn;
	
	
	public Terminal (String TrdfID , int num,String TCErdfID,String TCNrdfID, int TermConn ){
		rdfID = TrdfID;
		NUM = num;
		tcerdfID=TCErdfID;
		tcnrdfID=TCNrdfID;
		termConn=TermConn;
	}
	
	public String GetTrdfID(){ return rdfID; }
	public int GetTNUM(){ return NUM; }
	public int GetTermConn() {return termConn;}
	public String GetTcerdfID(){ return tcerdfID; }
	public String GetTcnrdfID(){ return tcnrdfID; }


}
