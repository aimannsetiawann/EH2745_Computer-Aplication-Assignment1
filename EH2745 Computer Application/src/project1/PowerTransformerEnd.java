package project1;

public class PowerTransformerEnd {
	
	private String rdfID , Name , TransformerID , baseVoltageID;
	private double r , x , b , g , TransformerS , TransformerU , baseVoltage, basePower ;
	private int terminalNumber , NUM;
	

	public PowerTransformerEnd (int TransformerEndNum, int PTETerminalNum,String PTWrdfID , String PTWname,String PTWTransformerrdfID,String PTWBUrdfID,  String PTWr, String PTWx, String PTWb, String PTWg, String PTWS, String PTWU, double PTEbasevoltage, double SystembasePower  ){
		rdfID = PTWrdfID;
		Name=PTWname;
		TransformerID=PTWTransformerrdfID;
		baseVoltageID=PTWBUrdfID;
		r=Double.parseDouble(PTWr);
		x=Double.parseDouble(PTWx);
		b=Double.parseDouble(PTWb);
		g=Double.parseDouble(PTWg);
		TransformerS=Double.parseDouble(PTWS);
		TransformerU=Double.parseDouble(PTWU);
		baseVoltage=PTEbasevoltage;
		basePower=SystembasePower;
		terminalNumber=PTETerminalNum;
		NUM=TransformerEndNum;
	}
	
	public String GetTErdfID(){ return rdfID; }
	public String GetTEName(){ return Name; }
	public String GetTransformerID() {return TransformerID;}
	public String GetbaseVoltageID() {return baseVoltageID;}
	
	public int GetTEterminalNumber(){ return terminalNumber; }
	public int GetTENUM(){ return NUM; }
	
	public double Getr(){ return r; }
	public double Getx(){ return x; }
	public double Getb(){ return b; }
	public double Getg(){ return g; }
	public double GetTransformerS(){ return TransformerS; }
	public double GetTransformerU(){ return TransformerU; }
	public double GetbaseVoltage(){ return baseVoltage; }
	public double GetbasePower(){ return basePower; }
}

	
	


