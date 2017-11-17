
public class Fraction {
	
	private int numerator;
	private int denominator;
	
	/**
	 * 
	 * @param num the numerator
	 * @param den the denominator
	 * @throws InvalidDenominatorException
	 */
	public Fraction(int num, int den) throws InvalidDenominatorException {
		if(den == 0) throw new InvalidDenominatorException();
		this.setNumerator(num);
		this.setDenominator(den);
	}
	
	/**
	 * Convert integer number into fraction.
	 * @param num an integer number which has denominator 1
	 */
	public Fraction(int num) {
		this.setNumerator(num);
		this.setDenominator(1);
	}

	public int getNumerator() {
		return this.numerator;
	}

	private void setNumerator(int num) {
		this.numerator = num;
	}

	public int getDenominator() {
		return this.denominator;
	}

	private void setDenominator(int den) {
		this.denominator = den;
	}
	
	public boolean isInteger() {
		return this.getDenominator()==1;
	}
	
	/**
	 * @param rhs the addend
	 * @return	  the sum of two fraction
	 * @throws InvalidDenominatorException 
	 */
	public Fraction add(Fraction rhs) throws InvalidDenominatorException {
		int newNum = this.numerator * rhs.denominator 
					+ rhs.numerator * this.denominator;
		int newDen = this.denominator * rhs.denominator;
		return new Fraction(newNum, newDen).trim();
	}
	
	/**
	 * @param rhs the subtrahend
	 * @return	  the difference of two fraction 
	 * @throws InvalidDenominatorException 
	 */
	public Fraction sub(Fraction rhs) throws InvalidDenominatorException {
		int newNum = this.numerator * rhs.denominator 
					- rhs.numerator * this.denominator;
		int newDen = this.denominator * rhs.denominator;
		return new Fraction(newNum, newDen).trim();
	}
	
	/**
	 * @param rhs the multiplier
	 * @return	  the product of two fraction
	 * @throws InvalidDenominatorException 
	 */
	public Fraction mul(Fraction rhs) throws InvalidDenominatorException {
		int newNum = this.numerator * rhs.numerator;
		int newDen = this.denominator * rhs.denominator;
		return new Fraction(newNum, newDen).trim();
	}
	
	/**
	 * @param rhs the divisor
	 * @return	  the quotient of two fraction
	 * @throws InvalidDenominatorException 
	 */
	public Fraction div(Fraction rhs) throws InvalidDenominatorException {
		return this.mul(new Fraction(rhs.denominator, rhs.numerator));
	}
	
	/**
	 * @return simplified fraction
	 * @throws InvalidDenominatorException 
	 */
	private Fraction trim() throws InvalidDenominatorException {
		int gcd = this.GCD(this.numerator, this.denominator);
		return new Fraction(this.numerator/gcd, this.denominator/gcd);
	}
	
	/**
	 * Use Euclidean algorithm to calculate the greatest common divisor.
	 * @param a an integer number
	 * @param b an integer number
	 * @return 	the greatest common divisor
	 */
	private int GCD(int a, int b) {
		if(b == 0) return Math.abs(a);
		return GCD(b, a % b);
	}

	public static void main(String[] args) {
		/**
		 * Test cases for debug only.
		 */
		try {
			Fraction testConstructor = new Fraction(2, 3);
			System.out.println("Creating fraction 2/3: " + testConstructor);
			Fraction testTrim = new Fraction(-9, -6);
			System.out.println("Simplfying fraction 9/6: " + testTrim.trim());
		} catch (InvalidDenominatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "(numerator=" + this.numerator + ", denominator=" + this.denominator + ")";
	}
	
	
}
class InvalidDenominatorException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4057277425549869796L;

	InvalidDenominatorException() {
		super("The denominator must not be zero!");
	}
}