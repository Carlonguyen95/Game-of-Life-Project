package golClasses;

import javax.swing.JOptionPane;

/** This class contains methods that display error messages to user if something 
 * has gone wrong (after an exception has been thrown in another class).
 * 
 
 * @author Carlo
 * @author Idris
 * @author Haweyo
 * 
 * */
public class Error extends Exception {
	
	/**
	 * shows error message of the type 'numberFormatException after its been caught.
	 * specifically made for file (patternformat exception)
	 */
	public static void formatError() {
		JOptionPane.showMessageDialog(null, "Error_1: Wrong format in file!", "Error",
                JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * shows error message of the type 'NumberFormatException' after its been caught
	 * specifically made for URL (patternformat exception)
	  */
	public static void urlError() {
		JOptionPane.showMessageDialog(null, "Error_2: Wrong format in file!", "Error",
                JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * shows error message of the type 'MalformedURLException' after its been caught
	 * specifically made for URL (patternformat exception)
	  */
	public static void malformedURLError() {
		JOptionPane.showMessageDialog(null, "Error_3: invalid URL!", "Error",
                JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * shows error message of the type IOException after it's been caught.
	  */
	public static void generalError() { // add specific error message??
		JOptionPane.showMessageDialog(null, "Error_4: something went wrong. Restart the program.", "Error",
                JOptionPane.ERROR_MESSAGE);
	}
	
	public static void interruptedError() {
		generalError();
	}
	

}