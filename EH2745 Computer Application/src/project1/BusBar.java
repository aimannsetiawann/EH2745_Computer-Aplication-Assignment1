package project1;

import java.util.ArrayList;

public class BusBar {
	String ID;
	String ECID;
	private int NUM;
	private ArrayList<Integer> Cnode = new ArrayList<Integer> ();
 
	public BusBar (int BusBarNUM ,String BusBarID, ArrayList <Integer> ConnNode,String ECrdfID){
		 
		 ID=  BusBarID;
		 NUM =BusBarNUM;
		 Cnode=ConnNode;
		 ECID=ECrdfID;
	}
 
	public int GetNUM(){ return NUM; }
	public String GetID(){ return ID; }
	public String GetECID(){ return ECID; }
	public ArrayList<Integer> GetList(){ return  Cnode; }

}
