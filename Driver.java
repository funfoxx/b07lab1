import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver {
	public static void main(String [] args) throws FileNotFoundException, IOException{
		Polynomial p = new Polynomial();
		// System.out.println(p.evaluate(3));
		double [] c1 = {6,5};
		int [] e1 = {0,3};
		Polynomial p1 = new Polynomial(c1, e1);
		System.out.println(p1.evaluate(2));
		double [] c2 = {-2,-9};
		int [] e2 = {1,4};
		Polynomial p2 = new Polynomial(c2, e2);
		Polynomial s = p1.add(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
		System.out.println("1 is not a root of s");
		double [] c3 = {3,1};
		int [] e3 = {1,2};
		Polynomial k1 = new Polynomial(c3,e3);
		double [] c4 = {2,1};
		int [] e4 = {2,3};
		Polynomial k2 = new Polynomial(c4, e4);
		Polynomial l = k1.multiply(k2);
		System.out.println("l(1) = " + l.evaluate(1));
		File file = new File("file_read_test.txt");
		Polynomial o1 = new Polynomial(file);
		System.out.println("o(2) = " + o1.evaluate(2));
		double [] c5 = {4,1,3,-5};
		int [] e5 = {0,2,3,4};
		Polynomial k5 = new Polynomial(c5,e5);
		File new_file = k5.saveToFile("file_write_test.txt");

	}
}