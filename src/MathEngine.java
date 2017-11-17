import java.util.ArrayList;
import java.util.HashSet;

/**
 * 
 * @author Xiaotong Yang
 * @version 1.1
 *
 */
public class MathEngine {
	
	private static final boolean DETAIL = true;
	private static final boolean DEBUG_ZERO_TEST = false;
	private static final boolean DEBUG_SYN_DIV = false;
	private static final boolean DEBUG_QUADRATIC = false;
	
	private static ArrayList<Double> rootsOfPoly = new ArrayList<Double>();
	
	private static void ruleOfSigns(Polynomial p) {
		/**
		 * Use boolean to find the number of sign changes for f(x).
		 */
		int signChanges = 0;
		boolean PrevIsPos;
		if(p.get(0) > 0) {
			PrevIsPos = true;
		} else {
			PrevIsPos = false;
		}
		for(int i = 1; i < p.size(); i++) {
			//System.out.println("Prev:"+p.get(i-1)+", Curr:"+p.get(i));
			if(p.get(i) == 0) continue;
			if(p.get(i) < 0 && PrevIsPos) {
				PrevIsPos = false;
				signChanges++;
				//System.out.println("Sign changed.");
			} else if(p.get(i) > 0 && !PrevIsPos) {
				PrevIsPos = true;
				signChanges++;
				//System.out.println("Sign changed.");
			}
		}
		
		/**
		 * Then find the number of sign changes for f(-x). 
		 */
		int signChangesNeg = 0;
		int deg = p.highestDeg(), coe;
		if(deg % 2 != 0){
			/**
			 * Degree is odd.
			 */
			coe = p.get(0) * -1;
		} else {
			coe = p.get(0);
		}
		if(coe > 0) {
			PrevIsPos = true;
		} else {
			PrevIsPos = false;
		}
		for(int i = 1; i < p.size(); i++) {
			if(p.get(i) == 0) continue;
			deg = p.highestDeg()-i;
			if(deg % 2 != 0){
				coe = p.get(i) * -1;
			} else {
				coe = p.get(i);
			}
			if(coe < 0 && PrevIsPos) {
				PrevIsPos = false;
				signChangesNeg++;
			} else if(coe > 0 && !PrevIsPos) {
				PrevIsPos = true;
				signChangesNeg++;
			}
		}
		/**
		 * !Output of result.
		 */
		System.out.print("Possible Positive Roots: " + signChanges);
		int temp = signChanges;
		while(true) {
			temp = temp - 2;
			if(temp >= 0) {
				System.out.print(", " + temp);
			} else {
				break;
			}
		}
		System.out.println();
		System.out.println("Possible Negative Roots: " + signChangesNeg);
		System.out.println();
	}
	
	/**
	 * Find all factors of an integer number.
	 * @param number an integer number
	 * @return 		 an ArrayList of Integer that includes all the factors
	 */
	private static ArrayList<Integer> findFactors(int number) {
		int factor = 1;
		ArrayList<Integer> factors = new ArrayList<Integer>();
		
		if(number < 0) {
			number = number * -1;
		}
		
		while(factor <= number) {
			if(number % factor == 0) {
				factors.add(factor);
			}
			factor++;
		}
		return factors;
	} // function:findFactors(int)

