package golModel;

import java.io.*;
import java.util.*;
public class FileReader {
	
	private int rows;
	private int columns;
	
	public FileReader(byte[][] b) {
		rows = b.length;
		columns = b[0].length;
		
	}
	
	public byte[][] readFromDisk(String filename) throws IOException {

		byte[][] board = new byte[rows][columns]; 
		try {
			
			
			
		}
		
		catch (FileNotFoundException f) { 
			
			//handle exception
			return board;
			}
		return board;
	}
	
}
