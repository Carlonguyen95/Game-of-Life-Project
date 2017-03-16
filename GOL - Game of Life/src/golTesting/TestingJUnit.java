package golTesting;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestingJUnit {
	
		byte[][] board = {
		{ 1, 1, 0, 0 },
		{ 0, 1, 0, 0 },
		{ 0, 0, 0, 0 },
		{ 0, 0, 0, 0 }
		};
		
	@Test
	public void testMethod() {

		TestingJUnit gol = new TestingJUnit();
		gol.setBoard(board);
		org.junit.Assert.assertEquals(gol.neighbours(0,0),0	);
		}
	
	public void setBoard(byte[][] board) {
		this.board = board;
	}
	
	public int neighbours(int x, int y) {
	    int nr = 0;
	    
	    if(board[x][y] == 1){
	    	nr--;
	    }
	    	
	    for(int i = x-1; i <= x+1; i++){
	    	
	    		if(i < board.length && i > 0){ //cells on the edges (rows)
	    			
	            	for(int j = y-1; j <= y+1; j++){
	            		
	                	if(j < board[i].length && j > 0){ // cells on the edges (columns)
	                		
	                    	if(board[i][j] == 1) {
	                            nr++;
	                        }
	                    }
	                }
	            }
	        }
	    	return nr;   	
	    }

}
