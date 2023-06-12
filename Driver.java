
public class Driver {
	public static void main(String [] args) {
//		////// own test cases
//		double[] c1 = {1, 2, 3};
//		int[] e1 = {0,1,2};
//		int[] e2 = {0,2,3};
//		
//		Polynomial p = new Polynomial(c1,e1);
//		Polynomial q = new Polynomial(c1, e2);
//		p.multiply(q);
		
		
		/// actual test
		Polynomial p = new Polynomial();
//		System.out.println(p.evaluate(3));
		double [] c1 = {6,5};
		int[] e1 = {0 ,3};
		Polynomial p1 = new Polynomial(c1, e1);
		double [] c2 = {-2, -9};
		int[] e2 = {1, 4};
		Polynomial p2 = new Polynomial(c2, e2);
		Polynomial s = p1.add(p2);
		Polynomial t = p1.multiply(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
		
		double one = -1;
		double onetwoone = 121;
		
		if((t.evaluate(1) * one) == onetwoone)
			System.out.println("1 evaluates to 121");
		else
			System.out.println("1 does not evaluate to 121");
		
		
		
	}
}
