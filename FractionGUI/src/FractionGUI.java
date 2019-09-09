import BreezySwing.*;
import javax.swing.*;

public class FractionGUI extends GBFrame {

	JTextField fraction1Field = addTextField("", 1, 0, 1, 1);
	JTextField fraction2Field = addTextField("", 1, 3, 1, 1);

	JLabel multipltySymbol = addLabel("                       x", 1, 2, 1, 1);

	JButton multiplyButton = addButton("Multiply", 5, 2, 1, 1);
	
	ButtonGroup bgTypeOfFraction = new ButtonGroup();

	JRadioButton mixedButton = addRadioButton("Mixed Number",4,2,1,1);
	JRadioButton improperButton = addRadioButton("Improper Fraction",3,2,1,1);
	
	
	JLabel resultLabel = addLabel("", 6, 2, 1, 1);

	public void buttonClicked(JButton button) {
		if (button == multiplyButton) {
			
			String fraction1Raw = fraction1Field.getText();
			String fraction2Raw = fraction2Field.getText();
			
			
			if(occurencesInString(fraction1Raw, '/') == 0) {
				fraction1Raw = fraction1Raw + "/1";
			}
			if(occurencesInString(fraction2Raw, '/') == 0) {
				fraction2Raw = fraction2Raw + "/1";
			}	
			
			
			String errorCheck = errorcheck(fraction1Raw, fraction2Raw);
			if (!errorCheck.equals("NOERROR")) {
				messageBox("Invalid data, format: 'x/y'\n" + errorCheck);
				return;
			}
			
			String[] fraction1 = fraction1Raw.split("/");
			String[] fraction2 = fraction2Raw.split("/");
			
			
			
			int fraction1Numerator = Integer.parseInt(fraction1[0]);
			int fraction1Denominator = Integer.parseInt(fraction1[1]);
			int fraction2Numerator = Integer.parseInt(fraction2[0]);
			int fraction2Denominator = Integer.parseInt(fraction2[1]);
			
			int[] fraction1Corrected = flipFraction(fraction1Numerator,fraction1Denominator);
			int[] fraction2Corrected = flipFraction(fraction2Numerator,fraction2Denominator);
			
			
			int numeratorProduct = fraction1Corrected[0] * fraction2Corrected[0];
			int denominatorProduct = fraction1Corrected[1] * fraction2Corrected[1];

			String finalFraction = reduceFraction(numeratorProduct, denominatorProduct);
			
			resultLabel.setText(finalFraction);

		}
	}

	public String errorcheck(String fraction1Raw, String fraction2Raw) {
		
		String[] fraction1 = fraction1Raw.split("/");
		String[] fraction2 = fraction2Raw.split("/");
		
		
		int fraction1Numerator, fraction1Denominator, fraction2Numerator, fraction2Denominator;
		
		
		String check = "NOERROR";
		
		if(occurencesInString(fraction1Raw, '/') > 1 || occurencesInString(fraction2Raw, '/') > 1) {
			check = "Multiple '/' characters";
			return check;
		}
		
		
		if (fraction1[0].equals("") || fraction2[0].equals("")) {
			check = "Empty Numerator";
			return check;
		}
		
		try {
			fraction1Numerator = Integer.parseInt(fraction1[0]);
			fraction1Denominator = Integer.parseInt(fraction1[1]);
			fraction2Numerator = Integer.parseInt(fraction2[0]);
			fraction2Denominator = Integer.parseInt(fraction2[1]);
		} catch (Exception e) {
			check = "Please only use inetgers";
			return check;
		}
		
		if ((int) fraction1Numerator != fraction1Numerator || (int) fraction1Denominator != fraction1Denominator
				|| (int) fraction2Numerator != fraction2Numerator
				|| (int) fraction2Denominator != fraction2Denominator) {
			check = "Please only use integers";
			return check;
		}
		
		if (fraction1Denominator == 0 || fraction2Denominator == 0) {
			check = "0 in denominator";
			return check;
		}

		return check;
	}

	public int findGCF(int one, int two) {
		one = Math.abs(one);
		two = Math.abs(two);
		if (one > two) {
			int temp = two;
			two = one;
			one = temp;
		}
		int gcf = one;
		for (int i = one; i > 1; i--) {
			if (one % i == 0 && two % i == 0) {
				gcf = i;
				break;
			}
		}
		
		return gcf;
	}

	public String reduceFraction(int numerator, int denominator) {
		int gcf = findGCF(numerator, denominator);
		numerator /= gcf;
		denominator /= gcf;

		String finalFraction = "if you're seeing this theres an error";
		
		if(numerator < 0 && denominator < 0) {
			numerator = Math.abs(numerator);
			denominator = Math.abs(denominator);
		}

		if (denominator == 1 && !improperButton.isSelected()) {
			finalFraction = "" + numerator;
		} else if (numerator == 0){
			finalFraction = "0";
		} else {
			finalFraction = numerator + "/" + denominator;
		}
		
		if(mixedButton.isSelected()) {
			if(numerator % denominator == 0){
				finalFraction = "" + numerator / denominator;
			}else {
				int wholeNum = numerator / denominator;
				int remainder = numerator % denominator;
				if(wholeNum == 0) {
					finalFraction = "" + remainder + "/" + denominator;
				}else {
					if(wholeNum < 0) {
						finalFraction = "" + wholeNum + " " + Math.abs(remainder) + "/" + denominator;
					}else {
						finalFraction = "" + wholeNum + " " + remainder + "/" + denominator;
					}
				}
			}
		}
		return finalFraction;

	}

	public int[] flipFraction(int numerator, int denominator) {
		int[] result = new int[2];
		
		result[0] = numerator;
		result[1] = denominator;
		
		if(denominator < 0) {
			result[0] = -numerator;
			result[1] = -denominator;
		}else if (numerator < 0 && denominator < 0) {
			result[0] = Math.abs(numerator);
			result[1] = Math.abs(denominator);
		}
		
		return result;
	}
	
	
	public byte occurencesInString(String str, char c) {
		byte occ = 0;
		int firstIndex = str.indexOf(c);
		if(firstIndex == -1)return occ;
		for(int i = firstIndex; i < str.length(); i++) {
			if(str.charAt(i) == c)occ++;
		}
		return occ;
	}
	
	public FractionGUI() {
		bgTypeOfFraction.add(improperButton);
		bgTypeOfFraction.add(mixedButton);
		improperButton.setSelected(true);
	}
	
	
	public static void main(String[] args) {
		FractionGUI frm = new FractionGUI();
		frm.setTitle("Fraction Multiplier");
		frm.setSize(400, 200);
		frm.setVisible(true);
		frm.setLocation(400, 400);
	}

}
