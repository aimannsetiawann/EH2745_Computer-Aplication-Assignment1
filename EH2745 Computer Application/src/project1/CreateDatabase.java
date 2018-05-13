package project1;

import java.sql.*;

public class CreateDatabase {
	

	private String Name;
	private String Pass;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";
	
	Connection conn = null;
    Statement stmt = null;
    String sql = null; // A variable which is used to store mySQL command in string

    // With this method we create data base in mySQL with specified username and password
	public CreateDatabase (String USERNAME,String PASS ) {
		
		Name=USERNAME;
		Pass=PASS;   
		
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to mySQL local server...");
			conn = DriverManager.getConnection(DB_URL, Name, Pass);
			
			// Because we want to try the program several times, before we create the database  we need to drop the previous one since we are going to create database with same name
			stmt = conn.createStatement();
			sql = "DROP DATABASE IF EXISTS POWER";
		    stmt.executeUpdate(sql);
			
		    // Creating the database called POWER SYSTEM and show user if database has been created sucsessfully
		    sql = "CREATE DATABASE POWER"; 
		    stmt.executeUpdate(sql);
		    
		    // State POWER SYSTEM as database, which are going to be used to create table and to store data 
		    conn = DriverManager.getConnection(DB_URL + "POWER", Name, Pass);
		    sql = "USE POWER"; 
		    stmt.executeUpdate(sql) ; 
			
		}
		
