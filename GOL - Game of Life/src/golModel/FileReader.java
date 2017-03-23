package golModel;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;


import java.nio.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		int counter = 0;
		
		try(
				InputStream lineCounter = Files.newInputStream(diskFile);
				InputStream fileReader = Files.newInputStream(diskFile);
			) {
			

			int line =-1; 

			while (line  != -1) { // number of lines in the file
				counter ++;
				line = lineCounter.read();
			}

			List <String> s = new ArrayList<>();
			
			int lines = 0;
			
			while (lines != -1) { // reads file content
				s = Files.readAllLines(diskFile);
				lines = fileReader.read();
			}

			
			for(int i =0; i< s.size(); i++) { // prints content of board (testing)
			System.out.println(s.get(i));
			} 
			
			for(int i = 0; i< board.length; i++) {
				for(int j = 0; i < board[i].length; j++ ) {
					for(int k =0; k < s.size(); k++) {
						
						int x = Integer.parseInt(s.get(k).substring(0,2));
						int y = Integer.parseInt(s.get(k).substring(1,3));
						
						board[x][y] = 1;
						System.out.println(board[i][j]);
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
	
	
}
