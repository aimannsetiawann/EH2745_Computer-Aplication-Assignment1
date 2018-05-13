package project1;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;  
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import javax.swing.table.TableColumn;
import java.awt.Font;

public class MainProgram {
	

	static final double SystembasePower = 650;
	public static void main (String[] args){
		
		try {	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
////////////////////////////////////////////GETTING PARAMETERS FROM XML FILES////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	File EQXMLFile=new File ("/Assignment_EQ_reduced.xml");
	File SSHXMLFile=new File ("/Assignment_SSH_reduced.xml");
	DocumentBuilderFactory dbFactory= DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder= dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(EQXMLFile);
	Document docb = dBuilder.parse(SSHXMLFile);
	NodeList baseVoltageList=doc.getElementsByTagName("cim:BaseVoltage");
	NodeList subList=doc.getElementsByTagName("cim:Substation");
	NodeList voltageLevelList=doc.getElementsByTagName("cim:VoltageLevel");
	NodeList synchronousMList=doc.getElementsByTagName("cim:SynchronousMachine");
	NodeList GeneratingUList=doc.getElementsByTagName("cim:GeneratingUnit");
	NodeList regulatingControlList=doc.getElementsByTagName("cim:RegulatingControl");
	NodeList powerTransformeList=doc.getElementsByTagName("cim:PowerTransformer");
	NodeList energyConsumerList=doc.getElementsByTagName("cim:EnergyConsumer");
	NodeList powerTransformeEndList=doc.getElementsByTagName("cim:PowerTransformerEnd");
	NodeList breakerList=doc.getElementsByTagName("cim:Breaker");
	NodeList tapChangerList=doc.getElementsByTagName("cim:RatioTapChanger");
	
		// Specified for Y-BUS matrix calculation
	NodeList ACLineNodeList=doc.getElementsByTagName("cim:ACLineSegment");
	NodeList ConnectivityNodeList=doc.getElementsByTagName("cim:ConnectivityNode");
	NodeList TerminalList=doc.getElementsByTagName("cim:Terminal");
	NodeList BusBarList=doc.getElementsByTagName("cim:BusbarSection");
	NodeList LinearShuntList=doc.getElementsByTagName("cim:LinearShuntCompensator");
	
	
	//// Node lists from SSH FILE
	NodeList synchronousMListSSH=docb.getElementsByTagName("cim:SynchronousMachine");
	NodeList regulatingControlListSSH=docb.getElementsByTagName("cim:RegulatingControl");
	NodeList energyConsumerListSSH=docb.getElementsByTagName("cim:EnergyConsumer");
	NodeList breakerListSSH=docb.getElementsByTagName("cim:Breaker");
	NodeList tapChangerListSSH=docb.getElementsByTagName("cim:RatioTapChanger");
	
//// After we extract the elements we create array list to store specified attributes we need to both publish data and calculate Ybus matrix of the power system
	
	/// Array Lists
	ArrayList<Basevoltage> BaseVoltage = new ArrayList<Basevoltage>();
	ArrayList<Substation> SubstationList = new ArrayList<Substation>();
	ArrayList<Voltagelevel> VoltagelevelList = new ArrayList<Voltagelevel>();
	ArrayList<GeneratingUnit> GeneratingunitList = new ArrayList<GeneratingUnit>();
	ArrayList<SynchronousMachine> SynchronousMachineList = new ArrayList<SynchronousMachine>();
	ArrayList<RegulatingControl> RegulatingControlList = new ArrayList<RegulatingControl>();
	ArrayList<ConnectivityNode> ConnectivityNList = new ArrayList<ConnectivityNode>();
	ArrayList<Terminal> Terminal = new ArrayList<Terminal>();
	ArrayList<PowerTransformerEnd> PowerTransformerEList = new ArrayList<PowerTransformerEnd>();
	ArrayList<PowerTransformer> PowerTransformerList = new ArrayList<PowerTransformer>();
	ArrayList<EnergyConsumer> EnergyConsumerList = new ArrayList<EnergyConsumer>();
	ArrayList<CircuitBreaker> CircuitBreakerList = new ArrayList<CircuitBreaker>();
	ArrayList<TapChanger> TapChangerList = new ArrayList<TapChanger>();
	ArrayList<ACLine> ACLineList = new ArrayList<ACLine>();
	ArrayList<BusBar> BusBarSectionList = new ArrayList<BusBar>();
	ArrayList<ShuntList>ShuntList = new ArrayList<ShuntList>();
	
	
	// Basically we use loop to extract specified element we need, both for publishing to mySQL database and for Y-BUS matrix calculation
	// In the loop for each iteration we extract the elements, store it to variable, and store the variable to their corresponding arraylist
	
	
	for (int i=0; i<baseVoltageList.getLength(); i++) {
		
		 Element BaseVelement = (Element) baseVoltageList.item(i);
	     String BaseVrdfID= BaseVelement.getAttribute("rdf:ID");
	     String BaseVname= BaseVelement.getElementsByTagName("cim:BaseVoltage.nominalVoltage").item(0).getTextContent();
	     
		 Basevoltage x = new Basevoltage(BaseVrdfID,BaseVname);
			
			BaseVoltage.add(x);	
		}
	

	for (int i=0; i<subList.getLength(); i++) {
		
			Element SLelement = (Element) subList.item(i);
			String SubsrdfID=  SLelement.getAttribute("rdf:ID");
			String Subsname=  SLelement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
	    
			NodeList subRegion=SLelement.getElementsByTagName("cim:Substation.Region");
			Element SRelement = (Element) subRegion.item(0);
			String SubRsrdfID=  SRelement.getAttribute("rdf:resource").replace("#","");
	    
			Substation x = new Substation(SubsrdfID,Subsname,SubRsrdfID);
	    
			SubstationList.add(x);	
			}
	
	
	for (int i=0; i<voltageLevelList.getLength(); i++) {
		
			Element VLelement = (Element)voltageLevelList.item(i);
			String VLsrdfID=  VLelement.getAttribute("rdf:ID");
	    	String VLname=  VLelement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
	    
	    	NodeList VLSubs= VLelement.getElementsByTagName("cim:VoltageLevel.Substation");
	    	Element VLSubselement= (Element) VLSubs.item(0);
	    	String VLSubsrdfID=  VLSubselement.getAttribute("rdf:resource").replace("#","");
	    
	    	NodeList VLBase= VLelement.getElementsByTagName("cim:VoltageLevel.BaseVoltage");
	    	Element VLBaseelement= (Element) VLBase.item(0);
	    	String VLBaserdfID=  VLBaseelement.getAttribute("rdf:resource").replace("#","");

	    	Voltagelevel x = new Voltagelevel (VLsrdfID,VLname,VLSubsrdfID,VLBaserdfID);
	    
	    	VoltagelevelList.add(x);	
			} 
	
	
	for (int i=0; i<GeneratingUList.getLength(); i++) {
		
			Element GUelement = (Element) GeneratingUList.item(i);
			String GUsrdfID=  GUelement.getAttribute("rdf:ID");
			String GUname=  GUelement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
			String GUMaxP=  GUelement.getElementsByTagName("cim:GeneratingUnit.maxOperatingP").item(0).getTextContent();
			String GUMinP=  GUelement.getElementsByTagName("cim:GeneratingUnit.minOperatingP").item(0).getTextContent();
	    
			NodeList GUEContList= GUelement.getElementsByTagName("cim:Equipment.EquipmentContainer");
			Element GUECelement= (Element) GUEContList.item(0);
			String GUECrdfID=  GUECelement.getAttribute("rdf:resource").replace("#","");
	    
	    	GeneratingUnit x = new GeneratingUnit (GUsrdfID,GUname,GUMaxP,GUMinP,GUECrdfID);
	    	GeneratingunitList.add(x);
			} 
	
	for (int i=0; i<synchronousMList.getLength(); i++) {
		
			Element syncMLelement = (Element)synchronousMList.item(i);
			String syncMLrdfID=  syncMLelement.getAttribute("rdf:ID");
			String syncMLname=  syncMLelement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
	    	String syncMLrating=  syncMLelement.getElementsByTagName("cim:RotatingMachine.ratedS").item(0).getTextContent(); 
	    
	    	NodeList syncML= syncMLelement.getElementsByTagName("cim:Equipment.EquipmentContainer");
	    	Element EquipmentContainer= (Element) syncML.item(0);
	    	String ECrDf=  EquipmentContainer.getAttribute("rdf:resource").replace("#","");
	    
	    	NodeList syncRC= syncMLelement.getElementsByTagName("cim:RegulatingCondEq.RegulatingControl");
	    	Element RControl= (Element) syncRC.item(0);
	    	String RCrDf=  RControl.getAttribute("rdf:resource").replace("#","");
	    
	    	NodeList syncGU= syncMLelement.getElementsByTagName("cim:RotatingMachine.GeneratingUnit");
	    	Element GenUnit= (Element) syncGU.item(0);
	    	String GUrDf=  GenUnit.getAttribute("rdf:resource").replace("#","");
	    
	    	Element syncMLelementSSH = (Element) synchronousMListSSH.item(i);
	    	String syncMP= syncMLelementSSH.getElementsByTagName("cim:RotatingMachine.p").item(0).getTextContent();
	    	String syncMQ= syncMLelementSSH.getElementsByTagName("cim:RotatingMachine.q").item(0).getTextContent();
	    
	    	String BaseVSMrdfID = null;
	    
	    	for (int j = 0; j < VoltagelevelList.size() ; j++){
	    	
		    String a=VoltagelevelList.get(j).GetVLrdfID();
		    String b=ECrDf.replace("#","");
		    
	    	if (b.equals(a)) {
	    		
	    		BaseVSMrdfID = VoltagelevelList.get(j).GetBaseVID().replace("#","");}
			}    
	    
	    	SynchronousMachine x = new SynchronousMachine (syncMLrdfID,syncMLname,syncMLrating,ECrDf,RCrDf,GUrDf,syncMP,syncMQ,BaseVSMrdfID);
	    
	    	SynchronousMachineList.add(x);	
			} 
	
			
	for (int i=0; i<regulatingControlList.getLength(); i++) {
		
			Element RCelement = (Element) regulatingControlList.item(i);
			String RCrdfID=  RCelement.getAttribute("rdf:ID");
			String RCname=  RCelement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
			String RCSSHname=null;
			for (int j=0; j<regulatingControlList.getLength(); j++) // Loop for making sure that the target value variable from SSH file are stored in right sequence
			{
				Element RCelementSSH = (Element) regulatingControlListSSH.item(j);
				String a= RCelementSSH.getAttribute("rdf:about").replace("#",""); // Store the variable value and make sure there is no # character 
				
				if(a.equals(RCrdfID)) {
					RCSSHname=RCelementSSH.getElementsByTagName("cim:RegulatingControl.targetValue").item(0).getTextContent(); 
				}			
			}
			RegulatingControl x = new RegulatingControl (RCrdfID,RCname ,RCSSHname);
    
			RegulatingControlList.add(x);	   
			} 
		
		
			
	for (int i=0; i<ConnectivityNodeList.getLength(); i++) {
		
			Element CNelement = (Element) ConnectivityNodeList.item(i);
			String CNrdfID=  CNelement.getAttribute("rdf:ID");
			
			NodeList ConnNodeECList= CNelement.getElementsByTagName("cim:ConnectivityNode.ConnectivityNodeContainer");
		    Element ConnNodeEC= (Element) ConnNodeECList.item(0);
		    String ECrdfID= ConnNodeEC.getAttribute("rdf:resource").replace("#","");
			
			int num=i;
			 
			ConnectivityNode  x = new ConnectivityNode  (CNrdfID,num,ECrdfID);
    
			ConnectivityNList.add(x);	
			
			}
		
			
	for (int i=0; i<TerminalList.getLength(); i++) {
			
			int TermConn = 0;

			Element Telement = (Element) TerminalList.item(i);
			String TrdfID= Telement.getAttribute("rdf:ID");
			int num=i;
			
			NodeList TCEList= Telement.getElementsByTagName("cim:Terminal.ConductingEquipment");
		    Element TCEelement= (Element) TCEList.item(0);
		    String TCErdfID=  TCEelement.getAttribute("rdf:resource");
		    
			NodeList TCNList= Telement.getElementsByTagName("cim:Terminal.ConnectivityNode");
		    Element TCNelement= (Element) TCNList.item(0);
		    String TCNrdfID=  TCNelement.getAttribute("rdf:resource");
		    TCNrdfID = TCNrdfID.replace("#","");
		    
		    for (int j = 0 ; j <ConnectivityNList.size() ; j++){
		    	
		    	String a=ConnectivityNList.get(j).GetCNrdfID();
		    	int b=ConnectivityNList.get(j).GetCNNUM();
		    	
		    	if (TCNrdfID.equals(a)){TermConn=b;
		    		break;} }
		    
		    Terminal  x = new Terminal  (TrdfID,num,TCErdfID,TCNrdfID,TermConn);
		    
			Terminal.add(x);
			
		}		
			
		
	for (int i=0; i<powerTransformeEndList.getLength(); i++) {
			
			double PTEbasevoltage=0.0;
			int PTETerminalNum=0;
			int TransformerEndNum=i;
			
			Element PTWelement = (Element) powerTransformeEndList.item(i);
			String PTWrdfID=  PTWelement.getAttribute("rdf:ID");
		    String PTWname=  PTWelement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		    
		    // Get impedance and shunt admittance value of the transformer winding
		    String PTWr=  PTWelement.getElementsByTagName("cim:PowerTransformerEnd.r").item(0).getTextContent();
		    String PTWx=  PTWelement.getElementsByTagName("cim:PowerTransformerEnd.x").item(0).getTextContent();
		    String PTWb=  PTWelement.getElementsByTagName("cim:PowerTransformerEnd.b").item(0).getTextContent();
		    String PTWg=  PTWelement.getElementsByTagName("cim:PowerTransformerEnd.g").item(0).getTextContent();
		    String PTWS=  PTWelement.getElementsByTagName("cim:PowerTransformerEnd.ratedS").item(0).getTextContent();
		    String PTWU=  PTWelement.getElementsByTagName("cim:PowerTransformerEnd.ratedU").item(0).getTextContent();
		    
		    NodeList PTWBaseUElementList= PTWelement.getElementsByTagName("cim:TransformerEnd.BaseVoltage");
		    Element PTWBUElement= (Element) PTWBaseUElementList.item(0);
		    String PTWBUrdfID=   PTWBUElement.getAttribute("rdf:resource");
		    PTWBUrdfID = PTWBUrdfID.replace("#","");
		    
		    	    
		    for (int j = 0 ; j <BaseVoltage.size() ; j++){ // Loop to get base voltage value of the transformer winding
		    	
		    	String a=BaseVoltage.get(j).GetrdfID();
		    	double b=BaseVoltage.get(j).GetBV();
		    	
		    	if (PTWBUrdfID.equals(a)){PTEbasevoltage=b;
		    		break;} }
		    
		    NodeList PTWTElementList= PTWelement.getElementsByTagName("cim:PowerTransformerEnd.PowerTransformer");
		    Element PTWTElement= (Element) PTWTElementList.item(0);
		    String PTWTransformerrdfID=   PTWTElement.getAttribute("rdf:resource");
		    PTWTransformerrdfID=PTWTransformerrdfID.replace("#","");
		    
		    NodeList PTWTerminalElementList= PTWelement.getElementsByTagName("cim:TransformerEnd.Terminal");
		    Element PTWTerminalElement= (Element) PTWTerminalElementList.item(0);
		    String PTWTerminalrdfID=   PTWTerminalElement.getAttribute("rdf:resource");
		    PTWTerminalrdfID = PTWTerminalrdfID.replace("#","");
		    
		    // Loop for getting the terminal number of transformer winding
		    for (int j = 0 ; j <Terminal.size() ; j++){
		    	
		    	String a=Terminal.get(j).GetTrdfID();
		    	int b=Terminal.get(j).GetTNUM();
		    	
		    	if (PTWTerminalrdfID.equals(a)){PTETerminalNum=b;
		    		break;} }
		    
		    PowerTransformerEnd x = new PowerTransformerEnd (TransformerEndNum, PTETerminalNum,PTWrdfID , PTWname,PTWTransformerrdfID,PTWBUrdfID,PTWr,PTWx,PTWb,PTWg, PTWS, PTWU,PTEbasevoltage,SystembasePower);
		    
		    PowerTransformerEList.add(x);		 
		} 
		
	// Since transformer admittance will be used to construct YBUS matrix, in this loop we also calculate the admittance value of the transformer
	// First we get corresponding winding of the transformer, get their impedance value, and determine its admittance value in per unit
		
	for (int i=0; i<powerTransformeList.getLength(); i++) {
			
			int PTEnd2Terminal=0,PTEnd1Terminal = 0,PTCN1 = 0,PTCN2 = 0;
			
			Complexnumber YTransformerpu= new Complexnumber (0,0);
			Complexnumber YTransformersh= new Complexnumber (0,0);
			
			double R1Transformer=0.0,X1Transformer=0.0,B1Transformer=0.0,G1Transformer=0.0,U1Transformer=0.0,STransformer=0.0;
			double R2Transformer=0.0,X2Transformer=0.0,B2Transformer=0.0,G2Transformer=0.0,U2Transformer=0.0;
			int h=0;
			
			Element PTelement = (Element) powerTransformeList.item(i);
			String PTrdfID=  PTelement.getAttribute("rdf:ID");
		    String PTname=  PTelement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		    
		    NodeList PTEqElementList= PTelement.getElementsByTagName("cim:Equipment.EquipmentContainer");
		    Element PTEqElement= (Element) PTEqElementList.item(0);
		    String PTECrdf=   PTEqElement.getAttribute("rdf:resource").replace("#","");
		    
		    for (int j = 0; j < PowerTransformerEList.size() ; j++){// loop to get transformer corresponding windings
		    	
			    String a=PowerTransformerEList.get(j).GetTransformerID();
			    String b=PTrdfID;
			    
		    	if (b.equals(a)&& h == 1) {
		    	
					PTEnd2Terminal=PowerTransformerEList.get(j).GetTEterminalNumber();
					R2Transformer= PowerTransformerEList.get(j).Getr();
					X2Transformer= PowerTransformerEList.get(j).Getx();
					B2Transformer= PowerTransformerEList.get(j).Getb();
					G2Transformer= PowerTransformerEList.get(j).Getg();
					U2Transformer= PowerTransformerEList.get(j).GetTransformerS();					
				}
		    		    	
		    	if (b.equals(a)&& h == 0) {
		    	
					PTEnd1Terminal=PowerTransformerEList.get(j).GetTEterminalNumber();
					R1Transformer= PowerTransformerEList.get(j).Getr();
					X1Transformer= PowerTransformerEList.get(j).Getx();
					B1Transformer=PowerTransformerEList.get(j).Getb();
					G1Transformer=PowerTransformerEList.get(j).Getg();
					U1Transformer=PowerTransformerEList.get(j).GetTransformerU();
					STransformer=PowerTransformerEList.get(j).GetTransformerS();
					
		    		h=1;
				}
		    	
		    	// Calculate impedance and admittance value of transformer
		    	double realR=R1Transformer+R2Transformer;
		    	double imagX=X1Transformer+X2Transformer;
		    	double realB=B1Transformer+B2Transformer;
		    	double imagG=G1Transformer+G2Transformer;
		 	   	double UBaseTransformer=U1Transformer;
		 	   	
		 	   	// Calculate base impedance value of transformer
		 	   	double ZbaseTransformer=(((UBaseTransformer*1000)*(UBaseTransformer*1000))/(1000000*SystembasePower));
		 
		    	Complexnumber Z= new Complexnumber(realR,imagX);	    	
		    	Complexnumber Zpu= Z.devideCons(Z,ZbaseTransformer);    	
		    	
		    	// Calculate per unit admittance and shunt admittance of transformer
		    	YTransformerpu= Zpu.reciprocal();
		    	YTransformersh= new Complexnumber (realB,imagG);  
		    	
			}
		    
		    // Loop for determining the connectivity node number of transformer primary side and secondary side
		    
	    	for (int j=0; j<Terminal.size(); j++) {   		
	    		
	    		int a=Terminal.get(j).GetTNUM();  		
	    		
	    		if(a==PTEnd2Terminal) {	
	    			PTCN2=Terminal.get(j).GetTermConn();	
	    		}    		
	    	}
	    	
	    	for (int j=0; j<Terminal.size(); j++) {
	    		
	    		int a=Terminal.get(j).GetTNUM(); 		
	    		
	    		if(a==PTEnd1Terminal) {
	    			PTCN1=Terminal.get(j).GetTermConn();
	    		}
	    	}	    	
	    	
		    PowerTransformer x = new PowerTransformer (PTrdfID,PTname,PTECrdf,PTCN1,PTCN2, YTransformerpu, YTransformersh);
		    PowerTransformerList.add(x);
		}

	
	for (int i=0; i<energyConsumerList.getLength(); i++) {
			
		Element EConselement = (Element) energyConsumerList.item(i);
		String ECrdfID=  EConselement.getAttribute("rdf:ID");
	    String ECname=  EConselement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();   
	    String ECP=null;
	    String ECQ=null;
	    
	    for (int j=0; j<energyConsumerList.getLength(); j++)
		{
	    	Element EConselementSSH = (Element) energyConsumerListSSH.item(j);
			String a= EConselementSSH.getAttribute("rdf:about").replace("#",""); 
			if(a.equals(ECrdfID)) {
				 ECP=  EConselementSSH.getElementsByTagName("cim:EnergyConsumer.p").item(0).getTextContent();
				 ECQ=  EConselementSSH.getElementsByTagName("cim:EnergyConsumer.q").item(0).getTextContent();
			}			
		}
	    
	    NodeList EConsContList= EConselement.getElementsByTagName("cim:Equipment.EquipmentContainer");
	    Element EConsContElement= (Element) EConsContList.item(0);
	    String EnergyConsEC=   EConsContElement.getAttribute("rdf:resource").replace("#","");
	    String BaseVrdfID=null ;
	    
	    for (int j = 0; j < VoltagelevelList.size() ; j++){ // Loop for getting its corresponding voltage value
	    	
		    String a=VoltagelevelList.get(j).GetVLrdfID();
		    String b=EnergyConsEC.replace("#","");
		    
	    	if (b.equals(a)) {
	    		
	    		BaseVrdfID = VoltagelevelList.get(j).GetBaseVID().replace("#","");}
		}    
  
	    EnergyConsumer x = new EnergyConsumer (ECrdfID,ECname,EnergyConsEC,ECP,ECQ,BaseVrdfID);
	    EnergyConsumerList.add(x);}
		
	
	for (int i=0; i<breakerList.getLength(); i++) {
			
			int BreakerNUM=i;
		
			Element Breakerelement = (Element) breakerList.item(i);
			String BreakerrdfID=  Breakerelement.getAttribute("rdf:ID");
		    String Breakername=  Breakerelement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();
		    NodeList BreakerEQElementList= Breakerelement.getElementsByTagName("cim:Equipment.EquipmentContainer");
		    Element BreakerEQElement= (Element) BreakerEQElementList.item(0);
		    String BECrdf=BreakerEQElement.getAttribute("rdf:resource").replace("#","");		    
		    String BreakerS= null;
		   
		    for (int j=0; j<breakerList.getLength(); j++)
			{
		    	Element BreakerelementSSH = (Element) breakerListSSH.item(j);
				String a= BreakerelementSSH.getAttribute("rdf:about").replace("#",""); ; 
				
				if(a.equals(BreakerrdfID)) {

					BreakerS=  BreakerelementSSH.getElementsByTagName("cim:Switch.open").item(0).getTextContent();
				}			
			}
		    
		    String CBVrdfID=null ;
		    
		    for (int j = 0; j < VoltagelevelList.size() ; j++){
		    	
			    String a=VoltagelevelList.get(j).GetVLrdfID();
			    String b=BECrdf.replace("#","");
			    
		    	if (b.equals(a)) {
		    		
		    		CBVrdfID = VoltagelevelList.get(j).GetBaseVID().replace("#","");}
			}    
		    
		    int h=0;	    
		    int BreakerCN1=0;
		    int BreakerCN2=0;
		 // For breaker class we also get its corresponding connectivity node number for both side of the breaker
		    for (int j = 0; j < Terminal.size() ; j++){
		    	
			    String a=Terminal.get(j).GetTcerdfID().replace("#","");
			    String b=BreakerrdfID;

		    	if (b.equals(a)&& h == 1) {		
		    		BreakerCN1= Terminal.get(j).GetTermConn();
				}
		    		    	
		    	if (b.equals(a)&& h == 0) {
		    		BreakerCN2= Terminal.get(j).GetTermConn();								
		    		h=1;
				}
		    }
		    
	    CircuitBreaker x = new CircuitBreaker (BreakerNUM,BreakerrdfID,Breakername,BECrdf,BreakerS,CBVrdfID,BreakerCN1,BreakerCN2);
	    CircuitBreakerList.add(x);
	   
		}
	
		
		
	for (int i=0; i<tapChangerList.getLength(); i++) {
			
			Element tapChangerElement = (Element) tapChangerList.item(i);
			String tapChangerrdfID=  tapChangerElement.getAttribute("rdf:ID");
		    String tapChangername=  tapChangerElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();

		    String tapChangerstep=  null;
		   
		    for (int j=0; j<tapChangerList.getLength(); j++)
			{
		    	Element tapChangerElementSSH = (Element) tapChangerListSSH.item(j);
				String a= tapChangerElementSSH.getAttribute("rdf:about").replace("#",""); ; 
				
				if(a.equals(tapChangerrdfID)) {
				tapChangerstep=tapChangerElementSSH.getElementsByTagName("cim:TapChanger.step").item(0).getTextContent(); 
				}			
			}
		    
		    TapChanger x = new TapChanger (tapChangerrdfID,tapChangername,tapChangerstep);
		    TapChangerList.add(x);
		}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////ELEMENTS PARSING FOR Y-BUS CALCULATION///////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	for (int i=0; i<ACLineNodeList.getLength(); i++) {
		
		int LineNum=i;

		Element ACLineElement = (Element) ACLineNodeList.item(i);
		String LinerdfID= ACLineElement.getAttribute("rdf:ID");
		String r= ACLineElement.getElementsByTagName("cim:ACLineSegment.r").item(0).getTextContent();
		String x= ACLineElement.getElementsByTagName("cim:ACLineSegment.x").item(0).getTextContent();
		String bsh= ACLineElement.getElementsByTagName("cim:ACLineSegment.bch").item(0).getTextContent();
		String gsh= ACLineElement.getElementsByTagName("cim:ACLineSegment.gch").item(0).getTextContent();
	    
		NodeList ACLineBVList= ACLineElement.getElementsByTagName("cim:ConductingEquipment.BaseVoltage");
	    Element ACLineBV= (Element) ACLineBVList.item(0);
	    String BVrdfID=ACLineBV.getAttribute("rdf:resource").replace("#","");
	    
	    int LineCNode1=0;
	    int LineCNode2=0;
	    
	    int h=0;
	    
	    for (int j = 0; j < Terminal.size() ; j++){
	    	
		    String a=Terminal.get(j).GetTcerdfID().replace("#","");
		    String b=LinerdfID;

	    	if (b.equals(a)&& h == 1) {
	    		LineCNode2 = Terminal.get(j).GetTermConn();					
			}
	    		    	
	    	if (b.equals(a)&& h == 0) {
	    		LineCNode1 = Terminal.get(j).GetTermConn();									
	    		h=1;
			}
	    }
	    	    
	    double BVLineValue=0.0;
	    
	    for (int j = 0; j < BaseVoltage.size() ; j++){
	    	
		    String a=BaseVoltage.get(j).GetrdfID();
		    String b=BVrdfID.replace("#","");
		
	    	if (b.equals(a)) {
	    		BVLineValue = BaseVoltage.get(j).GetBV();}
		}  
	    
    	double UBaseLine=BVLineValue;
    	double Zbaseline=(((UBaseLine*1000)*(UBaseLine*1000))/(1000000*SystembasePower));
    	double R=Double.parseDouble(r);
    	double X=Double.parseDouble(x);
    	double B=Double.parseDouble(bsh)/2;
    	double G=Double.parseDouble(gsh)/2;
    	
    	Complexnumber Zline= new Complexnumber(R,X);
    	Complexnumber Zlinepu= Zline.devideCons(Zline, Zbaseline);
   
    	Complexnumber Yshline= new Complexnumber(B,G);
    	Complexnumber YshlinePU= Yshline.MultyCons(Yshline, Zbaseline);
    	Complexnumber YLinePU= Zlinepu.reciprocal();
    
    	ACLine y= new ACLine (LineNum ,LinerdfID, LineCNode1, LineCNode2, YLinePU, YshlinePU );
    	
    	ACLineList.add(y);

	}		

	for (int i=0; i<BusBarList.getLength(); i++) {
		
		int BusBarNUM=i;
	
		Element BusBarElement = (Element) BusBarList.item(i);
		String BusBarID=  BusBarElement.getAttribute("rdf:ID");
		String BusBarname=  BusBarElement.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent();

		NodeList BusBarECList= BusBarElement.getElementsByTagName("cim:Equipment.EquipmentContainer");
		Element BusBarEC= (Element) BusBarECList.item(0);
		String ECrdfID= BusBarEC.getAttribute("rdf:resource").replace("#","");
    
		ArrayList<Integer> BusBarConnectivityNodeNum = new ArrayList<Integer>();
    
		for (int j = 0; j < ConnectivityNList.size() ; j++){
    	
			String a=ConnectivityNList.get(j).GetCNECrdfID().replace("#","");
			String b=ECrdfID;

			if (b.equals(a)) {
    		int y = ConnectivityNList.get(j).GetCNNUM();		
    		BusBarConnectivityNodeNum.add(y);
		}
			}
    
		BusBar x= new BusBar(BusBarNUM,BusBarID,BusBarConnectivityNodeNum,ECrdfID);
    
		BusBarSectionList.add(x);
	}
	
	for (int i=0; i<LinearShuntList.getLength(); i++) {
		
	
		Element LinearShuntElement = (Element) LinearShuntList.item(i);
		String ShuntrdfID=  LinearShuntElement.getAttribute("rdf:ID");
	
		NodeList LinearShuntECList= LinearShuntElement.getElementsByTagName("cim:Equipment.EquipmentContainer");
		Element LinearShuntEC= (Element) LinearShuntECList.item(0);
		String ECrdfID= LinearShuntEC.getAttribute("rdf:resource").replace("#","");
    
		double B=Double.parseDouble(LinearShuntElement.getElementsByTagName("cim:LinearShuntCompensator.bPerSection").item(0).getTextContent());
		double G=Double.parseDouble(LinearShuntElement.getElementsByTagName("cim:LinearShuntCompensator.gPerSection").item(0).getTextContent());
	    double UbaseShunt=Double.parseDouble(LinearShuntElement.getElementsByTagName("cim:ShuntCompensator.nomU").item(0).getTextContent());
	    double ZbaseShunt=(((UbaseShunt*1000)*(UbaseShunt*1000))/(1000000*SystembasePower));
	   
    	Complexnumber Ysh= new Complexnumber(B,G);
    	Complexnumber YshPU= Ysh.MultyCons(Ysh,ZbaseShunt);
	   
	    int BusBarNUM = 0;
    
		for (int j = 0; j <BusBarSectionList.size() ; j++){
    	
			String a=BusBarSectionList.get(j).GetECID().replace("#","");
			String b=ECrdfID;

			if (b.equals(a)) {
				BusBarNUM  = BusBarSectionList.get(j).GetNUM();		
			
		}
			}
		
		 ShuntList x= new ShuntList (ShuntrdfID, BusBarNUM,YshPU);
		 ShuntList.add(x);
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
////////////////////////////////////////////CREATE AND IMPORT DATA TO SQL DATA BASE/////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		final String USERNAME="root";
		final String PASS="28Des94";
		
		CreateDatabase mySQL = new CreateDatabase (USERNAME,PASS);
		
		// Extracting data from array list and store it to sql database
		for (int i=0; i<BaseVoltage.size(); i++) {
			String rdfID = BaseVoltage.get(i).GetrdfID();
			double Value = BaseVoltage.get(i).GetBV();
			mySQL.TableBV(rdfID, Value);
			}
		
		for (int i=0; i<SubstationList.size(); i++) {
			String rdfID = SubstationList.get(i).SubsRDFID();
			String Name = SubstationList.get(i).GetName();
			String Region= SubstationList.get(i).SubsRegion();
			mySQL.TableSubstation(rdfID, Name, Region );
			}
		for (int i=0; i<VoltagelevelList.size(); i++) {
			String rdfID = VoltagelevelList.get(i).GetVLrdfID();
			double Value = VoltagelevelList.get(i).GetValueVL();
			String Substation= VoltagelevelList.get(i).GetSubsID();
			String BaseV= VoltagelevelList.get(i).GetBaseVID();
			
			mySQL.TableVoltageLevel(rdfID, Value, Substation, BaseV);
			}

		for (int i=0; i<GeneratingunitList.size(); i++) {
			String rdfID = GeneratingunitList.get(i).GetGUrdfID();
			String Name= GeneratingunitList.get(i).GetGUName();
			double maxP= GeneratingunitList.get(i).GetGUPmax();
			double minP= GeneratingunitList.get(i).GetGUPmin();
			String ECID= GeneratingunitList.get(i).GetGUEC();
			
			mySQL.TableGeneratingUnit(rdfID, Name, maxP, minP, ECID);
			
			}
		
		for (int i=0; i<SynchronousMachineList.size(); i++) {
			String rdfID = SynchronousMachineList.get(i).GetSMrdfID();
			String Name= SynchronousMachineList.get(i).GetSMName();
			double Srating= SynchronousMachineList.get(i).GetSMRating();
			double syncMP= SynchronousMachineList.get(i).GetSMP();
			double syncMQ= SynchronousMachineList.get(i).GetSMQ();
			String GUrdfID= SynchronousMachineList.get(i).GetSMGU();
			String RCrdfID= SynchronousMachineList.get(i).GetSMRC();
			String ECrdfID= SynchronousMachineList.get(i).GetSMEC();
			String BVrdfID= SynchronousMachineList.get(i).GetBVID();
			
			mySQL.TableSynchronousMachine(rdfID, Name, Srating,syncMP,syncMQ,GUrdfID,RCrdfID,ECrdfID,BVrdfID );
			
			}
		
		for (int i=0; i<RegulatingControlList.size(); i++) {
			String rdfID = RegulatingControlList.get(i).GetRCrdfID();
			String Name= RegulatingControlList.get(i).GetRCName();
			double TargetValue= RegulatingControlList.get(i).GetRCTV();
					
			mySQL.TableRC(rdfID,Name,TargetValue);
			
			}
		
		for (int i=0; i<PowerTransformerList.size(); i++) {
			String rdfID = PowerTransformerList.get(i).GetrdfID();
			String Name= PowerTransformerList.get(i).Getname();
			String PTErdf= PowerTransformerList.get(i).GetECID();
					
			mySQL.TableTransformer(rdfID,Name,PTErdf);
			
			}
		
		for (int i=0; i<EnergyConsumerList.size(); i++) {
			String rdfID = EnergyConsumerList.get(i).GetrdfID();
			String Name= EnergyConsumerList.get(i).GetnamerdfID();
			double P= EnergyConsumerList.get(i).GetP();
			double Q= EnergyConsumerList.get(i).GetQ();
			String ECID = EnergyConsumerList.get(i).GetECrdfID();
			String BVID= EnergyConsumerList.get(i).GetBVrdfID();
			
			mySQL.TableEnergyConsumer(rdfID,Name,P,Q,ECID,BVID);
			
			}
		
		for (int i=0; i<PowerTransformerEList.size(); i++) {
			String rdfID = PowerTransformerEList.get(i).GetTErdfID();
			String Name= PowerTransformerEList.get(i).GetTEName();
			double r= PowerTransformerEList.get(i).Getr();
			double x= PowerTransformerEList.get(i).Getx();
			String TrdfID = PowerTransformerEList.get(i).GetTransformerID();
			String BVID= PowerTransformerEList.get(i).GetbaseVoltageID();
			
			mySQL.TableTransformerEnd(rdfID,Name,r,x,TrdfID,BVID);
			
			}
		
		for (int i=0; i<CircuitBreakerList.size(); i++) {
			String rdfID = CircuitBreakerList.get(i).GetrdfID();
			String Name= CircuitBreakerList.get(i).GetnamerdfID();
			String State= CircuitBreakerList.get(i).GetState();
			String ECID = CircuitBreakerList.get(i).GetECrdfID();
			String BVID= CircuitBreakerList.get(i).GetCBVrdfID();
			
			mySQL.TableCircuitBreaker(rdfID,Name,State,ECID,BVID);
			
			}
		
		for (int i=0; i<TapChangerList.size(); i++) {
			String rdfID = TapChangerList.get(i).GetrdfID();
			String Name= TapChangerList.get(i).GetnamerdfID();
			double Step= TapChangerList.get(i).GetStep();
			
			mySQL.TableRatioTapChanger(rdfID,Name,Step);
			
			}
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
////////////////////////////////////////////Y-BUS MATRIX CALCULATION/////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// In This Section, we will determine Y BUS Matrix of The System
		
// We are not using translation method as described in given journal examples, instead we focus on elements which give value for admittance calulation
// In this case the elements are ACLines, Power Transformer, and Shunt Compensator

// We determine the size of Ybus Matrix for example if the system has 5 buses the Ybus matrix will be matrix with size of 5 x 5.
		
// Basically we review each elements and find its corresponding bus. For example, AC Line 1 has corresponding buses with number of 1 and 2, the Y[1][2] and Y[2][1] value will equal to the line admittance, and the value also be updated to Y[1][1] and Y[2][2]
	
		final int size=BusBarSectionList.size();
		final Complexnumber zero = new Complexnumber(0,0);			
		final Complexnumber Ybus[][] = new Complexnumber[size][size];
		
		for (int i=0; i<BusBarSectionList.size(); i++) {
			for (int j=0; j<BusBarSectionList.size(); j++) {
				
				Ybus[i][j]=zero;
			}		
		}
		
		for (int i=0; i<ACLineList.size(); i++) {	
			
			String Flagbr1= "false";
			String Flagbr2= "false";
			
			Complexnumber Yline=ACLineList.get(i).GetY();
			Complexnumber Yshline=ACLineList.get(i).GetYsh();
			
			int LineBusI=0;
			int LineBusJ=0;
			
			int a=0;
			int b=0;
			
			int LineCN1=ACLineList.get(i).GetCN1();
			int LineCN2=ACLineList.get(i).GetCN2();
			
			for (int j=0; j<CircuitBreakerList.size(); j++) {
				
				int BreakerCN1=CircuitBreakerList.get(j).GetCN1();
				int BreakerCN2=CircuitBreakerList.get(j).GetCN2();
				
					if(BreakerCN1==LineCN1) {
						a=CircuitBreakerList.get(j).GetCN2();
					} 
					
					else if(BreakerCN2==LineCN1) {
						a=CircuitBreakerList.get(j).GetCN1();
					} 
					
					else {
						a=LineCN1;
					}
					
					if ((BreakerCN1==LineCN1)||(BreakerCN2==LineCN1)) {
					
						String br=CircuitBreakerList.get(j).GetState();
						
						if (br.equals(Flagbr1)) {
							continue;
						}
						
						else {
							Flagbr1="true";
						}
						
					}
			}
			
			for (int j=0; j<CircuitBreakerList.size(); j++) {
				
				int BreakerCN1=CircuitBreakerList.get(j).GetCN1();
				int BreakerCN2=CircuitBreakerList.get(j).GetCN2();
				
					if(BreakerCN1==LineCN2) {
						b=CircuitBreakerList.get(j).GetCN2();
					} 
					
					if(BreakerCN2==LineCN2) {
						b=CircuitBreakerList.get(j).GetCN1();
					}
					
					else {
						b=LineCN2;
					}
					
					if ((BreakerCN1==LineCN2)||(BreakerCN2==LineCN2)) {
						
						String br=CircuitBreakerList.get(j).GetState();
						
						if (br.equals(Flagbr2)) {
							continue;
						}
						
						else {
							Flagbr2="true";
						}
						
					}	
			}
			
			for (int k=0; k<BusBarSectionList.size(); k++) {
				
				for (int K=0; K<BusBarSectionList.get(k).GetList().size(); K++) {
					
					int L=BusBarSectionList.get(k).GetList().get(K);
					
					if(L==a) {
						LineBusI=BusBarSectionList.get(k).GetNUM();
					}
					
				}
				
			}
			
			for (int m=0; m<BusBarSectionList.size(); m++) {
				
				for (int M=0; M<BusBarSectionList.get(m).GetList().size(); M++) {
					
					int N=BusBarSectionList.get(m).GetList().get(M);
					
					if(N==b) {
						LineBusJ=BusBarSectionList.get(m).GetNUM();
					}
				}				
			}
			
			
			if ((Flagbr1.equals("false") && Flagbr2.equals("false")) );
			{
				Ybus[LineBusI][LineBusI]=(Ybus[LineBusI][LineBusI]).plus((Ybus[LineBusI][LineBusI]), (Yline.plus(Yline, Yshline)));
				Ybus[LineBusJ][LineBusJ]=(Ybus[LineBusJ][LineBusJ]).plus((Ybus[LineBusJ][LineBusJ]), (Yline.plus(Yline, Yshline)));
				Ybus[LineBusI][LineBusJ]=(Ybus[LineBusI][LineBusJ]).plus((Ybus[LineBusI][LineBusJ]), Yline);
				Ybus[LineBusJ][LineBusI]=(Ybus[LineBusJ][LineBusI]).plus((Ybus[LineBusJ][LineBusI]), Yline);
			}
		}
		
		for (int i=0; i<PowerTransformerList.size(); i++) {	
		
			String Flagbr1= "false";
			String Flagbr2= "false";
			
			Complexnumber YTrans=PowerTransformerList.get(i).GetY();
			Complexnumber YshTrans=PowerTransformerList.get(i).GetYsh();
			
			int TransBusI=0;
			int TransBusJ=0;
			
			int a=0;
			int b=0;
			
			int TransCN1=PowerTransformerList.get(i).GetCN1();
			int TransCN2=PowerTransformerList.get(i).GetCN2();
		
			for (int j=0; j<CircuitBreakerList.size(); j++) {
				
				int BreakerCN1=CircuitBreakerList.get(j).GetCN1();
				int BreakerCN2=CircuitBreakerList.get(j).GetCN2();
				
					if(BreakerCN1==TransCN1) {
						a=CircuitBreakerList.get(j).GetCN2();
						
					} 
					
					else if(BreakerCN2==TransCN1) {
						a=CircuitBreakerList.get(j).GetCN1();
					} 
					
					else {
						a=TransCN1;
					}
					
					if ((BreakerCN1==TransCN1)||(BreakerCN2==TransCN1)) {
					
						String br=CircuitBreakerList.get(j).GetState();
						
						if (br.equals(Flagbr1)) {
							continue;
						}
						
						else {
							Flagbr1="true";
						}
						
					}
			}
			
			for (int j=0; j<CircuitBreakerList.size(); j++) {
				
				int BreakerCN1=CircuitBreakerList.get(j).GetCN1();
				int BreakerCN2=CircuitBreakerList.get(j).GetCN2();
				
					if(BreakerCN1==TransCN2) {
						b=CircuitBreakerList.get(j).GetCN2();
					} 
					
					if(BreakerCN2==TransCN2) {
						b=CircuitBreakerList.get(j).GetCN1();
					}
					
					else {
						b=TransCN2;
					}
					
					if ((BreakerCN1==TransCN2)||(BreakerCN2==TransCN2)) {
						
						String br=CircuitBreakerList.get(j).GetState();
						
						if (br.equals(Flagbr2)) {
							continue;
						}
						
						else {
							Flagbr2="true";
						}
						
					}	
			}
			
			for (int k=0; k<BusBarSectionList.size(); k++) {
				
				for (int K=0; K<BusBarSectionList.get(k).GetList().size(); K++) {
					
					int L=BusBarSectionList.get(k).GetList().get(K);
					
					if(L==a) {
						TransBusI=BusBarSectionList.get(k).GetNUM();

					}
				}
			}
			
			for (int m=0; m<BusBarSectionList.size(); m++) {
				
				for (int M=0; M<BusBarSectionList.get(m).GetList().size(); M++) {
					
					int N=BusBarSectionList.get(m).GetList().get(M);
					
					if(N==b) {
						TransBusJ=BusBarSectionList.get(m).GetNUM();
					}
				}				
			}
			
			if ((Flagbr1.equals("false") && Flagbr2.equals("false")))
			{
				
				Ybus[TransBusI][TransBusI]=(Ybus[TransBusI][TransBusI]).plus((Ybus[TransBusI][TransBusI]), (YTrans.plus(YTrans, YshTrans)));
				Ybus[TransBusJ][TransBusJ]=(Ybus[TransBusJ][TransBusJ]).plus((Ybus[TransBusJ][TransBusJ]), (YTrans));
				Ybus[TransBusI][TransBusJ]=YTrans;
				Ybus[TransBusJ][TransBusI]=YTrans;
			}
		}
		
		for (int i=0; i<ShuntList.size(); i++) {	
			
			Complexnumber Ysh=ShuntList.get(i).GetYsh();
			
			int LineBusI=ShuntList.get(i).GetBusNUM();
			
			System.out.println(LineBusI);
			
				Ybus[LineBusI][LineBusI]=(Ybus[LineBusI][LineBusI]).plus((Ybus[LineBusI][LineBusI]),Ysh);
		}
		
	for (int i=0; i<BusBarSectionList.size(); i++) {
		for (int j=0; j<BusBarSectionList.size(); j++) {
		
		System.out.println("Admittance from bus to bus");
		System.out.println((Ybus[i][j]).StringForm((Ybus[i][j]))+"\t");
				}
		System.out.println("\t");
		}
		
		}
	
catch (Exception e) {
			e.printStackTrace();
}
		
	}}
	


	
	
	
	
