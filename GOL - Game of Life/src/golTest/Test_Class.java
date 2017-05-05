package golTest;
 
/**
 * This class is used to testing the NextGeneration method, by using JUNIT testing,
 * it checks if if it's working in the right way by setting up 'expected results'.
 * 
 * @author Carlo Nguyen
 * @author Haweya Jama
 * @author Idris Milamean
 */

public class Test_Class {  
	
	/**
	 *  this array is we will testing by test method.
	 *  we  butting this in testing method  and  we expect will be  the same
	 */
  private byte[][] board = {
 
        { 0, 0, 0, 0 },
 
        { 0, 1, 1, 0 }, 
 
        { 0, 1, 1, 0 },
 
        { 0, 0, 0, 0 }
 
        };
   
  /**
    * This  method return condition for each cell, where the board is formatted as a 1D table
	* we used StringBuffer cause it can be modifiedAt any point in time it contains some particular sequence of characters,
 	* but the length and content of the sequence can be changed through certain method calls.
 	* we used (for statement) to go through each sell, this method updates the value of the object that invoked the method
 	*/
  @Override  
  public String toString() {
	  StringBuffer DOfBoard = new StringBuffer("");
              for (int i = 0; i < board.length; i++) {
                  for (int j = 0; j < board[i].length; j++) {
                      if (board[i][j] == 1) {
                        DOfBoard.append("1");
                      } else {
                        DOfBoard.append("0");
                      }
                  }
              }
              return String.valueOf(DOfBoard);
  }
  /**
   * 
   */
  public void testNextGeneration() {
	  byte[][] updated = new byte[board.length][board[0].length];
      	for(int i = 0; i < board.length; i++) { 
      		for( int j =0; j < board[i].length; j++) {
                  updated[i][j] = board[i][j];
            }
        }
  }   
 
  /**
   * This method is to count each sell that is neighbor to sell we testing, it's testing if it's a live or not . 
   * testing  go through  8 neighbor and doesn't count itself
   * 1 means  live and 0 means dead 
   * @param X   is  point to x axis
   * @param y   is  point to y axis 
   * @return  values to  x,y axis
   */
  public int neighbours(int x, int y) {
	  int nr = 0;
        if(board[x][y] == 1) { // This line to that the cell doesn't count itself
          nr = -1;
        }
        
        for(int i = x-1; i <= x+1; i++){
              if(i < board.length && i > 0){ //cells on the edges (rows)
                  for(int j = y-1; j <= y + 1; j++){
                      if(j < board[i].length && j > 0){ // cells on the edges (columns) 
                          if (board[i][j] == 1) {
                              nr++;
                          }
                      }
                  }
              }
          }
        return nr;
      }
}
 