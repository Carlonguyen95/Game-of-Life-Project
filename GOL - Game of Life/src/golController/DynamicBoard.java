/**
 * This class represent the dynamic Game board.
 * 
 * @author Carlo Nguyen
 * @author Haweya Jama
 * @author Idris Milamean
 */

package golController;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

import golClasses.*;

public class DynamicBoard extends Board {  

	// Data field
	protected List<List<Byte>> board = new ArrayList<>();
	private int cellSize = 15;

	private Canvas graphics;
	private ColorPicker colorChangerBtn;
	private Slider sizeSliderBtn;
	private GraphicsContext gc;

	public DynamicBoard(GraphicsContext gc, Canvas graphics, ColorPicker colorChangerBtn, Slider sizeSliderBtn) {
		super(gc,graphics,colorChangerBtn,sizeSliderBtn);
		this.gc = gc;
		this.graphics = graphics;
		this.colorChangerBtn = colorChangerBtn;
		this.sizeSliderBtn = sizeSliderBtn;		

		int index = 0;
		int indeks = 0;

		while(index < 300) { // makes the board 300x300
			List <Byte> yBoard = new ArrayList<>();
			while (indeks < 300) {
				yBoard.add(indeks,(byte)0);		
				indeks++;
			}
			indeks=0;
			board.add(index,yBoard);			
			index++;		
		}
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
	 * This method makes a given cell alive (changing the value to 1)
	 * overridden to fit the dynamic board
	 * @param x and y variables that tells us where the cell is on the board
	 * */
	@Override
	public void setCellState(int x, int y) throws Exception {

		try {		
			board.get(x).set(y,(byte)1); 


		}

		catch(IndexOutOfBoundsException e) {

			e.printStackTrace();
			//	board.add(x).add(y);
		}	

	}
	
	/**
	 * this method returns a cells state (if its dead or alive)
	 * @param x and y variables that tells us where in the list the cell is and a
	 * 2D arraylist of the board
	 * */
	public int getCellState(List<List<Byte>> b,int x, int y) {
		try {
			return b.get(x).get(y);

		}

		catch(IndexOutOfBoundsException e) {
			//handle exception

			return 0;
		}	

	}

	/**
	 * this method makes the array rectangular (equally many rows and columns)
	 * after the user has clicked in a specific place on the grid
	 * */
	public void rectangular() { 

		int col = board.size(); //x
		int rows = board.get(0).size(); //y

		int index = 0;
		int indeks = 0;


		if(col > rows) {
			while(index <= 300) {
				List <Byte> yBoard = new ArrayList<>();
				while (indeks <= 300) {
					yBoard.add(indeks,(byte)0);		
					indeks++;
				}
				board.add(index,yBoard);			
				index++;		
			}
		}

		else if (col < rows) {
			while(col != rows) {
				board.get(index).set(col,(byte)0);
				col++;
				rows++;
				index++;

			}

		}

	}


	/**
	 * This method draws the grid of the board.
	 * for-loops for X and Y, which adds the size of the cell each iterate to the grid
	 * according to the height and width of the Canvas.
	 */
	@Override
	public void drawGrid() {

		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);

		for (int x = 0; x < graphics.getWidth(); x += cellSize) {
			gc.strokeLine(x, 1000, x, 0);
		}
		for (int y = 0; y < graphics.getWidth(); y += cellSize) {
			gc.strokeLine(0, y, 1000, y);
		}
	}

	/**
	 * This is a "helping-method" which contains two main draw-methods.
	 * drawGrid draws the grid of the board.
	 * drawBoard draws the board's array.
	 */
	public void draw() {  	
		drawGrid();
		drawBoard();

	}

	
	
