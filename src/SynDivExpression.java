import java.util.ArrayList;

/**
 * 
 * @author Xiaotong Yang
 * @version 0.8
 */
public class SynDivExpression {
	
	private ArrayList<Integer> upperCoe = new ArrayList<Integer>();
	private ArrayList<Double> midValue = new ArrayList<Double>();
	private ArrayList<Integer> lowerCoe = new ArrayList<Integer>();
	private double d;
	
	public SynDivExpression(Polynomial poly, double divisor) {
		int i = poly.size();
		for(i = 0; i < poly.size(); i++) {
			upperCoe.add(poly.get(i));
		}
		midValue.add(0.0);
		lowerCoe.add(poly.get(0));
		this.d = divisor;
	}
	
	public void addMidValue(double val) {
		midValue.add(val);
	}
	
	public void addLowerCoe(int val) {
		lowerCoe.add(val);
	}

	public void output() {
		String str;
		if(d > 0) {
			str = "+"+d;
		} else {
			str = ""+d;
		}
		
		String polyExp = "";
		String temp = "";
		temp = upperCoe.get(0)+"x^"+(upperCoe.size()-1);
		polyExp += temp;
		for(int i = 1; i < upperCoe.size(); i++) {
			int coe = upperCoe.get(i);
			temp = "";
			if(coe < 0) {
				temp = upperCoe.get(i)+"x^"+(upperCoe.size()-1-i);
			} else {
				temp = "+"+upperCoe.get(i)+"x^"+(upperCoe.size()-1-i);
			}
			polyExp += temp;
		}
		System.out.println("("+polyExp+")/"+"(x"+str+")=");
		System.out.println(this.toString());
	}
	
	@Override
	public String toString() {
		return upperCoe.toString()+"\n"+midValue.toString()+"\n"+lowerCoe.toString();
	}

}
