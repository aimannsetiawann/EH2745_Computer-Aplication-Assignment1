package project1;

public class ShuntList {

	String ID;
	private int BusNUM;
	Complexnumber Ysh;
 
	public ShuntList (String ShuntrdfID, int BusBarNUM, Complexnumber YshPU){
		 
		 ID=ShuntrdfID;
		 BusNUM=BusBarNUM;
		 Ysh=YshPU;
	}
 
	public int GetBusNUM(){ return BusNUM; }
	public String GetrdfID(){ return ID; }
	public Complexnumber GetYsh() {return Ysh;}
	
}