	/**
	 * This method draws the board
	 * If the X and Y is equal to 1 there is a living cell.
	 * overrides the method in Board to fit the dynamic board
	 * 
	 * @param i is point to X axis
	 * @param j is point to Y axis
	 */
	@Override
	public void drawBoard() {
		for (int i = 0; i < board.size(); i++) {
			for (int j = 0; j < board.get(i).size(); j++) {                
				if (board.get(i).get(j) == 1) { //getCellState
					gc.fillRect(i*cellSize, j*cellSize,cellSize,cellSize);
					gc.setFill(colorChangerBtn.getValue());
				}
			}
		}

		//rectangular();	

	}
	
	 
	public int getCellSize(){
		return this.cellSize;
	}


	/**
	 * This method clears the current Board
	 * Clears the board first.
	 * Then a for-loop that search board for any living cells
	 * if true, then set that coordinate [x][y] equal to 0
	 * Draw grid again and stop the Animation.
	 * overrides the method in Board to fit the dynamic board
	 */
	@Override
	public void clearBoard() {

		gc.clearRect(0, 0, graphics.getWidth(), graphics.getHeight());

		for (int i = 0; i < board.size(); i++) {
			for (int j = 0; j < board.get(i).size(); j++) {                
				if (board.get(i).get(j) == 1) {
					board.get(i).set(j,(byte)0);
					gc.clearRect(i*cellSize, j*cellSize,cellSize,cellSize);
					// board.get(i).get(j) = 0;
				}
			}
		}

		drawGrid();
	}

	
	/**
	 * this method updates the board by applying the GoL rules to the board. 
	 * By using a temporary array that stores the previous state of the board, 
	 * the next generation of the board is created.
	 * overrides the method in Board to fit the dynamic board
	 */
	@Override
	public void nextGeneration() {

		List<List<Byte>> updated = new ArrayList<>();

		for(int i = 0; i < board.size(); i++) { //copies board content into updated
			ArrayList<Byte> inner = new ArrayList<Byte>();

			for(int j = 0; j < board.get(i).size(); j++) {
				inner.add(board.get(i).get(j));
			}

			updated.add(inner);

		}
		updateBoard(updated);
		gc.clearRect(0, 0, graphics.getWidth(), graphics.getHeight());

		for (int i = 0; i < updated.size(); i++) {	
			for (int j = 0; j < updated.get(i).size(); j++) {

				if(getCellState(updated,i,j) == 1) { //the cell is dead
					if(neighbours(i,j) == 3) {
						gc.fillRect(i*cellSize, j*cellSize,cellSize,cellSize);

					}           	
				}   	
				else { // the cell is alive
					if(neighbours(i,j)< 2 || neighbours(i,j) > 3) {
						gc.clearRect(i*cellSize, j*cellSize,cellSize,cellSize);
					}                          
				}
			}

		}     
		board = updated;
		draw();
		rectangular();

	}

	//threads
	public void nextGenerationConcurrentPrintPerformance() {

	}
	//threads 
	public synchronized void nextGenerationConcurrent() {

	}

	@Override
	public int neighbours(int x, int y) {

		int nr = 0;

		if(board.get(x).get(y) == 1) {
			nr  = -1;
		}

		for(int i = x-1; i <= x+1; i++){

			if(i < board.size() && i >= 0){ //cells on the edges (rows)

				for(int j = y-1; j <= y + 1; j++){

					if(j < board.get(i).size() && j >= 0){ // cells on the edges (columns)

						if (board.get(i).get(j) == 1) {
							nr++;
						}
					}
				}
			}
		}

		return nr; 
	}

	public void updateBoard( List<List<Byte>> updated) {

		for (int i = 0; i < updated.size(); i++) {
			for (int j = 0; j < updated.get(i).size(); j++) {

				if(updated.get(i).get(j) == 0) { //the cell is dead
					if(neighbours(i,j) == 3) {
						updated.get(i).set(j,(byte)1);
					}           	
				}

				else { // the cell is alive
					if(neighbours(i,j)< 2 || neighbours(i,j) > 3) {
						updated.get(i).set(j, (byte)0);
					}               

				}
			}

		} 

	}


}
