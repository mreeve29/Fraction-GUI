import BreezySwing.*;
import javax.swing.*;

public class FractionClient extends GBFrame {

	// Add User Inputs / Buttons
	JTextField fraction1Field = addTextField("", 2, 0, 1, 1);
	JTextField fraction2Field = addTextField("", 2, 3, 1, 1);

	JLabel multipltySymbol = addLabel("                    X", 2, 2, 1, 1);

	JButton multiplyButton = addButton("      Multiply      ", 6, 3, 1, 1);
	JButton clearButton = addButton("       Clear        ", 7, 3, 1, 1);

	JLabel outputModeLabel = addLabel("Output Mode:", 5, 1, 1, 1);

	// Group for radio buttons
	ButtonGroup bgTypeOfFraction = new ButtonGroup();

	JRadioButton mixedButton = addRadioButton("Mixed Number", 6, 1, 1, 1);
	JRadioButton improperButton = addRadioButton("Improper Fraction", 7, 1, 1, 1);

	// Labels
	JLabel formatLabel = addLabel("     Format: x/y or x", 1, 2, 1, 1);
	JLabel resultLabel = addLabel("Result: ", 5, 2, 1, 1);
	JLabel productLabel = addLabel("", 5, 3, 1, 1);

	Fraction f1, f2;

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

			Fraction f1 = new Fraction(fraction1Numerator, fraction1Denominator);
			Fraction f2 = new Fraction(fraction2Numerator, fraction2Denominator);

			Fraction product = multiplyFractions(f1, f2);
			
			product.reduce();

			productLabel.setText("" + product.toString(1));

		}
	}

	public String errorcheck(String fraction1Raw, String fraction2Raw) {
		String[] fraction1 = fraction1Raw.split("/");
		String[] fraction2 = fraction2Raw.split("/");

		int fraction1Numerator, fraction1Denominator, fraction2Numerator, fraction2Denominator;

		// base error check value, if this is returned back, no error will be shown
		String check = "NOERROR";

		// if user only enters '/'
		// this works when the user enters a whole number because '/1' is added to the
		// end of the string internally
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

	// this method finds the amount of times a character occurs in a string, used
	// for error checking multiple '/' symbols
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

	// reset all fields
	public void clearFields() {
		fraction1Field.setText("");
		fraction2Field.setText("");
		productLabel.setText("");
		improperButton.setSelected(true);
	}

	public Fraction multiplyFractions(Fraction f1, Fraction f2) {
		Fraction product = new Fraction();

		product.setNumerator(f1.getNumerator() * f2.getNumerator());
		product.setDenominator(f1.getDenominator() * f2.getDenominator());

		return product;
	}

	// constructor that modifies the buttongroup and sets the default radio button
	// selected
	public FractionClient() {
		bgTypeOfFraction.add(improperButton);
		bgTypeOfFraction.add(mixedButton);
		improperButton.setSelected(true);
	}

	public static void main(String[] args) {
		FractionClient frm = new FractionClient();
		frm.setTitle("Fraction Multiplier");
		frm.setSize(470, 300);
		frm.setVisible(true);
		frm.setLocation(400, 400);

	}

}
