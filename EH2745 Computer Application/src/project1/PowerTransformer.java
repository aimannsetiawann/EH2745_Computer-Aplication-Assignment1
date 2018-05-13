package project1;

public class PowerTransformer {
	private String rdfID , Name, EC;
	private int CN1,CN2;
	Complexnumber y,ysh;
	
	public PowerTransformer (String PTrdfID,String PTname,String PTECrdf,int PTCN1,int PTCN2,Complexnumber Y,Complexnumber Ysh)
	{
		rdfID=PTrdfID;
		Name=PTname; 
		CN1=PTCN1;
		CN2=PTCN2;
		y=Y;
		ysh=Ysh;
		EC=PTECrdf;
		
		
	}
	
	public int GetCN1(){ return CN1; }
	public int GetCN2(){ return CN2; }
	
	
	public String GetrdfID() {return rdfID;}
	public String Getname() {return Name;}
	public String GetECID() {return EC;}
	
	public Complexnumber GetY() {return y;}
	public Complexnumber GetYsh() {return ysh;}
	
	
	
	
	
}
