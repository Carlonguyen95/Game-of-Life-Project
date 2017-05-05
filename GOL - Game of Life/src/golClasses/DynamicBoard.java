/**
 * This class represent the dynamic Game board.
 * 
 * @author Carlo Nguyen
 * @author Haweya Jama
 * @author Idris Milamean
 */

package golClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class DynamicBoard {  

	// Data field
	public List<List<Byte>> board = new ArrayList<>();
	protected List<List<Boolean>> boolBoard = new ArrayList<>();
	protected int cellSize = 15;
	private int boardSize = 150;

	private Canvas graphics;
	private ColorPicker colorChangerBtn;
	private Slider sizeSliderBtn;
	private GraphicsContext gc;

	public DynamicBoard(GraphicsContext gc, Canvas graphics, ColorPicker colorChangerBtn, Slider sizeSliderBtn) {
		//super(gc,graphics,colorChangerBtn,sizeSliderBtn);
		this.gc = gc;
		this.graphics = graphics;
		this.colorChangerBtn = colorChangerBtn;
		this.sizeSliderBtn = sizeSliderBtn;
	}
	
	public void init(){
		for (int x = 0; x < boardSize; x++){
			List <Byte> innerBoard = new ArrayList<>();
			List <Boolean> innerBoolBoard = new ArrayList<>();
			for (int y = 0; y < boardSize; y++){
				innerBoard.add((byte) 0);
				innerBoolBoard.add(false);
			}
			board.add(innerBoard);
			boolBoard.add(innerBoolBoard);
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
	 * this method makes the array rectangular 
	 * after the user has clicked in a specific place on the grid
	 * (everything behind the given cell)
	 * */
	public void rectangular(int x, int y) { 

		for(int i =x-1; i>= 0; i--) {
			for(int j =y-1; j>= 0; j--) {
				if(board.get(i).get(j) != 1) {
					board.get(i).set(j,(byte)0);
				}
			}
		}
	}


	/**
	 * This method draws the grid of the board.
	 * for-loops for X and Y, which adds the size of the cell each iterate to the grid
	 * according to the height and width of the Canvas.
	 */
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
	public void drawBoard() {
		for (int i = 0; i < board.size(); i++) {
			for (int j = 0; j < board.get(i).size(); j++) {                
				if (board.get(i).get(j) == 1) { //getCellState
					gc.fillRect(i*cellSize, j*cellSize,cellSize,cellSize);
					gc.setFill(colorChangerBtn.getValue());
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 */
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
	 * 
	 * @param gameBoard
	 */
	public void setBoard(byte[][] gameBoard) {
		List<List<Byte>> newBoard = new ArrayList<List<Byte>>();

		for(int i=0;i<gameBoard.length;i++){
			newBoard.add(new ArrayList<Byte>());
			for(int j=0;j<gameBoard[0].length;j++){
				newBoard.get(i).add(gameBoard[i][j]);
			}
		}

		board = newBoard;	
	}

	/**
	 * this method updates the board by applying the GoL rules to the board. 
	 * By using a temporary array that stores the previous state of the board, 
	 * the next generation of the board is created.
	 * overrides the method in Board to fit the dynamic board
	 */
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
		checkIncrease();
	}

	/**
	 * 
	 */
	public synchronized void nextGenerationConcurrent() {
		//split board, give each side a task and run nextGeneration();
		
		board = Collections.synchronizedList( new ArrayList<List<Byte>>());
		int n = board.size()/Runtime.getRuntime().availableProcessors();
		
		ExecutorService executor = Executors.newCachedThreadPool();
		Executors.newFixedThreadPool(n);

	}

	/**
	 * 
	 */
	public void nextGenerationConcurrentPrintPerformance() {
		long start = System.currentTimeMillis();
		//neighbours();
		long elapsed = System.currentTimeMillis() - start;
		System.out.println("Counting time (ms): " + elapsed);
	}


	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
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
	
	/**
	 * 
	 * @param updated
	 */
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
	
	/**
	 * 
	 */
	public void increaseBelow(){
		int increase = 2;
		boardSize += increase;
		
		
		for(int x = 0; x < increase; x++){
			List<Byte> innerArray = new ArrayList<>();
			for(int y = 0; y < boardSize-increase; y++){
				innerArray.add((byte) 0);
			}
			board.add(innerArray);
		}
		
		for(int x = 0; x < boardSize; x++){
			for(int y = 0; y < increase; y++){
				board.get(x).add((byte) 0);
			}
		}
	}
	
	/**
	 * 
	 */
	public void increaseUpper(){
		int increase = 2;
		boardSize += increase;
		for(int x = 0; x < increase; x++){
			List<Byte> innerArray = new ArrayList<>();
			for(int y = 0; y < boardSize-increase; y++){
				innerArray.add(0,(byte) 0);
			}
			board.add(0, innerArray);
		}
		
		for(int x = 0; x < boardSize; x++){
			for(int y = 0; y < increase; y++){
				board.get(x).add(0, (byte) 0);
			}
		}
	}
	
	/**
	 * 
	 */
	public void checkIncrease(){
		int minX = 0;
		int minY = 0;
		int maxX = boardSize-1;
		int maxY = boardSize-1;
		
		for(int y = 0; y < boardSize; y++){
			if(board.get(minX).get(y) == 1){
				increaseUpper();
				increaseBelow();
				return;
			}
			if(board.get(maxX).get(y) == 1){
				increaseUpper();
				increaseBelow();
				return;
			}
		}
		
		for(int x = 0; x < boardSize; x++){
			if(board.get(x).get(minY) == 1){
				increaseUpper();
				increaseBelow();
				return;
			}
			if(board.get(x).get(maxY) == 1){
				increaseUpper();
				increaseBelow();
				return;
			}
		}
	}
}
