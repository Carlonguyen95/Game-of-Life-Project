package golController;

public class BoardTester {
	
	public static void main(String[] args) {
		
		byte[][] board = new byte[3][3];
		board[0][0] = 1;
		board[0][1] = 1;
		board[0][2] = 0;
		
		board[1][0] = 0;
		board[1][1] = 1;
		board[1][2] = 0;
		
		board[2][0] = 0;
		board[2][1] = 0;
		board[2][2] = 1;
		
		neighbours(1,0,board);
		

	}
    public static int neighbours(int x, int y,byte[][] board) {

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
                            System.out.println(nr);
                        }
                    }
                }
            }
        }

    	return nr;
    	
    	
    }


}
