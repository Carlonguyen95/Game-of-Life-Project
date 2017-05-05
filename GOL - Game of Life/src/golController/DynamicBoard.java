/**
 * This class represent the dynamic Game board.
 * 
 * @author Carlo Nguyen
 * @author Haweya Jama
 * @author Idris Milamean
 */

package golController;

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

import golClasses.*;
import golClasses.Error;

public class DynamicBoard extends Board {  

	// Data field
	protected List<List<Byte>> board = new ArrayList<>();
	protected int cellSize = 15;
	int index = 0;
	int indeks = index;
	int boardSize = indeks;

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

		while(index < 100) { // makes the board 500x500 (as a starting point)
			List <Byte> yBoard = new ArrayList<>();
			while (indeks < 100) {
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
			if(x > board.size() || y > board.get(0).size()) {

				ArrayList<Byte> yBoard = new ArrayList<Byte>();
				while(index < x-board.size()) { // makes the board 500x500 (as a starting point)
					while (indeks <x-board.size()) {
						yBoard.add(indeks,(byte)0);		
						indeks++;
					}



					board.get(x).set(y,(byte)1); 

				}

			}
		}
		catch(IndexOutOfBoundsException e) {
			Error err = new Error();
			err.generalError();

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
			Error err = new Error();
			err.generalError();

			return 0;
		}	
	}

	/**
	 * helping method for the Class DynamicBoardTest, to check if the
	 * dynamic board is working
	 * @param x and y coordinates for the given cell
	 * @return 0 if the cell is dead, and 1 if the cell is alive
	 * */

	public int getCellStateTest(int x, int y) {
		try {
			return board.get(x).get(y);
		}
		catch (Exception e) {
			Error err = new Error();
			err.generalError();
			return 0;
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
					//rectangular(i,j);

				}
			}
		}
	}

	/**
	 * returns the variable cellsSize
	 * @return cellSize
	 * */
	public int getCellSize(){ return this.cellSize;}


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
	 * this metohd converts a given static 2d board into a dynamic Arraylist. the static
	 * array has information about what cells are alive and which are not, which will be
	 * set in the new board. the static board is the information from a diskFile or URL.
	 * overrides the method from board to fit the dynamic board
	 * @param the static 2d board 
	 * */

	@Override
	public void setBoard(byte[][] gameBoard) {
		List<List<Byte>> newBoard = new ArrayList<List<Byte>>();
		int valX = 0;
		int valY =0;

		for(int i=0;i<gameBoard.length;i++){
			newBoard.add(new ArrayList<Byte>());
			for(int j=0;j<gameBoard[0].length;j++){
				newBoard.get(i).add(gameBoard[i][j]);

				valX = i;
				valY = j;

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
	@Override
	public void nextGeneration() {
		List<List<Byte>> updated = new ArrayList<>();
		int tempX = 0;
		int tempY = 0;

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

	}

	/**
	 * this method splits the nextGeneration method into several tasks that are given to
	 * multiple threads, optimalize the performance. 
	 *(due to a lack of time we couldn't finish implementing threads.)
	 * */
	public synchronized void nextGenerationConcurrent() {
		//split board, give each side a task and run nextGeneration();

		board = Collections.synchronizedList( new ArrayList<List<Byte>>());
		int n = board.size()/Runtime.getRuntime().availableProcessors();

		ExecutorService executor = Executors.newCachedThreadPool();
		Executors.newFixedThreadPool(n);

	}

	/**
	 * this method prints out the performance of the nextGeneration(); 
	 * method. (due to a lack of time we couldn't finish implementing threads.)
	 * */
	public void nextGenerationConcurrentPrintPerformance() {
		long start = System.currentTimeMillis();
		//neighbours();
		long elapsed = System.currentTimeMillis() - start;
		System.out.println("Counting time (ms): " + elapsed);
	}


	/**
	 * This method counts the number of neighbouring cells a given cell has,
	 * by iterating through the board with two for-loops that only checks the 8
	 * cells surrounding it. 
	 * overrides the method in Board to fit the dynamic board
	 *@return the number of neighbours the cell has.
	 *@param the x and y coordinates of the cell (placement of the cell in the grid)
	 * */
	@Override
	public int neighbours(int x, int y) {

		int nr = 0;

		if(board.get(x).get(y) == 1) { // so that the cell doesn't count itself
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
	 * This method updates the board by applying the GoL rules,
	 * by iterating through the board with two for-loops and assigning
	 * 1 or 0 using the GoL rules.
	 * @param the ArrayList which contains the previous generation
	 * */
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
	 * this method checks if its necessary to expand the board by checking
	 * the given cell. if it is, it expands the board by calling on two other methods
	 * (due to a lack of time we couldn't finish making the board dynamic.)
	 * @param the coordinates for the cell
	 * */
	public void checkExpand(int x, int y){

		int minX = 0;
		int minY = 0;
		int maxX = boardSize-1;
		int maxY = boardSize-1;

		for(int j = 0; y < boardSize; y++){
			if(board.get(minX).get(y) != null){
				expandUpper(x,y);
				expandBelow(x,y);
				return;
			}
			if(board.get(maxX).get(y) != null){
				expandUpper(x,y);
				expandBelow(x,y);
				return;
			}
		}

		for(int i = 0; x < boardSize; x++){
			if(board.get(x).get(minY) != null){
				expandUpper(x,y);
				expandBelow(x,y);
				return;
			}
			if(board.get(x).get(maxY) != null){
				expandUpper(x,y);
				expandBelow(x,y);
				return;
			}
		}
	}

	/**
	 * this method expands the board with two cells south-east from the given
	 * cell point. (due to a lack of time we couldn't finish making the board dynamic.)
	 * @param the coordinates for the cell.
	 * */
	public void expandBelow(int i, int j){

		int expand = 2;
		boardSize += expand;

		int boardSizeX = i-board.size();
		int boardSizeY = j-board.get(0).size();
		List<Byte> innerArray = new ArrayList<>();

		for(int x = i; x < boardSizeX; x++){
			for(int y = j; y < boardSizeY; y++){
				innerArray.add((byte) 0);
			}
			board.add(innerArray);
		}

		for(int x = i; x < boardSize; x++){
			for(int y = j; y < expand; y++){
				board.get(x).add((byte) 0);
			}
		}
	}

	/**
	 * this method expands the board with two cells north-west from the given
	 * cell point (due to a lack of time we couldn't finish making the board dynamic.)
	 * @param the coordinates for the cell.
	 * */
	public void expandUpper(int i,int j){
		int expand = 2;
		boardSize += expand;

		int boardSizeX = i-board.size();
		int boardSizeY = j-board.get(0).size();
		List<Byte> innerArray = new ArrayList<>();

		for(int x = i; x < boardSizeX; x++){
			for(int y = j; y < boardSizeY; y++){
				innerArray.add(0,(byte) 0);
			}
			board.add(0,innerArray);
		}

		for(int x = i; x < boardSize; x++){
			for(int y = j; y < expand; y++){
				board.get(x).add(0,(byte) 0);
			}
		}
	}


	/**
	 * this method expands the dynamic board by adding cells
	 * behind the given cell
	 * (due to a lack of time we couldn't finish making the board dynamic.)
	 * @param x y coordinates to the cell*/
	public void expandBehind(int x, int y) { 

		for(int i =x-1; i>= 0; i--) {
			for(int j =y-1; j>= 0; j--) {
				if(board.get(i).get(j) != 1) {
					board.get(i).set(j,(byte)0);
				}
			}
		}
	}


}
