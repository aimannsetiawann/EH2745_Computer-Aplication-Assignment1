package project1;

public class CalculationToPerUnitSystem {
	

	public CalculationToPerUnitSystem() {}
	
	Complexnumber zline=new Complexnumber (0,0);
	Complexnumber ZbaseLinePU=new Complexnumber (0,0);
	double ZbaseLine=0.0;
	double VbaseLine=0.0;
	double Sbase=0.0;
	
	public void perUnitLine(Complexnumber Zline, double BaseVoltage, double BasePower ) {
		
		ZbaseLine=(((VbaseLine*1000)*(VbaseLine*1000))/(1000000*Sbase));
		
		ZbaseLinePU= zline. reciprocal();
		
	}
}

