import java.util.ArrayList;
/**
 * 
 * @author Xiaotong Yang
 * @version 1.0
 *
 */
public class Polynomial {
	private ArrayList<Integer> polyList;
	
	/**
	 * Create a new Polynomial object with empty list.
	 */
	public Polynomial() {
		this.polyList = new ArrayList<Integer>();
	}
	
	/**
	 * Create a new Polynomial object with an array containing the 
	 * coefficient from highest degree term to lowest degree term 
	 * (the constant term with degree 0).
	 * 
	 * @param coe coefficients of the polynomial
	 */
	public Polynomial(int[] coe) {
		for(int i = 0; i < coe.length; i++) {
			this.polyList.add(coe[i]);
		}
	}
	
	/**
	 * Replace the polynomial object with the example polynomial: 
	 * 10x^5+86x^4+158x^3-254x^2-792x^1-360
	 * <p>
	 * For developing purpose only.
	 */
	public void example() {
		/**
		 * Clear all element from Polynomial object.
		 */
		this.polyList.clear();
		/**
		 * Fill the object with example polynomial.
		 */
		this.polyList.add(10);
		this.polyList.add(86);
		this.polyList.add(158);
		this.polyList.add(-254);
		this.polyList.add(-792);
		this.polyList.add(-360);
	}
	
	/**
	 * Replace the polynomial object with the example polynomial: 
	 * 3x^3-4x^2+5x^1-1
	 * <p>
	 * For developing purpose only.
	 */
	public void example2() {
		/**
		 * Clear all element from Polynomial object.
		 */
		this.polyList.clear();
		/**
		 * Fill the object with example polynomial.
		 */
		this.polyList.add(3);
		this.polyList.add(-4);
		this.polyList.add(5);
		this.polyList.add(-1);
	}
	
	/**
	 * Use index of array list to get coefficient.
	 * @param index index of array list
	 * @return 		coefficient
	 */
	public Integer get(int index) {
		return this.polyList.get(index);
	}
	
	/**
	 * Returns the last term in this polynomial.
	 * @return the last term in this polynomial (constant)
	 */
	public Integer getLast() {
		return this.get(this.size()-1);
	}
	
	public boolean add(Integer coefficient) {
		return this.polyList.add(coefficient);
	}
	
	/**
	 * @return the number of terms in this polynomial
	 */
	public int size() {
		return this.polyList.size();
	}
	
	/**
	 * Return highest degree of current polynomial.
	 * @return highest degree of current polynomial
	 */
	public int highestDeg() {
		return this.polyList.size()-1;
	}
	
	/**
	 * Use index of array list to get degree.
	 * @param index index of array list
	 * @return		degree of corresponding term
	 * @throws Exception 
	 */
	public int getDeg(int index) throws Exception {
		if(!(index < this.polyList.size())) {
			throw new InvalidIndexException(index);
		}
		return this.polyList.size()-1-index;
	}

	@Override
	public String toString() {
		String polyExp = "";
		String temp = "";
		temp = this.polyList.get(0)+"x^"+this.highestDeg();
		polyExp += temp;
		for(int i = 1; i < this.polyList.size(); i++) {
			int coe = this.polyList.get(i);
			temp = "";
			try {
				temp = this.polyList.get(i)+"x^"+this.getDeg(i);
				if(coe >= 0) {
					temp = "+"+temp;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			polyExp += temp;
		}
		//return polyList.toString();
		return polyExp;
	}
	
	
	
} // class:Poly

class InvalidIndexException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1083290251302954139L;

	InvalidIndexException(int input) {
		super("Index out of bound! Index: " + input);
	}
}