package golClasses;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;

public class Graphic {
	
	// Data field
	private byte[][] board = new byte[100][100];
	private int cellSize = 10;
	
	private Canvas graphics;
	private ColorPicker colorChangerBtn;
	private Slider sizeSliderBtn;
	private GraphicsContext gc;
	
	/**
	* This is a "helping-method" which contains two main draw-methods.
	* drawGrid draws the grid of the board.
	* drawBoard draws the board's array.
	*/
	public void draw() {  	
		drawGrid();
	    drawBoard();
	    rleboard();
	}

	    /**
	     * This method is connected to a colorpicker.
	     * Allows the user to select color to the board and drawn cells.
	     * The color will be changed after each sequence of drawBoard() is executed.
	     * gc is a variable assigned to Canvas, and returns the GraphicsContext associated with this Canvas.
	     */
	    public void colorChange() {

	        gc.setFill(colorChangerBtn.getValue());
	        drawBoard();
	    }

	    /**
	     * This method is connected to a slider.
	     * Allows the user to select size to the board and drawn cells.
	     * The method sets the new size to cellSize, gc clears the board, then draw() is executed.
	     * And draws everything to the screen.
	     */
	    public void sizeChange() {
	    	
	        cellSize = (int) sizeSliderBtn.getValue();
	    	gc.clearRect(0, 0, graphics.getWidth(), graphics.getHeight());
	        draw();
	    }
	    
	    /**
	     * This method draws the grid of the board.
	     * for-loops for X and Y, which adds the size of the cell each iterate to the grid
	     * according to the height and width of the Canvas.
	     */
	    public void drawGrid() {

	    	gc.setFill(colorChangerBtn.getValue());
	        gc.setStroke(colorChangerBtn.getValue());
	        gc.setLineWidth(1);

	        for (int x = 0; x < graphics.getWidth(); x += cellSize) {
	            gc.strokeLine(x, 1000, x, 0);
	        }
	        for (int y = 0; y < graphics.getWidth(); y += cellSize) {
	            gc.strokeLine(0, y, 1000, y);
	        }
	    }

	    /**
	     * This method draws the board
	     * If the X and Y is equal to 1 there is a living cell.
	     * 
	     * @param i is point to X axis
	     * @param j is point to Y axis
	     */
	    public void drawBoard() {
	    	
	    	for (int i = 0; i < board.length; i++) {
	            for (int j = 0; j < board[i].length; j++) {                
	                if (board[i][j] == 1) {
	                    gc.fillRect(i*cellSize, j*cellSize,cellSize,cellSize);
	                    gc.setFill(colorChangerBtn.getValue());
	                }
	            }
	        }
	    }
	    
	public void rleboard() {
	    	
	    	for (int i = 0; i < board.length; i++) {
	            for (int j = 0; j < board[i].length; j++) {                
	                if (board[i][j] == 1) {
	                    gc.fillRect(i*cellSize, j*cellSize,cellSize,cellSize);
	                    gc.setFill(colorChangerBtn.getValue());
	                }
	            }
	        }
	    }
	    
	    /**
	     * This method clears the current Board
	     * Clears the board first.
	     * Then a for-loop that search board for any living cells
	     * if true, then set that coordinate [x][y] equal to 0
	     * Draw grid again and stop the Animation.
	     */
	public void clearBoard() {
	    	
		gc.clearRect(0, 0, graphics.getWidth(), graphics.getHeight());
	    	
	    	for (int i = 0; i < board.length; i++) {
	            for (int j = 0; j < board[i].length; j++) {                
	                if (board[i][j] == 1) {
	                    gc.clearRect(i*cellSize, j*cellSize,cellSize,cellSize);
	                    board[i][j] = 0;
	                }
	            }
	        }
	    	
	    	drawGrid();
	    }
}
