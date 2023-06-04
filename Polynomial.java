 public class Polynomial {
 	double[] coefficients;

 	public Polynomial(){
 		this.coefficients = new double[0]; // use this for clarification, should work with just coe.
 	}

 	public Polynomial(double[] coefficients){
 		this.coefficients = coefficients;
 	}

 	public Polynomial add(Polynomial other){

 		int length = other.coefficients.length;

 		if (this.coefficients.length > length){
 			length = this.coefficients.length;
 		}

 		// int length = (this.coefficients.length > other.coefficients.length) ? this.coefficients.length : other.coefficients.length; one line way showed in tut

 		double[] newPoly = new double[length];

 		for(int i = 0; i < this.coefficients.length; i++){
 			 newPoly[i] = this.coefficients[i];
 		}

 		for(int i = 0; i < other.coefficients.length; i++){
 			 newPoly[i] += other.coefficients[i];
 		}

 		return new Polynomial(newPoly);
 	
 	}

 	public double evaluate(double x){
 		double sum = 0;
 		double scale = 1;

 		for(double i: this.coefficients){
 			sum += scale * i;
 			scale = scale * x;
 		}

 		return sum;
 	}

 	public boolean hasRoot(double root){
 		if(evaluate(root) == 0) return true;

 		return false;

 	}

 }