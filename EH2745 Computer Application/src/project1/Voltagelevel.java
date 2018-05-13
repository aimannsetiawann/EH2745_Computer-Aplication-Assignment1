package project1;

public class Voltagelevel {
			
		private String VLrdfID;
		private double Value;
		private String SubsID;
		private String BaseVID;
		
		public Voltagelevel(String VLsrdfID,String VLname,String VLSubsrdfID, String VLBaserdfID) {
			
			try {
			VLrdfID = VLsrdfID;
			Value = Double.parseDouble(VLname);
			SubsID= VLSubsrdfID;
			BaseVID= VLBaserdfID;
			
			}
			
			catch (NumberFormatException ex) {}
			
		}
		
		public String GetVLrdfID(){ return VLrdfID; }
		public double GetValueVL(){ return Value; }
		public String GetSubsID(){ return SubsID; }
		public String GetBaseVID(){ return BaseVID; }
		

}

