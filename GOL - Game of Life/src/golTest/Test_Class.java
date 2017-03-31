package golTest;
 
public class Test_Class {
 
  private byte[][] board = {
 
        { 0, 0, 0, 0 },
 
        { 0, 1, 1, 0 },
 
        { 0, 1, 1, 0 },
 
        { 0, 0, 0, 0 }
 
        };
 
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

  public void testNextGeneration() {
	  byte[][] updated = new byte[board.length][board[0].length];
      	for(int i = 0; i < board.length; i++) { // copies board
      		for( int j =0; j < board[i].length; j++) {
                  updated[i][j] = board[i][j];
            }
        }
  }   
 
  public int neighbours(int x, int y) {
	  int nr = 0;
        if(board[x][y] == 1) { //so that the cell doesn't count itself
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
 