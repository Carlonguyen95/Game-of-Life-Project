/** This class reads files given by user either by URL or file from disk
 * and transforms the information in the files to byte arrays
 * that can be used for making patterns.
 * @author Idris
 * @author Carlo
 * @author Haweyo
 *
 * */

package golClasses;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class FileConverter{
	private int rows=0;
	private int columns=0;

	private byte[][] board = new byte[300][300];
	private byte[][] rle_Board;
	private Error error = new Error();

	StringBuilder RLEPattern = new StringBuilder();
	String line = "";
	String rule;
	String rleCode = "";
	String RlePattern = "";
	String regEx = "x ?= ?(\\d*), y ?= ?(\\d*), rule ?= ?(B([0-9]+)\\/S(([0-8])+))|(S[0-9]+\\/B[0-9]+)";
	String regexWeb= "x ?= ?(\\d*), y ?= ?(\\d*)";


		/**
	 * this method reads file from disk. The file contains a specific pattern formatted in #RLE
	 * Any other format will be caught as an pattern exception.
	 * @param filePath is the path where the file is located in disk.
	 * @return a byte board with the pattern in the file.
	 * @throws exception of the type numberFormat or IOException.
	 */

	public byte[][] readBoardFromDisk(File path) throws IOException {
		FileReader Fr = new FileReader(path);
		BufferedReader Br = new BufferedReader(Fr);

			while ((line = Br.readLine()) != null) {
				Pattern Pt = Pattern.compile(regEx);
				Matcher Mt = Pt.matcher(line);

				while (Mt.find()) {
					rows = Integer.parseInt(Mt.group(1));
					columns = Integer.parseInt(Mt.group(2));
					rule = Mt.group(3);
				}
				if ((line.matches("[b, o, $, !, 0-9]*"))) {
					RlePattern = RlePattern.concat(line);
				}
			}
		rle(RlePattern);
		return board;
	}

	/**
	 * helping method that takes a given rlepattern and simplifies it in order to
	 * turn the information into an Array. 
	 * @param the rle pattern in the form of a string
	 * @return the pattern simplified 
	 * */
	public void rle(String rlePattern) {

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
		rleConverter(simpleRle.toString());
	}

	
	/**
	 * turns the given string into an array by sorting the information
	 * given in the string into byte
	 * @param the string containing the rle information
	 * @return the converted 2d byte array
	 * */
	public byte[][] rleConverter(String rle) {

		int x = 0;
		int y = 0;

		rle_Board = new byte[350][350]; 

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

		return board = rle_Board;
	}

	/**
	 * this method reads pattern from URL.
	 * The page contains a specific pattern formatted in #Life 1.06
	 * Any other format will be caught as an pattern exception.
	 * @param url contains the URL given by user.
	 * @return a byte board with the pattern from the URL.
	 * @throws exception of the type numberFormat or IOException.
	 */

	public byte[][] readFromURL(String url) throws Exception, Error,
	MalformedURLException {

		URL destination = new URL(url);
		URLConnection conn = destination.openConnection();

				BufferedReader in = new BufferedReader(
						new InputStreamReader(conn.getInputStream()));

			String inputLine = " ";

			while ((inputLine = in.readLine()) != null) { // puts webpage content in arraylist		

				Pattern P = Pattern.compile(regEx);
				Matcher M = P.matcher(inputLine);
				while (M.find()) {

					rows = Integer.parseInt(M.group(1));
					columns = Integer.parseInt(M.group(2));
					rule = M.group(2);		
				}
				if ((inputLine.matches("[b, o, $, !, 0-9]*"))) {
					RlePattern = RlePattern.concat(inputLine);
				}
			}
		rle(RlePattern);
		return board;
	}
}

