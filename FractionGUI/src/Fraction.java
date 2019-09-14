

enum FractionType{
	IMPROPER, MIXED
}


public class Fraction {

	private int numerator;
	private int denominator;
	
	
	private int findGCF(int numer, int denom) {
		// sort two inputs
		
		numer = Math.abs(numer);
		denom = Math.abs(denom);
		
		int low  = Math.min(numer, denom);
		int max  = Math.max(numer, denom);

		// loop to find gcf
		int gcf = low;
		for (int i = low; i >= 1; i--) {
			if (numer % i == 0 && denom % i == 0) {
				gcf = i;
				break;
			}
		}
		if (gcf == 0) gcf = 1;
		return gcf;
	}
	
	public void reduce() {
		int gcf = findGCF(numerator, denominator);
		numerator /= gcf;
		denominator /= gcf;
		
		System.out.println(numerator + "/" + denominator);
		
	}
	
	public String toString(FractionType type) {
		//flipFraction();
		
		//type:
		//1 --> improper
		//2 --> mixed
		
		String finalFraction = "";

		// basic formatting rules that apply for improper and mixed outputs
		if (denominator == 1) {
			finalFraction = "" + numerator;
			return finalFraction;
		} else if (numerator == 0) {
			finalFraction = "0";
			return finalFraction;
		}
		
		// output correct format depending on if user want mixed or improper
		if (type == FractionType.IMPROPER) {
			finalFraction = numerator + "/" + denominator;
			return finalFraction;
		} else if (type == FractionType.MIXED) {
			if (numerator % denominator == 0) {
				finalFraction = "" + numerator / denominator;
			} else {
				int wholeNum = numerator / denominator;
				int remainder = numerator % denominator;
				if (wholeNum == 0) {
					finalFraction = "" + remainder + "/" + denominator;
				} else {
					// remove negative character from output on remainder if whole number is negative
					if (wholeNum < 0) {
						finalFraction = "" + wholeNum + " " + Math.abs(remainder) + "/" + denominator;
					} else {
						finalFraction = "" + wholeNum + " " + remainder + "/" + denominator;
					}
				}
			}
		}
		return finalFraction;
	}
	
	private void flipFraction() {
		System.out.println("input: " + numerator + "/" + denominator);
		if(denominator < 0) {
			numerator = -numerator;
			denominator = -denominator;
		}else if(numerator < 0 && denominator < 0) {
			numerator = Math.abs(numerator);
			denominator = Math.abs(denominator);
		}
		System.out.println("output: " + numerator + "/" + denominator);
		System.out.println();
	}
	
	
	public void setNumerator(int numer) {
		numerator = numer;
		//flipFraction();
	}
	
	public void setDenominator(int denom) {
		denominator = denom;
		//flipFraction();
	}
	
	public int getNumerator() {
		return numerator;
	}
	
	public int getDenominator() {
		return denominator;
	}
	
	
	
	public Fraction(){
		numerator = 0;
		denominator = 0;
	}
	
	public Fraction(int numer, int denom) {
		numerator = numer;
		denominator = denom;
		flipFraction();
	}
	
	
	
	
	
}
