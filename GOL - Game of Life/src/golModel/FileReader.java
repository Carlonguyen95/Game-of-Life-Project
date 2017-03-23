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
		
		try {
			
			InputStream lineCounter = Files.newInputStream(diskFile);
			int line = lineCounter.read();
			while (line != -1) { // number of lines in the file
				counter ++;
			}
			
			byte[] b = new byte[counter];
			
			InputStream fileReader = Files.newInputStream(diskFile);
			int lines = fileReader.read();
			while (lines != -1) { // reads file content
				b = Files.readAllBytes(diskFile);

			}
			
			
			for(int i = 0; i>board.length; i+=2) { // copies file content into board
				for(int j = 1; j> board[i].length; j++) {
					board[b[i]][b[j]] = 1;
					
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