	private static Polynomial syntheticDivision(Polynomial dividend, double divisor) throws Exception {
		if(DETAIL && DEBUG_SYN_DIV) 
			System.out.println("SynDivisor received: " + divisor);
		SynDivExpression exp = new SynDivExpression(dividend, divisor);
		Polynomial quotient = new Polynomial();
		/**
		 * Drop the first coefficient.
		 */
		quotient.add(dividend.get(0));
		for(int i = 1; i < dividend.size(); i++) {
			/**
			 * Multiply divisor and previous lower coefficient.
			 */
			double mid = quotient.get(i-1) * divisor;
			exp.addMidValue(mid);
			if(DETAIL && DEBUG_SYN_DIV)
				System.out.println("Mul Val: " + mid);
			/**
			 * Then add the product to upper coefficient.
			 */
			mid = mid + dividend.get(i);
			if(DETAIL && DEBUG_SYN_DIV)
				System.out.println("Add Val: " + mid);
			/**
			 * In the case current term is not the last term.
			 */
			if(i != dividend.size()-1) {
				/**
				 * Checking if having non-integer coefficient.
				 * For developing purpose only. 
				 */
				int intVal = (int)mid;
				if((double)intVal != mid) {
					throw new NotIntegerException(i, mid);
				}
				/**
				 * If valid, store as the coefficient for new polynomial.
				 */
				quotient.add(intVal);
				exp.addLowerCoe(intVal);
			} 
			/**
			 * In the case current term is the last term.
			 */
			else {
				/**
				 * Checking does the remainder equal to zero.
				 */
				if(mid != 0)
					throw new RemainderNotZeroException(mid);
				exp.addLowerCoe(0);
			}
		} // loop:for(1->dividend.size())
		exp.output();
		return quotient;
	}
	
	private static double[] quadraticFormula(Polynomial p) {
		/**
		 * Pre-check needed: p.size <= 3, p.get(0) != 0
		 */
		double a = p.get(0), b = p.get(1), c = p.get(2);
		double root1 = 0, root2 = 0;
		/**
		 * Discriminant.
		 */
		double d = Math.pow(b, 2) - 4*a*c;
		/**
		 * Three cases of roots.
		 */
		if(d > 0) {
			if(DETAIL && DEBUG_QUADRATIC)
				System.out.println("This polynomial equation has two different real roots.");
			d = Math.sqrt(d);
			root1 = ((0-b) + d) / (2*a);
			root2 = ((0-b) - d) / (2*a);
		} else if(d == 0) {
			if(DETAIL && DEBUG_QUADRATIC)
				System.out.println("This polynomial equation has one real root.");
			root1 = (0-b) / (2*a);
			root2 = root1;
		} else {
			System.out.println("This polynomial equation has no real root.");
		}
		double[] r = {root1, root2};
		return r;
	}

