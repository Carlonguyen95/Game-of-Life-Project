package golClasses;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.util.*;


/** This class reads files given by user either by URL or file from disk
 * and transforms the information in the files to byte arrays
 * that can be used for making patterns.
 * @author Idris
 * @author Carlo
 * @author Haweyo
 *
 * */
	public class Readmetodet{
		private int rows=0;
		private int columns=0;
		
	    private byte[][] board = new byte[300][300];
	    private byte[][] rle_Board;
	    private PatternFormatException error = new PatternFormatException();

		StringBuilder RLEPattern = new StringBuilder();
		String line = "";
	    String rule;
	    String rleCode = "";
	    String RlePattern = "";
	    String regEx = "x ?= ?(\\d*), y ?= ?(\\d*), rule ?= ?(B([0-9]+)\\/S(([0-8])+))|(S[0-9]+\\/B[0-9]+)";
	    String regexWeb= "x ?= ?(\\d*), y ?= ?(\\d*)";
	    
	/**
	 * this method reads file from disk. The file contains a specific pattern formatted in #Life 1.06
	 * Any other format will be caught as an pattern exception.
	 * The information is then stored in a temporary arrayList.
	 * Lastly the arrayList is used to fill a byte array representing the board.
	 * @param filePath is the path where the file is located in disk.
	 * @return a byte board with the pattern in the file.
	 * @throws exception of the type numberFormat or IOException.
	 */
	    
	    
	    //converts dynamic board to static two dimentional array
	    public byte[][] convertArray(ArrayList<List<Byte>> b) {
	    	byte[][] byteArray = b.stream().map(u->u.stream().mapToInt(i->i).toArray()).toArray(byte[][]::new);
	    	return byteArray;

	    	
	    }
	    
	    public byte[][] readBoardFromDisk(File path) throws IOException {

	        FileReader Fr = new FileReader(path);
	        BufferedReader Br = new BufferedReader(Fr);

	        try{
	        while ((line = Br.readLine()) != null) {
	            Pattern Pt = Pattern.compile(regEx);
	            Matcher Mt = Pt.matcher(line);

	            while (Mt.find()) {
	            	rows = Integer.parseInt(Mt.group(1));
	                System.out.println("Rows : " + rows);
	            	columns = Integer.parseInt(Mt.group(2));
	                System.out.println("Columns : " + columns);
	                rule = Mt.group(3);
	                System.out.println("Rule : " + rule);
	            }

	            if ((line.matches("[b, o, $, !, 0-9]*"))) {
	            	RlePattern = RlePattern.concat(line);
	            }

	        }
	        System.out.println(RlePattern);

	        }
   
		catch (NumberFormatException e) {  // wrong format in file
			 error.formatError();
			}

	        SimpleRLe(RlePattern);
			return board;
	    
	    }


	    public String SimpleRLe(String rlePattern) {

	        StringBuilder simpleRle = new StringBuilder();
	        Pattern pattern = Pattern.compile("\\d+|[ob]|\\$");
	        Matcher matcher = pattern.matcher(rlePattern);

	        while (matcher.find()) {
	            int num = 1;
	            if (matcher.group().matches("\\d+")) {
	                num = Integer.parseInt(matcher.group());
	                matcher.find();
	            }
	            for (int i = 0; i < num; i++) {
	                simpleRle.append(matcher.group());
	            }
	        }

	       System.out.println(simpleRle.toString());
	       rle_to_Array(simpleRle.toString());
	       return simpleRle.toString();
	    }

	    public byte[][] rle_to_Array(String rle) {

	        int x = 0;
	        int y = 0;

	        rle_Board = new byte[rows][columns];
	        
	        for (int i = 0; i < rle.length(); i++) {
	            if (rle.charAt(i) == '$') {
	                x = 0;
	                y++;
	            }
	            if (rle.charAt(i) == 'o') {	
	            	rle_Board[x][y] = 1;
	            	x++;
	            }
	            if (rle.charAt(i) == 'b') { 
	            	rle_Board[x][y] = 0;
	            	x++;
	            }
	        }
	        System.out.println(Arrays.deepToString(rle_Board));
	        return board = rle_Board;
	    }

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
	