	    catch(SQLException se){
		se.printStackTrace();
		}
	    catch(Exception e){
		e.printStackTrace();
		}
		
try {
	
	// Before we create table we drop the specified table first, because we want to try program several times
	// We insert my sql command to sql variable, and every time we insert new value to variable we need to execute it
	// For different data table we also specified t types of variable we want store to the table
	// After that we notice user that table has been sucsessfully created
			
						//////////////////////////// Table for Base Voltage Data /////////////////////////////////
						sql = "DROP TABLE IF EXISTS BaseVoltage"; 
						stmt.executeUpdate(sql) ;
						sql = "CREATE TABLE IF NOT EXISTS BaseVoltage (ID VARCHAR(37) NOT NULL, Value_kV Double , PRIMARY KEY (ID))";
						stmt.executeUpdate(sql) ; // execute query
						
						////////////////////////////// Table for Substation Data /////////////////////////////////
						sql = "DROP TABLE IF EXISTS Substation";
						stmt.executeUpdate(sql) ;
						sql = "CREATE TABLE IF NOT EXISTS Substation (ID VARCHAR(37) NOT NULL, Name VARCHAR(37), Region_rdfID VARCHAR(37), PRIMARY KEY(ID))";
						stmt.executeUpdate(sql) ; // execute query   
						
						////////////////////////////// Table for Voltage Level Data //////////////////////////////
						sql = "DROP TABLE IF EXISTS VoltageLevel";
						stmt.executeUpdate(sql) ;
						sql = "CREATE TABLE IF NOT EXISTS VoltageLevel (ID VARCHAR(37) NOT NULL, Value_kV Double , Substation_rdfID VARCHAR(37),BaseVoltage_rdfID VARCHAR(37), PRIMARY KEY (ID))";
						stmt.executeUpdate(sql) ; // execute query
						
						//////////////////////////////Table for Generating Unit Data //////////////////////////////
						sql = "DROP TABLE IF EXISTS GeneratingUnit";
						stmt.executeUpdate(sql) ;
						sql = "CREATE TABLE IF NOT EXISTS GeneratingUnit (ID VARCHAR(37) NOT NULL, Name VARCHAR(37), MaxP DOUBLE, MinP DOUBLE, EquipmentContainer_rdfID VARCHAR(37), PRIMARY KEY (ID))";
						stmt.executeUpdate(sql) ; // execute query
									
						//////////////////////////////Table for Synchronous Machine Data //////////////////////////////
						sql = "DROP TABLE IF EXISTS SynchronousMachine";
						stmt.executeUpdate(sql) ;
						sql = "CREATE TABLE IF NOT EXISTS SynchronousMachine"
								+ "(ID VARCHAR(37) NOT NULL, Name VARCHAR(40), RatedS DOUBLE, P DOUBLE, Q DOUBLE,"
								+ "GenUnit_rdfID VARCHAR(37), RegControl_rdfID VARCHAR(37),"
								+ "EquipmentContainer_rdfID VARCHAR(37), baseVoltage_rdfID VARCHAR(37), PRIMARY KEY (ID))";
						stmt.executeUpdate(sql) ; // execute query
						
						//////////////////////////////Table for Regulating Control Data //////////////////////////////
						sql = "DROP TABLE IF EXISTS RegulatingControl";
						stmt.executeUpdate(sql) ;
						sql = "CREATE TABLE IF NOT EXISTS RegulatingControl (ID VARCHAR(37) NOT NULL, Name VARCHAR(37), TargetValue DOUBLE, PRIMARY KEY (ID))";
						stmt.executeUpdate(sql) ; // execute query
									
						//////////////////////////////Table for Power Transformer////////////////////////////////////
						sql = "DROP TABLE IF EXISTS PowerTransformer";
						stmt.executeUpdate(sql) ;
						sql = "CREATE TABLE IF NOT EXISTS PowerTransformer (ID VARCHAR(37) NOT NULL, Name VARCHAR(37),EquipmentContainer_rdfID VARCHAR(37), PRIMARY KEY (ID))";
						stmt.executeUpdate(sql) ; // execute query
						
						//////////////////////////////Table for Energy Consumer////////////////////////////////////
						sql = "DROP TABLE IF EXISTS EnergyConsumer";
						stmt.executeUpdate(sql) ;
						sql = "CREATE TABLE IF NOT EXISTS EnergyConsumer(ID VARCHAR(37) NOT NULL, Name VARCHAR(37), P DOUBLE, Q DOUBLE, EquipmentContainer_rdfID VARCHAR(37), BaseVoltage_rdfID VARCHAR(37),  PRIMARY KEY (ID))";		
						stmt.executeUpdate(sql) ; // execute query
						
						//////////////////////////////Table for Power Transformer End//////////////////////////////////
						sql = "DROP TABLE IF EXISTS TransformerWinding";
						stmt.executeUpdate(sql) ;
						sql = "CREATE TABLE IF NOT EXISTS TransformerWinding (ID VARCHAR(40) NOT NULL, Name VARCHAR(40), TransformerR DOUBLE,"
								+ "TransformerX DOUBLE, Transformer_rdfID VARCHAR(37), BaseVoltage_rdfID VARCHAR(37),PRIMARY KEY (ID))";
						stmt.executeUpdate(sql) ; // execute query
						
						////////////////////////////////Table for Circuit Breaker////////////////////////////////////
						sql = "DROP TABLE IF EXISTS CircuitBreaker";
						stmt.executeUpdate(sql) ;
						sql = "CREATE TABLE IF NOT EXISTS CircuitBreaker (ID VARCHAR(37) NOT NULL, Name VARCHAR(37), State VARCHAR(37), EquipmentContainer_rdfID VARCHAR(37), BaseVoltage_rdfID VARCHAR(37),PRIMARY KEY (ID))";
						stmt.executeUpdate(sql) ; // execute query
						
						///////////////////////////////Table for Tap Changer////////////////////////////////////////
						sql = "DROP TABLE IF EXISTS RatioTapChanger";
						stmt.executeUpdate(sql) ;
						sql = "CREATE TABLE IF NOT EXISTS RatioTapChanger (ID VARCHAR(37) NOT NULL, Name VARCHAR(37), Step DOUBLE, PRIMARY KEY (ID))";					
						stmt.executeUpdate(sql) ; // execute query
						
						
						System.out.println("The database POWER SYSTEM and all the tables have been created sucsessfully");
			
		}

