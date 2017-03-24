package golModel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class Board {

	private int cellSize = 10;
	
	private ColorPicker colorChange;
	private GraphicsContext g;
	private byte[][] board = new byte[100][100];
	
	public Board(GraphicsContext g) {
		this.g = g;
	}
	
    public void drawGrid() {

    	g.setFill(colorChange.getValue());
        g.setStroke(colorChange.getValue());
        g.setLineWidth(1);

        for (int x = 0; x < width; x += cellSize) {
            g.strokeLine(x, 1000, x, 0);
        }
        for (int y = 0; y < height; y += cellSize) {
            g.strokeLine(0, y, 1000, y);
        }
    }

    public void drawBoard() {

    	for (int i = 0; i < board.length; i++) {
    		
            for (int j = 0; j < board[i].length; j++) {
            	                
                if (board[i][j] == 1) {
                	
                    g.fillRect(i*cellSize, j*cellSize,cellSize,cellSize);
                    g.setFill(colorChange.getValue());
                }
            }
        }
    }

}
