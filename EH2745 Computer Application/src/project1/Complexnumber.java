package project1;

public class Complexnumber {

	private double real;
	private double imag;
	
	public Complexnumber (double Re, double Im) {
		
		this.real=Re;
		this.imag=Im;
		}
	
	public Complexnumber plus (Complexnumber a, Complexnumber b){
		double real = a.real + b.real;
		double imag = a.imag + b.imag;
		
		Complexnumber sum = new Complexnumber (real,imag);
		
		return sum;
	}
	
	public Complexnumber minus (Complexnumber a, Complexnumber b){
		double real = a.real - b.real;
		double imag = a.imag - b.imag;
		Complexnumber min = new Complexnumber (real,imag);
		
		return min;
	}

    public Complexnumber reciprocal() {
    	
    	Complexnumber a=this;
    	
        double scale = a.real*a.real + a.imag*a.imag;
        
        double real=(a.real)/scale;
        double imag=(a.imag)/scale;
        
    	return new Complexnumber(real,-imag);
    }
	
    public Complexnumber MultyCons(Complexnumber a,double b) {
    	 
    	double real=(a.real)*b;
    	double imag=(a.imag)*b;
    	
    	return new Complexnumber(real, imag);
        
    }
    
    
    public Complexnumber devideCons(Complexnumber a,double b) {
    	 
    	double real=(a.real)/b;
    	double imag=(a.imag)/b;
    	
    	return new Complexnumber(real, imag);
        
    }
    
	public String StringForm(Complexnumber a) 
	{
	       if (a.imag == 0) return real + "";
	       if (a.real == 0) return a.imag + "i";
	       if (a.imag <  0) return a.real + " - " + (-a.imag) + "i";
	       return a.real + " + " + a.imag + "i";
	    } 
	
	
	}

