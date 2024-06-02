import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Polynomial{
	// field
	double [] coefficients;
	int [] exponents;

	// methods
	public Polynomial(){
		coefficients = null;
		exponents = null;
	}

	public Polynomial(double [] coe, int [] exp){
		coefficients = new double[coe.length];
		exponents = new int[exp.length];
		for(int i=0; i<coe.length; i++){
			coefficients[i] = coe[i];
		}
		for(int i=0; i<exp.length; i++){
			exponents[i] = exp[i];
		}
	}

	public Polynomial(File file) throws FileNotFoundException{
		Scanner input = new Scanner(file);
		String text = input.nextLine();
		text = text.replace("+", "#");
		text = text.replace("-", "#-");
		
		String [] split_text = text.split("#", -1);
		double [] final_coe = new double[split_text.length];
		int [] final_exp = new int[split_text.length];
		int counter = 0;
		for(int i=0; i<split_text.length; i++){
			String split_text_chunk = split_text[i];
			if(split_text_chunk.indexOf("x") == -1){
				double coe = Double.parseDouble(split_text_chunk);
				final_coe[counter] = coe;
				final_exp[counter] = 0;
				counter++;
			}
			else{
				String [] split_x = split_text_chunk.split("x", -1);
				double coe = Double.parseDouble(split_x[0]);
				int exp = Integer.parseInt(split_x[1]);
				final_coe[counter] = coe;
				final_exp[counter] = exp;
				counter++;
			}
		}
		coefficients = final_coe;
		exponents = final_exp;
	}

	public int getIndex(int x, int [] p){
		for(int i=0; i<p.length; i++){
			if(p[i] == x) return i;
		}
		return -1;
	}

	public int getIntArrayMax(int [] p){
		int max = 0;
		for(int i=0; i<p.length; i++){
			max = Math.max(p[i], max);
		}
		return max;
	}

	public Polynomial add(Polynomial p){
		int max_size = Math.max(getIntArrayMax(p.exponents), getIntArrayMax(this.exponents)) + 1;
		int [] possible_exp = new int[max_size];
		double [] possible_coe = new double[max_size];
		for(int i=0; i<possible_exp.length; i++){
			possible_exp[i] = i;
		}

		// get el in this
		for(int i=0; i<this.exponents.length; i++){
			int idx = getIndex(this.exponents[i], possible_exp);
			possible_coe[idx] += this.coefficients[i];
		}

		// get el in other
		for(int i=0; i<p.exponents.length; i++){
			int idx = getIndex(p.exponents[i], possible_exp);
			possible_coe[idx] += p.coefficients[i];
		}

		// count how many non zero in possible_coe
		int size = 0;
		for(int i=0; i<possible_coe.length; i++){
			if(possible_coe[i] != 0){
				size++;
			}
		}

		// create 
		double [] final_coe = new double[size];
		int [] final_exp = new int[size];

		// fill new
		int final_index = 0;
		for(int i=0; i<possible_coe.length; i++){
			if(possible_coe[i] != 0){
				final_coe[final_index] = possible_coe[i];
				final_exp[final_index] = possible_exp[i];
				final_index++;
			}
		}

		Polynomial new_poly = new Polynomial(final_coe, final_exp);
		return new_poly;
	}

	public double evaluate(double x){
		double sum = 0.0;
		for(int i=0; i<this.exponents.length; i++){
			sum += this.coefficients[i] * Math.pow(x, this.exponents[i]);
		}
		return sum;
	}

	public boolean hasRoot(double x){
		double result = this.evaluate(x);
		return (result == 0);
	}

	public Polynomial multiply(Polynomial p){
		int max_size = this.coefficients.length * p.coefficients.length;
		int step_counter = 0;
		double [] max_coe = new double[max_size];
		int [] max_exp = new int[max_size];
		for(int i=0; i<this.coefficients.length; i++){
			for(int j=0; j<p.coefficients.length; j++){
				max_coe[step_counter] = this.coefficients[i] * p.coefficients[j];
				max_exp[step_counter] = this.exponents[i] + p.exponents[j];
				step_counter++;
			}
		}

		double [] temp_coe = new double[max_size];
		int [] temp_exp = new int[max_size];
		for(int i=0; i<temp_exp.length; i++){
			temp_exp[i] = -1;
		}
		int step_counter_2 = -1;
		for(int i=0; i<max_exp.length; i++){
			if(getIndex(max_exp[i], temp_exp) == -1){
				step_counter_2++; 
				temp_coe[step_counter_2] = max_coe[i];
				temp_exp[step_counter_2] = max_exp[i];
			}
			else{
				int repeat_index = getIndex(max_exp[i], temp_exp);
				temp_coe[repeat_index] += max_coe[i];
			}
		}

		int length_counter = 0;
		for(int i=0; i<temp_exp.length; i++){
			if(temp_exp[i] != -1) length_counter++;
		}

		double [] final_coe = new double[length_counter];
		int [] final_exp = new int[length_counter];

		for(int i=0; i<final_exp.length; i++){
			final_coe[i] = temp_coe[i];
			final_exp[i] = temp_exp[i];
		}

		Polynomial final_poly = new Polynomial(final_coe, final_exp);
		return final_poly;
	}

	public File saveToFile(String file_name) throws IOException{
		FileWriter writer = new FileWriter(file_name, false);
		String final_string = "";
		for(int i=0; i<this.coefficients.length; i++){
			if(this.exponents[i] == 0){
				String temp = this.coefficients[i] + "+";
				final_string = final_string + temp;
			}
			else{
				String temp = this.coefficients[i] + "x" + this.exponents[i] + "+";
				final_string = final_string + temp;
			}
		}
		final_string = final_string.replace("+-", "-");
		final_string = final_string.substring(0, final_string.length() - 1);
		writer.write(final_string);
		writer.close();

		File file = new File(file_name);
		return file;
	}

}