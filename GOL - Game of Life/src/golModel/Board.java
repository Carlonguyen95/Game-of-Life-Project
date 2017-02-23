package golModel;

import golController.Controller;

public class Board {
	
	private byte[][] board = new byte[100][100];
	
    public void drawGrid() {
    	
        gc.setFill(colorChanger.getValue());
        gc.setStroke(colorChanger.getValue());
        gc.setLineWidth(1);

        for (int x = 0; x < graphics.getWidth(); x += cellSize) {
            gc.strokeLine(x, 1000, x, 0);
        }
        for (int y = 0; y < graphics.getHeight(); y += cellSize) {
            gc.strokeLine(0, y, 1000, y);
        }
    }

    public void drawBoard() {

    	for (int i = 0; i < board.length; i++) {
    		
            for (int j = 0; j < board[i].length; j++) {
            	                
                if (board[i][j] == 1) {
                	
                    gc.fillRect(i*cellSize, j*cellSize,cellSize,cellSize);
                    gc.setFill(colorChanger.getValue());
                }
            }
        }
    }

}
