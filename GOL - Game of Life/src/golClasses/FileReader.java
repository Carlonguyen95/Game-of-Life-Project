package golClasses;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Point;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** This class reads files given by user either by URL or file from disk
 * and transforms the information in the files to byte arrays
 * that can be used for making patterns.
 * @author Carlo
 * @author Idris
 * @author Haweyo
 * 
 * */
	public class FileReader {
		private int rows;
		private int columns;
	
	/**
	 * This constructor is invoked when an object is created by this class
	 * 
	 * @param b is an byte array given when object is made
	 */
	public FileReader(byte[][] b) {
		rows = b.length;
		columns = b[50].length;
	}
	
	/**
	 * this method reads file from disk. The file contains a specific pattern formatted in #Life 1.06
	 * Any other format will be caught as an pattern exception. 
	 * The information is then stored in a temporary arrayList.
	 * Lastly the arrayList is used to fill a byte array representing the board.
	 * @param filePath is the path where the file is located in disk.
	 * @return a byte board with the pattern in the file.
	 * @throws exception of the type numberFormat or IOException.
	 */	
	public byte[][] readFromDisk(String filePath) throws Exception {
		
		Path diskFile = Paths.get(filePath);

		byte[][] board = new byte[rows][columns];
		
		try(
		
				BufferedReader in = Files.newBufferedReader(diskFile);
			) {
			

			String inputLine;
			String rle = null;
			
			while ((inputLine = in.readLine()) != null) { // puts file content in string
				 if ((inputLine.matches("[b, o, $, !, 0-9]*"))) { // kopier fra den andre koden
					 rle = rle.concat(inputLine + "\n");
				 }
		}	
		
	
			// Pattern p = Pattern.compile() // legg til regex utrykket som tolker rle
			
			Pattern p = Pattern.compile(("\\d+|[ob]|\\$")); // kopiert fra den andre koden, må endres!
			Matcher m = p.matcher(rle);
			while(m.find()){
				//System.out.println(m.group());
			}
			
			
			/*
			Pattern p = Pattern.compile("//d+ //d+");
			Matcher m = p.matcher("6 44 3 1 6 8");
			while(m.find()) System.out.println(m.group());
			System.out.println("TEST");
		*/
			
				
			return board;
		}
	
	
		catch (NumberFormatException e) {  // wrong format in file
			 PatternFormatException error = new PatternFormatException();
			 error.formatError();

			}
		

		catch (IOException ioe) { // general IO exception
			PatternFormatException error = new PatternFormatException();
			 error.generalError();

		}		
		
	return board;
}
	
	// http://student.cs.hioa.no/~s315613/glider.html
	
	
	/**
	 * this method reads pattern from URL.
	 * The page contains a specific pattern formatted in #Life 1.06
	 * Any other format will be caught as an pattern exception. 
	 * The information is then stored in a temporary arrayList.
	 * Lastly the arrayList is used to fill a byte array representing the board.
	 * @param url contains the URL given by user.
	 * @return a byte board with the pattern from the URL.
	 * @throws exception of the type numberFormat or IOException.
	 */
	
	public byte[][] readFromURL(String url) throws Exception, PatternFormatException, 
	MalformedURLException {
		
		byte[][] board = new byte[rows][columns];
		URL destination = new URL(url);
		URLConnection conn = destination.openConnection();

		
		try(
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			) {

			List <String> s = new ArrayList<String>();
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) { // puts webpage content in arraylist
					s.add(inputLine);
			}	
			
			
			for(int i = 0; i< board.length; i++) { //puts webpage content in board array
				for(int j = 0; j < board[i].length; j++ ) {
					for(int k =0; k < s.size(); k++) {
							
						if(s.get(k).substring(0,1).equals("#")) { // removes first line
							s.remove(s.get(k));
							}
													
						else if(s.get(k).substring(0,1).equals("<")) { // removes html code
							s.remove(s.get(k));				
							}
						else {
								
							int x = Integer.parseInt(s.get(k).substring(0,1));
							int y = Integer.parseInt(s.get(k).substring(2,3));					
							board[x][y] = 1;
								
							}
						}				
					}
				}				
			
		return board;
	}
		
		catch (NumberFormatException e) {  // wrong format in file
			 PatternFormatException error = new PatternFormatException();
			 error.urlError();
		}
		
		catch (MalformedURLException m) { // invalid URL
			PatternFormatException error = new PatternFormatException();
			 error.malformedURLError();
		}
		
		
		catch (IOException ioe) { //general IO exception
			PatternFormatException error = new PatternFormatException();
			 error.generalError();
		}	
	
		return board;
	}
}