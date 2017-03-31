package golClasses;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import javax.swing.JOptionPane;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

	public class FileReader {
		private int rows;
		private int columns;
	
	public FileReader(byte[][] b) {
		rows = b.length;
		columns = b[0].length;
	}
	
	public byte[][] readFromDisk(String filePath) throws Exception {
		
		Path diskFile = Paths.get(filePath);

		byte[][] board = new byte[rows][columns];
		
		try(
			InputStream fileReader = Files.newInputStream(diskFile);
			) {
			
			List <String> s = new ArrayList<>();
			
			int lines = 0;
			
			while (lines != -1) { // reads file content
				s = Files.readAllLines(diskFile);
				lines = fileReader.read();
			}

			for(int i = 0; i< board.length; i++) { //puts file content in board array
				for(int j = 0; j < board[i].length; j++ ) {
					for(int k =0; k < s.size(); k++) { 
						if(s.get(k).substring(0,1).equals("#")) { // removes first line
							//add patternformat exception
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
		
		catch (NumberFormatException e) { // filenotfound exception (fix) 

			//handle exception
			 PatternFormatException error = new PatternFormatException();
			 error.formatError();
			}
		return board;
	}

	public byte[][] readFromURL(String url) throws Exception, PatternFormatException {
		
		byte[][] board = new byte[rows][columns];
		URL destination = new URL(url);
		URLConnection conn = destination.openConnection();
		
		try(
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			) {
			
			String inputLine;
			
			List <String> s = new ArrayList<>();
			
			while ((inputLine = in.readLine()) != null) {
				s.add(inputLine);
				in.close();
			}

			for(int i = 0; i< board.length; i++) { //puts file content in board array
				for(int j = 0; j < board[i].length; j++ ) {
					for(int k =0; k < s.size(); k++) { 
						if(s.get(k).substring(0,1).equals("#")) { // removes first line
							//add patternformat exception
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
		
		catch (MalformedURLException e) { 
			
			 PatternFormatException error = new PatternFormatException();
			 error.urlError();
			}
		catch(IOException ioe) {
			
			PatternFormatException error = new PatternFormatException();
			error.ioeError();
		}
		return board;
	}
}
