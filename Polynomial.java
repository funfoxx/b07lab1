public class Polynomial{
	// field
	double [] coefficients;

	// methods
	public Polynomial(){
		coefficients = new double[1];
		coefficients[0] = 0;
	}

	public Polynomial(double [] coe){
		coefficients = new double[coe.length];
		for(int i=0; i<coe.length; i++){
			coefficients[i] = coe[i];
		}
	}

	public Polynomial add(Polynomial p){
		int max_length = Math.max(p.coefficients.length, this.coefficients.length);
		int min_length = Math.min(p.coefficients.length, this.coefficients.length);
		int dif = max_length - min_length;
		double [] sum = new double [max_length];

		for(int i=0; i<min_length; i++){
			sum[i] = p.coefficients[i] + this.coefficients[i];
		}
		if(dif == 0){
			Polynomial new_poly = new Polynomial(sum);
			return new_poly;
		}
		else{
			if(p.coefficients.length == max_length)
				for(int j=0; j<dif; j++){
					sum[min_length + j] = p.coefficients[min_length + j];
				}
			else 
				for(int j=0; j<dif; j++){
					sum[min_length + j] = this.coefficients[min_length + j];
				}
		}
		Polynomial new_poly = new Polynomial(sum);
		return new_poly;
	}

	public double evaluate(double x){
		int i;
		double total = 0.0;
		for(i=0;i<coefficients.length;i++){
			double pow = i;
			total += coefficients[i] * Math.pow(x, pow);
		}
		return total;
	}

	public boolean hasRoot(double x){
		double result = evaluate(x);
		return (result == 0);
	}
} 