		catch(SQLException se){
		    se.printStackTrace();
		    }
		catch(Exception e){
		    e.printStackTrace();
		    }
	}
	
	// In this section we create different methods to insert the parsed data to specified table
	// Since there are 14 different data class there are 14 methods to insert the data to their corresponding table
	
	// There are three steps to insert the data into their corresponding table
		//1. Creating query command to insert values to specified table for example "INSERT INTO BaseVoltage VALUES(?,?)", it means inserting two values to table called "BaseVoltage"
		//2. prepare statement to execute query
		//3. Specified the first value with its type
		//4. Specified the n value with its type
		//5. Execute the command
	
	// This is a method for creating table for Base Voltage data
	// The method has input of Base Voltage RDFID and nominal value (kV)
	public void TableBV(String rdfID, double Value){
		try {
			
			String query = "INSERT INTO BaseVoltage VALUES(?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1,rdfID); 
			preparedStmt.setDouble(2,Value);  
			preparedStmt.executeUpdate();  
			
			System.out.println("Create data to BaseVoltage Table successfully...");
		   }
		catch(SQLException se){

		se.printStackTrace();}
		catch(Exception e){

		e.printStackTrace();}			
		}

	// This is a method for creating table for Substation data
	// The method has input of Base Voltage rdfID, name, and region
	public void TableSubstation(String rdfID, String Name, String Region){
		try {
			
			String query = "INSERT INTO Substation VALUES(?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1,rdfID);
			preparedStmt.setString(2,Name);
			preparedStmt.setString(3,Region);
			preparedStmt.executeUpdate(); 
			
			System.out.println("Create data to Substation Table successfully...");
		   }
		catch(SQLException se){
	
		se.printStackTrace();}
		catch(Exception e){
		
		e.printStackTrace();}			
		}
	
	// This is a method for creating table for Substation data
	// The method has input of substation rdfID, name, and region
	public void TableVoltageLevel(String rdfID, double Value, String Substation, String BaseV){
		try {
			
			String query = "INSERT INTO VoltageLevel VALUES (?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1,rdfID);
			preparedStmt.setDouble(2,Value);
			preparedStmt.setString(3,Substation);
			preparedStmt.setString(4,BaseV);
			preparedStmt.executeUpdate(); 
			
			System.out.println("Create data to Voltage Level Table successfully...");
		   }
		catch(SQLException se){
	   
		se.printStackTrace();}
		catch(Exception e){
		
		e.printStackTrace();}			
		}
	
	// This is a method for creating table for Generating Unit data
	// The method has input of generating unit rdfID, name, maximum and minimum power, and equipment container ID
	public void TableGeneratingUnit(String rdfID, String Name, double maxP, double minP, String ECID){
		try {
			
			String query = "INSERT INTO GeneratingUnit VALUES (?,?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1,rdfID);
			preparedStmt.setString(2,Name);
			preparedStmt.setDouble(3,maxP);
			preparedStmt.setDouble(4,minP);
			preparedStmt.setString(5,ECID);
			preparedStmt.executeUpdate(); 
			
			System.out.println("Create data to Generating UnitTable successfully...");
			
		   }
		catch(SQLException se){
	    
		se.printStackTrace();}
		catch(Exception e){
		
		e.printStackTrace();}			
		}
	
	// This is a method for creating table for Synchronous Machine data
	// The method has input of Synchronous Machine rdfID, name, base power rating, active power, reactive power, generating unit rdfID, regulating control rdfID, and base votage rdfID
	public void TableSynchronousMachine(String rdfID, String Name, double Srating, double syncMP, double syncMQ, String GUrdfID, String RCrdfID,  String ECrdfID,  String BVrdfID ){
		try {
			
			String query = "INSERT INTO SynchronousMachine VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1,rdfID);
			preparedStmt.setString(2,Name);
			preparedStmt.setDouble(3,Srating);
			preparedStmt.setDouble(4,syncMP);
			preparedStmt.setDouble(5,syncMQ);
			preparedStmt.setString(6,GUrdfID);
			preparedStmt.setString(7,RCrdfID);
			preparedStmt.setString(8,ECrdfID);
			preparedStmt.setString(9,BVrdfID);
			preparedStmt.executeUpdate(); // execute PreparedStatement
			
			System.out.println("Create data to Synchronous Machine Table successfully...");
		   }
		catch(SQLException se){
	    
		se.printStackTrace();}
		catch(Exception e){
		
		e.printStackTrace();}			
		}
	
	// This is a method for creating table for regulating control data
	// The method has input of regulating control  rdfID, name, and target value
	public void TableRC(String rdfID, String Name, double TargetValue){
		try {
			
			String query = "INSERT INTO RegulatingControl VALUES(?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.setString(1,rdfID);
			preparedStmt.setString(2,Name);
			preparedStmt.setDouble(3,TargetValue);
			
			preparedStmt.executeUpdate(); 
			
			System.out.println("Create data to Regulating Control Table successfully...");
			
		   }
		catch(SQLException se){
	
		se.printStackTrace();}
		catch(Exception e){
		
		e.printStackTrace();}			
		}
	
	// This is a method for creating table for transformer data
	// The method has input of transformer  rdfID, name, and equipment container
	public void TableTransformer(String rdfID, String Name, String PTErdf){
		try {
			
			String query = "INSERT INTO PowerTransformer VALUES(?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.setString(1,rdfID);
			preparedStmt.setString(2,Name);
			preparedStmt.setString(3,PTErdf);
			
			preparedStmt.executeUpdate(); 
			
			System.out.println("Create data to Power Transformer Table successfully...");
			
		   }
		catch(SQLException se){
	    
		se.printStackTrace();}
		catch(Exception e){
		
		e.printStackTrace();}			
		}
	
	// This is a method for creating table for energy consumer data
	// The method has input of energy consumer  rdfID, name, active power, reactive power, equipment container rdfID, and base voltage rdfID
	public void TableEnergyConsumer(String rdfID, String Name, double P, double Q, String ECID, String BVID){
		try {
			
			String query = "INSERT INTO EnergyConsumer VALUES (?,?,?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1,rdfID);
			preparedStmt.setString(2,Name);
			preparedStmt.setDouble(3,P);
			preparedStmt.setDouble(4,Q);
			preparedStmt.setString(5,ECID);
			preparedStmt.setString(6,BVID);
			preparedStmt.executeUpdate(); // 
			
			System.out.println("Create data to Energy Consumer Table successfully...");
			
		   }
		catch(SQLException se){
	    
		se.printStackTrace();}
		catch(Exception e){
		
		e.printStackTrace();}			
		}
	
	// This is a method for creating table for transformer end data
	// The method has input of transformer end  rdfID, name, impedance data, transformer rdfID, and base voltage rdfID
	public void TableTransformerEnd (String rdfID, String Name, double r, double x, String TrdfID, String BVID){
		try {
			
			String query = "INSERT INTO TransformerWinding VALUES (?,?,?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1,rdfID);
			preparedStmt.setString(2,Name);
			preparedStmt.setDouble(3,r);
			preparedStmt.setDouble(4,x);
			preparedStmt.setString(5,TrdfID);
			preparedStmt.setString(6,BVID);
			preparedStmt.executeUpdate(); 
			
			System.out.println("Create data to Power Transformer Windings Table successfully...");
			
		   }
		catch(SQLException se){
	    
		se.printStackTrace();}
		catch(Exception e){
		
		e.printStackTrace();}			
		}
	
	// This is a method for creating table for transformer end data
	// The method has input of transformer end  rdfID, name, impedance data, transformer rdfID, and base voltage rdfID
	public void TableCircuitBreaker (String rdfID, String Name, String State, String ECID, String BVID){
		try {
			
			String query = "INSERT INTO CircuitBreaker VALUES(?,?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.setString(1,rdfID);
			preparedStmt.setString(2,Name);
			preparedStmt.setString(3,State);
			preparedStmt.setString(4,ECID);
			preparedStmt.setString(5,BVID);
			
			preparedStmt.executeUpdate(); 
			
			System.out.println("Create data to Circuit Breaker Table successfully...");
			
		   }
		catch(SQLException se){
		    
			se.printStackTrace();}
			catch(Exception e){
			
			e.printStackTrace();}			
			}
	
	// This is a method for creating table for ratio tap changer data
	// The method has input of ratio tap changer rdfID, name, and step
	public void TableRatioTapChanger(String rdfID, String Name, double STEP){
		try {
			
			String query = "INSERT INTO RatioTapChanger VALUES(?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.setString(1,rdfID);
			preparedStmt.setString(2,Name);
			preparedStmt.setDouble(3,STEP);
			
			preparedStmt.executeUpdate(); 
			
			System.out.println("Create data to Ratio Tap Changer Table successfully...");
			
		   }
		catch(SQLException se){
	   
		se.printStackTrace();}
		catch(Exception e){
		
		e.printStackTrace();}			
		}


	
}
	


