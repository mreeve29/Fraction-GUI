//Mike Reeve
//B Block

import BreezySwing.*;
import javax.swing.*;


public class FractionGUI extends GBFrame {

	// Add User Inputs / Buttons
	JTextField fraction1Field = addTextField("",                       2, 0, 1, 1);
	JTextField fraction2Field = addTextField("",                       2, 3, 1, 1);

	JLabel multipltySymbol = addLabel("                    X",         2, 2, 1, 1);

	JButton multiplyButton = addButton("      Multiply      ",         6, 3, 1, 1);
	JButton clearButton =    addButton("       Clear        ",         7, 3, 1, 1);

	JLabel outputModeLabel = addLabel("Output Mode:",                  5, 1, 1, 1);
	
	// Group for radio buttons
	ButtonGroup bgTypeOfFraction = new ButtonGroup();	
	
	JRadioButton mixedButton = addRadioButton("Mixed Number",          6, 1, 1, 1);
	JRadioButton improperButton = addRadioButton("Improper Fraction",  7, 1, 1, 1);

	// Labels
	JLabel formatLabel = addLabel("     Format: x/y or x",             1, 2, 1, 1);
	JLabel resultLabel = addLabel("Result: ",                          5, 2, 1, 1);
	JLabel productLabel = addLabel("",                                 5, 3, 1, 1);

	// button event handler
	public void buttonClicked(JButton button) {
		if (button == clearButton) {
			clearFields();
		}
		if (button == multiplyButton) {
			// Get input from textfields as strings
			String fraction1Raw = fraction1Field.getText();
			String fraction2Raw = fraction2Field.getText();

			// If user enters a whole number --> add '/1' internally
			if (occurencesInString(fraction1Raw, '/') == 0) {
				fraction1Raw = fraction1Raw + "/1";
			}
			if (occurencesInString(fraction2Raw, '/') == 0) {
				fraction2Raw = fraction2Raw + "/1";
			}

			// error check / display message
			String errorCheck = errorcheck(fraction1Raw, fraction2Raw);
			if (!errorCheck.equals("NOERROR")) {
				messageBox("Invalid data, format: 'x/y'\n" + errorCheck);
				return;
			}

			// once fields have been error checked:

			// split fraction at the '/' character
			String[] fraction1 = fraction1Raw.split("/");
			String[] fraction2 = fraction2Raw.split("/");

			// parse ints
			int fraction1Numerator = Integer.parseInt(fraction1[0]);
			int fraction1Denominator = Integer.parseInt(fraction1[1]);
			int fraction2Numerator = Integer.parseInt(fraction2[0]);
			int fraction2Denominator = Integer.parseInt(fraction2[1]);

			// check negatives and correct them
			int[] fraction1Corrected = flipFraction(fraction1Numerator, fraction1Denominator);
			int[] fraction2Corrected = flipFraction(fraction2Numerator, fraction2Denominator);

			// multiply fractions
			int numeratorProduct = fraction1Corrected[0] * fraction2Corrected[0];
			int denominatorProduct = fraction1Corrected[1] * fraction2Corrected[1];

			String finalFraction = reduceFraction(numeratorProduct, denominatorProduct);

			productLabel.setText("" + finalFraction);

		}
	}

	public String errorcheck(String fraction1Raw, String fraction2Raw) {
		String[] fraction1 = fraction1Raw.split("/");
		String[] fraction2 = fraction2Raw.split("/");

		int fraction1Numerator, fraction1Denominator, fraction2Numerator, fraction2Denominator;

		// base error check value, if this is returned back, no error will be shown
		String check = "NOERROR";

		// if user only enters '/'
		//this works when the user enters a whole number because '/1' is added to the end of the string internally
		if (fraction1.length <= 1 || fraction2.length <= 1) {
			check = "";
			return check;
		}

		// check if user entered multiple '/' characters
		if (occurencesInString(fraction1Raw, '/') > 1 || occurencesInString(fraction2Raw, '/') > 1) {
			check = "Multiple '/' characters";
			return check;
		}

		// check for blank fields
		if (fraction1[0].equals("") || fraction2[0].equals("")) {
			check = "Empty Numerator";
			return check;
		}

		// attempt to parse numbers / also serves as integer check
		try {
			fraction1Numerator = Integer.parseInt(fraction1[0]);
			fraction1Denominator = Integer.parseInt(fraction1[1]);
			fraction2Numerator = Integer.parseInt(fraction2[0]);
			fraction2Denominator = Integer.parseInt(fraction2[1]);
		} catch (Exception e) {
			check = "Please only use inetgers";
			return check;
		}

		// check for 0 in denom
		if (fraction1Denominator == 0 || fraction2Denominator == 0) {
			check = "0 in denominator";
			return check;
		}

		return check;
	}

	public int findGCF(int one, int two) {
		// sort two inputs
		one = Math.abs(one);
		two = Math.abs(two);
		if (one > two) {
			int temp = two;
			two = one;
			one = temp;
		}

		// loop to find gcf
		int gcf = one;
		for (int i = one; i >= 1; i--) {
			if (one % i == 0 && two % i == 0) {
				gcf = i;
				break;
			}
		}
		if (gcf == 0) gcf = 1;
		return gcf;
	}

	public String reduceFraction(int numerator, int denominator) {
		// reduce with gcf
		int gcf = findGCF(numerator, denominator);
		numerator /= gcf;
		denominator /= gcf;

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
		if (improperButton.isSelected()) {
			finalFraction = numerator + "/" + denominator;
			return finalFraction;
		} else if (mixedButton.isSelected()) {
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

	// this method corrects the signs on the fraction and returns the result in an array
	public int[] flipFraction(int numerator, int denominator) {
		int[] result = { numerator, denominator };

		result[0] = numerator;
		result[1] = denominator;

		if (denominator < 0) {
			result[0] = -numerator;
			result[1] = -denominator;
		} else if (numerator < 0 && denominator < 0) {
			result[0] = Math.abs(numerator);
			result[1] = Math.abs(denominator);
		}

		return result;
	}

	// this method finds the amount of times a character occurs in a string, used for error checking multiple '/' symbols
	public byte occurencesInString(String str, char c) {
		byte occ = 0;
		int firstIndex = str.indexOf(c);
		if (firstIndex == -1)
			return occ;
		for (int i = firstIndex; i < str.length(); i++) {
			if (str.charAt(i) == c)
				occ++;
		}
		return occ;
	}

	// constructor that modifies the buttongroup and sets the default radio button selected
	public FractionGUI() {
		bgTypeOfFraction.add(improperButton);
		bgTypeOfFraction.add(mixedButton);
		improperButton.setSelected(true);
	}

	// reset all fields
	public void clearFields() {
		fraction1Field.setText("");
		fraction2Field.setText("");
		productLabel.setText("");
		improperButton.setSelected(true);
	}

	public static void main(String[] args) {
		FractionGUI frm = new FractionGUI();
		frm.setTitle("Fraction Multiplier");
		frm.setSize(470, 300);
		frm.setVisible(true);
		frm.setLocation(400, 400);
	}

}