	private static Polynomial solve(Polynomial poly) {
		/**
		 * Stage #2:
		 * Find all possible real roots.
		 * Each result includes both positive and negative values.
		 * 
		 * q is factors of the constant term;
		 * p is factors of the leading term coefficient.
		 */
		ArrayList<Integer> q = findFactors(poly.getLast());
		if(DETAIL) System.out.println("The factors of the last term: " + q); //!Output
		ArrayList<Integer> p = findFactors(poly.get(0));
		if(DETAIL) System.out.println("The factors of the first term: " + p); //!Output
		
		/**
		 * In case of having non-integer real root like 0.6, use
		 * ArrayList of Double to contain the possible real roots.
		 * 
		 * Use HashSet to eliminate duplicates.
		 */
		ArrayList<Double> allPossibleRoot = new ArrayList<Double>();
		HashSet<Double> PRR = new HashSet<Double>();
		/**
		 * Calculate c = q/p.
		 */
		for(int i = 0; i < q.size(); i++) {
			int dividend = q.get(i);
			for(int j = 0; j < p.size(); j++) {
				int divisor = p.get(j);
				double quotient = (double)dividend/(double)divisor;
				PRR.add(quotient);
			}
		}
		allPossibleRoot.addAll(PRR);
		allPossibleRoot.sort(null);
		if(DETAIL) {
			System.out.println("All possible real number roots:");
			System.out.println(allPossibleRoot);
		} //!Output
		
		/**
		 * Stage #3:
		 * Apply rational zero test to find a real root from all 
		 * possible real roots.
		 */
		int indexOfRoot = -1;
		double rootFound = 0;
		boolean foundARoot = false;
		boolean isNeg = false;
		
		for(int i = 0; i < allPossibleRoot.size(); i++) {
			double possibleRoot = allPossibleRoot.get(i);
			double result = 0;
			/**
			 * For each possible root, calculate the following 
			 * for each term and sum all the results.
			 * f(possibleRoot) = (possibleRoot^degree)*coefficient
			 */
			for(int j = 0; j < poly.size(); j++) {
				double tmp = 0;
				try {
					tmp = Math.pow(possibleRoot, poly.getDeg(j)) 
								 * poly.get(j);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result = result + tmp;
			} // loop:for(0->poly.size())
			/**
			 * It is a real root of the polynomial if the final 
			 * result equals to zero.
			 */
			if(result == 0) {
				indexOfRoot = i;
				foundARoot = true;
				rootFound = possibleRoot;
				if(DETAIL && DEBUG_ZERO_TEST) 
					System.out.print("postive loop, index:" + indexOfRoot);
				break; // for
			}
		} // loop for(0->allPossibleRoot.size())
		
		/**
		 * Before testing negative values of possible roots, 
		 * check the if found a positive real root.
		 */
		if(!foundARoot) {
			isNeg = true;
			for(int i = 0; i < allPossibleRoot.size(); i++) {
				/**
				 * Convert to negative value.
				 */
				double possibleRoot = -1 * allPossibleRoot.get(i);
				double result = 0;
				/**
				 * For each possible root, calculate the following 
				 * for each term and sum all the results.
				 * f(possibleRoot) = (possibleRoot^degree)*coefficient
				 */
				for(int j = 0; j < poly.size(); j++) {
					double tmp = 0;
					try {
						tmp = Math.pow(possibleRoot, poly.getDeg(j)) 
									 * poly.get(j);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					result = result + tmp;
				} // loop:for(0->poly.size())
				/**
				 * It is a real root of the polynomial if the final 
				 * result equals to zero.
				 */
				if(result == 0) {
					indexOfRoot = i;
					foundARoot = true;
					rootFound = possibleRoot;
					if(DETAIL && DEBUG_ZERO_TEST) 
						System.out.print("negative loop, index:" + indexOfRoot);
					break; // for
				}
			} // loop for(0->allPossibleRoot.size())
		}
		
		/**
		 * !Output if no root found.
		 */
		if(!foundARoot) {
			System.out.println("No root found!");
			System.exit(0);
		}
		
		if(DETAIL) System.out.println("Found a root: " + rootFound); //!Output
		/**
		 * Store root of the polynomial.
		 */
		rootsOfPoly.add(rootFound);
		/**
		 * Applying synthetic division.
		 */
		Polynomial newPoly = new Polynomial();
		try {
			//newPoly = syntheticDivision(poly, synDivDivisor);
			newPoly = syntheticDivision(poly, rootFound);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(DETAIL) System.out.println("New polynomial with reduced degree: " + newPoly); //!Output
		
		return newPoly;
	} // function:solve(Poly)

	public static void factorization(Polynomial poly) {
		ruleOfSigns(poly);
		
		while(poly.highestDeg() != 2) {
			poly = solve(poly);
			if(DETAIL) System.out.println("");
		}
		double[] roots = quadraticFormula(poly);
		if(roots[0] == roots[1]) {
			rootsOfPoly.add(roots[0]);
		} else {
			rootsOfPoly.add(roots[0]);
			rootsOfPoly.add(roots[1]);
		}
		/**
		 * !Output of factorization.
		 */
		System.out.println("The polynomial factors into:");
		System.out.print("f(x)=");
		for(int i = 0; i < rootsOfPoly.size(); i++) {
			double c = rootsOfPoly.get(i) * -1;
			if(c < 0) {
				System.out.print("(x" + c + ")");
			} else {
				System.out.print("(x+" + c + ")");
			}
		}
		System.out.println("");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * Test case.
		 */
		Polynomial polyExample = new Polynomial();
		polyExample.example();
		System.out.println("Example polynomial is:");
		System.out.println(polyExample);
		System.out.println("");
		factorization(polyExample);
	}
}

class NotIntegerException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6360766953671791274L;

	NotIntegerException(int index, double coe) {
		super("Coefficient is not an integer! Index: " + index + ", coe: " + coe);
	}
}

class RemainderNotZeroException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2958073169030662792L;

	RemainderNotZeroException(double rem) {
		super("Remainder of sythetic division must be zero! Rem: " + rem);
	}
}
