package project1;

public class ACLine {

	String ID;
	private int NUM;
	private int CNNUM1;
	private int CNNUM2;
	Complexnumber Y;
	Complexnumber Ysh;
	
 
	public ACLine (int LineNUM ,String LinerdfID, int LineCNode1, int LineCNode2, Complexnumber YlinePU, Complexnumber YshlinePU ){
		 
		 ID=LinerdfID;
		 NUM=LineNUM;
		 CNNUM1=LineCNode1;
		 CNNUM2=LineCNode2;
		 Y=YlinePU;
		 Ysh=YshlinePU;
	}
 
	public int GetNUM(){ return NUM; }
	public String GetrdfID(){ return ID; }
	public int GetCN1(){ return CNNUM1; }
	public int GetCN2(){ return CNNUM2; }
	public Complexnumber GetY() {return Y;}
	public Complexnumber GetYsh() {return Ysh;}
}
