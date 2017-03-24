package golModel;

import java.io.*;
import java.util.*;

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
		
		catch (Exception e) { // filenotfound exception (fix) 

			//handle exception
			System.out.println(e);
			}
		return board;
	}
	
	
	
	
	public byte[][] readFromURL() {
		return null;
	}
}
