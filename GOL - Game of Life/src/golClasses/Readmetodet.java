package golClasses;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import golController.Control;
import java.awt.Point;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** This class reads files given by user either by URL or file from disk
 * and transforms the information in the files to byte arrays
 * that can be used for making patterns.
 * @author Idris
 * @author Carlo
 * @author Haweyo
 *
 * */
	public class Readmetodet extends Control{
		private int Rows=0;
		private int columns=0;
		
	    private byte[][] board = new byte[100][100];

		StringBuilder RLEPattern = new StringBuilder();
		String line = "";
	    String Rule;
	    String rleCode = "";
	    String rlePattern = "";
	    String regEx = "x ?= ?(\\d*), y ?= ?(\\d*), rule ?= ?(B([0-9]+)\\/S(([0-8])+))|(S[0-9]+\\/B[0-9]+)";
	    String regexWeb= "x ?= ?(\\d*), y ?= ?(\\d*)";
	    


	/**
	 * This constructor is invoked when an object is created by this class
	 *
	 * @param b is an byte array given when object is made
	 */
	
	

	/**
	 * this method reads file from disk. The file contains a specific pattern formatted in #Life 1.06
	 * Any other format will be caught as an pattern exception.
	 * The information is then stored in a temporary arrayList.
	 * Lastly the arrayList is used to fill a byte array representing the board.
	 * @param filePath is the path where the file is located in disk.
	 * @return a byte board with the pattern in the file.
	 * @throws exception of the type numberFormat or IOException.
	 */
	public void readFromDisk(File filePath) throws Exception {

		FileReader fileR = new FileReader(filePath);
        BufferedReader BuReader = new BufferedReader(fileR);
        
        
        
        while ((line = BuReader.readLine()) != null) {
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(line);
            
          
            try{
        
            while (matcher.find()) {
                Rows = Integer.parseInt(matcher.group(1));
                System.out.println("Row = " + Rows);
                columns = Integer.parseInt(matcher.group(2));
                System.out.println("Colum = " + columns);
                Rule = matcher.group(3);
                System.out.println("Rule : " + Rule);
                convertRleToSimpleRLe(rlePattern);

            }     } 

            
        

            	
            
		catch (NumberFormatException e) {  // wrong format in file
			 PatternFormatException error = new PatternFormatException();
			 error.formatError();

			}
        
    
		System.out.println(rlePattern);
         

         }

	
        

        }

	


    public String convertRleToSimpleRLe(String rlePattern) {

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
                
//                if (matcher.group(2).matches("b")) {
//                    if (matcher.group(1)==null) {
//                        Rows++;
//                    } else {
//                        Rows = Integer.parseInt(matcher.group(1));
//                    }
//                } else if (matcher.group(2).matches("o")) {
//                    if (matcher.group(1)==null) {
//                        board[columns][Rows] = 1;
//                        columns++;
//                    } else {
//                        for (int j = columns; columns < (i+Integer.parseInt(matcher.group(1))); columns++) {
//                            board[Rows][columns] = 1;
//                        }
//                    }
//
//                } else if (matcher.group(2).matches("\\$")) {
//                    if (matcher.group(1)==null) {
//                    	columns++;
//                        Rows = 0;
//                    } else {
//                    	columns=Integer.parseInt(matcher.group(1));
//                    	columns = 0;
//                    }
//                }


            }


        }

        System.out.println(simpleRle.toString());

       rleToArray(simpleRle.toString());
        return simpleRle.toString();


    }


    public void rleToArray(String rle) {

        int x = 0;
        int y = 0;

        board = new byte[Rows][columns];

        for (int i = 0; i < rle.length(); i++) {
             if (rle.charAt(i) == '$') {
                x = 0;
                y++;
            }
            if (rle.charAt(i) == 'o') {

                board[x][y] = 1;
                x++;
            }
            if (rle.charAt(i) == 'b') {

                board[x][y] = 0;
                x++;
                
                drawBoard();
            }

        }

        
        
        System.out.println(Arrays.deepToString(board));
        
    	


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

		byte[][] board = new byte[Rows][columns];
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
	
