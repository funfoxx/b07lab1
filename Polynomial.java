import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    // null construct
    public Polynomial(){
        this.coefficients = null;
        this.exponents = null;
    }

    // normal construct
    public Polynomial(double[] coefficients, int[] exponents){
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    // file construct
    public Polynomial(File file) throws Exception{
        
        Scanner myScanner = new Scanner(file);

        if(!myScanner.hasNextLine()){

            this.coefficients = null;
            this.exponents = null;

        }

        else{

            String line = myScanner.nextLine();
            line.replace("-", "+-");

            // we get [5, -3x2, 7x8]
            String[] polyArr = line.split("\\+");

            this.exponents = new int[polyArr.length];
            this.coefficients = new double[polyArr.length];

            for(int i = 0; i < polyArr.length; i++){

                String[] subArray = polyArr[i].split("x"); // might need subArray[], don't think so though

                coefficients[i] = Double.parseDouble(subArray[0]);

                if(subArray.length > 1){

                    exponents[i] = Integer.parseInt(subArray[1]);

                }

                else{

                    exponents[i] = 0;

                }

            }

        }

        myScanner.close();

    }

    public Polynomial add(Polynomial other){
        
        // if both are empty
        if(other.coefficients.length == 0 && this.coefficients.length == 0) {
            return new Polynomial();
        }
        
        // if one is empty
        if(other.coefficients.length == 0 || this.coefficients.length == 0) {
            if (other.coefficients.length == 0){
                return new Polynomial(this.coefficients, this.exponents);
            }
            
            return new Polynomial(other.coefficients, other.exponents);
        }
        
        // finding the number of unique exponent values
        int[] lengthArr = new int[this.exponents.length + other.exponents.length];
        
        // add both exponent arrays to lengthArr
        for(int i = 0; i < this.exponents.length; i++) {
            lengthArr[i] = this.exponents[i];
        }
        
        for(int i = 0; i < other.exponents.length; i++) {
            lengthArr[i + this.exponents.length] = other.exponents[i];
        }
        
        Arrays.sort(lengthArr);
        
        // change all duplicates to -1
        for(int i = 0; i < lengthArr.length; i++) {
            if(lengthArr[i] != -1) {
                for(int j = i + 1; j < lengthArr.length; j++) {
                    if(lengthArr[i] == lengthArr[j]) lengthArr[j] = -1;
                }
            }
        }
        
        Arrays.sort(lengthArr);
        
        int length = 0;
        
        for(int i = 0; i < lengthArr.length; i++) {
            if(lengthArr[i] != -1) length++;
        }
        
//      System.out.println("length " + length);

        // int length = (this.coefficients.length > other.coefficients.length) ? this.coefficients.length : other.coefficients.length; one line way showed in tut

        // create new polynomial arrays
        double[] newPolyCoefficients = new double[length];
        int[] newPolyExponent = new int[length];
        
        // goes through lengthArr, moves values to newPolyExponent
        for(int i = 0; i < newPolyExponent.length; i++) {
            for(int j = 0; j < lengthArr.length; j++) {
                if(lengthArr[j] != -1) {
                    newPolyExponent[i] = lengthArr[j];
                    lengthArr[j] = -1;
                    break;
                }
            }
        }
        
//      for(int i = 0; i < newPolyExponent.length; i++) {
//          System.out.println(newPolyExponent[i]);
//      }
        
        // adding coefficients based on exponent value
        for(int l = 0; l < length; l++){
            for(int m = 0; m < this.exponents.length; m++){
                if(newPolyExponent[l] == this.exponents[m]) {
                    newPolyCoefficients[l] = this.coefficients[m];
                    
                }
            }
        }
        
        for(int l = 0; l < length; l++){
            for(int m = 0; m < other.exponents.length; m++){
                if(newPolyExponent[l] == other.exponents[m]) {
                    newPolyCoefficients[l] += other.coefficients[m];
                    
                }
            }
        }
        
//      System.out.println("Exponents");
//      for(int i = 0; i < newPolyExponent.length; i++) {
//          System.out.println(newPolyExponent[i]);
//      }
        
//      System.out.println("Coeff");
//      for(int i = 0; i < newPolyCoefficients.length; i++) {
//          System.out.println(newPolyCoefficients[i]);
//      }
        
        return new Polynomial(newPolyCoefficients, newPolyExponent);
    
    }

    public double evaluate(double x){
        
        double[] expx = new double[this.exponents.length];
        double sum = 0;
        
        for(int i = 0; i < this.exponents.length; i++) {
            
            double power = x;
            
//          System.out.println("power start " + power);
            
            for(int j = 0; j < this.exponents[i] - 1; j++) {
                
                power = power * x;
                
            }
            
            if(this.exponents[i] == 0) power = 0;
            
            
//          System.out.println("power end " + power);
            
            expx[i] = power;

        }
 
        
        for(int i = 0; i < this.exponents.length; i++) {

            sum += this.coefficients[i] * expx[i];
            
        }

        return sum;
    }
    
    public Polynomial multiply(Polynomial other) { 
        
        int[] newExponents = new int[other.exponents.length];
        double[] newCoefficients = new double[other.coefficients.length];
        
        Polynomial newPoly = new Polynomial(newCoefficients, newExponents);
        
        for(int i = 0; i < this.exponents.length; i++) {
            
            double[] loopCoefficients = new double[other.coefficients.length];
            int[] loopExponents = new int[other.coefficients.length];
            
            for(int j = 0; j < other.exponents.length; j++) {
                
                double thisCoe = this.coefficients[i];
                double otherCoe = other.coefficients[j];
                int thisExp = this.exponents[i];
                int otherExp = other.exponents[j];
                
//              System.out.println(thisCoe);
//              System.out.println(otherCoe);
//              System.out.println(thisCoe * otherCoe);
//              System.out.println("________________");
                
                loopCoefficients[j] = (thisCoe * otherCoe);
                loopExponents[j] = (thisExp + otherExp);
                
//              System.out.println("this coe " + i + " " + j + ": " + this.coefficients[i]);
//              System.out.println("this ex " + i + " " + j + ": " + this.exponents[i]);
//              System.out.println("other coe " + i + " " + j + ": " + other.coefficients[j]);
//              System.out.println("other ex " + i + " " + j + ": " + other.exponents[j]);
//              System.out.println("loop coe " + i + " " + j + ": " + loopCoefficients[j]);
//              System.out.println("loop ex " + i + " " + j + ": " + loopExponents[j]);
//              System.out.println("________________");
                    
            }
            
            Polynomial p = new Polynomial(loopCoefficients, loopExponents);
            
            newPoly = newPoly.add(p);
            
//          System.out.println("==================");
//          
//          System.out.println("Coeff");
//          for(int i1 = 0; i1 < newPoly.exponents.length; i1++) {
//              System.out.println("coe " + i1 + ": " + newPoly.coefficients[i1]);
//              System.out.println("ex " + i1 + ": " + newPoly.exponents[i1]);
//          }
        
        }
        
        
        return newPoly;
    }

    public boolean hasRoot(double root){
        if(evaluate(root) == 0) return true;

        return false;

    }

    public void saveToFile(String myFile) throws Exception{

        ////////// need to check if arrays are null or if lengths are different
        
        if(this.coefficients.length == 0 || this.exponents.length == 0 || this.coefficients.length != this.exponents.length) {
 
            return;
        }

        String writeString = "";

        for(int i = 0; i < this.coefficients.length; i++){

            writeString += coefficients[i];
            if(exponents[i] != 0){
                writeString+="x" + exponents[i];
            }
            writeString += "+";

            if(writeString.endsWith("+")){
                writeString = writeString.substring(0, writeString.length() - 1);
            }

            FileWriter myWriter = new FileWriter(new File(myFile));
            myWriter.write(writeString);
            myWriter.close();
        }

    }

 }

 