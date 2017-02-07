package MVC;
import java.io.*;  // for filReader()
import java.util.*; // for fileReader()
public class Board {
	
	private byte[][] board;
	private int height;
	private int width;
	
	public Board(byte[][] b, int h, int w) {
		board = b;
		height = h;
		width = w;
		
	}
	
	public void setBoard(byte[][] b) {
		
		board = b;
	}
	
	public byte[][] getBoard() {
		return board;
	}
	
	public void fileReader(String filename) throws Exception {
		//leser inn fil
	}
	
	public void nextGeneration() {
		//
	}
}
