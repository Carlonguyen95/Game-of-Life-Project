package golClasses;

import javax.swing.JOptionPane;

public class PatternFormatException extends Exception {
	
	public static void formatError() {
		JOptionPane.showMessageDialog(null, "Error_1: Wrong format in file!", "Error",
                JOptionPane.ERROR_MESSAGE);
	}
	
	public static void urlError() {
		JOptionPane.showMessageDialog(null, "Error_2: Wrong format in file!", "Error",
                JOptionPane.ERROR_MESSAGE);
	}
}
