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
	private List<List<Byte>> board = new ArrayList<>();
	private int cellSize = 10;

	private Canvas graphics;
	private ColorPicker colorChangerBtn;
	private Slider sizeSliderBtn;
	private GraphicsContext gc;

	public DynamicBoard(GraphicsContext gc, Canvas graphics, ColorPicker colorChangerBtn) {
		super(gc,graphics,colorChangerBtn);
		this.gc = gc;
		this.graphics = graphics;
		this.colorChangerBtn = colorChangerBtn;

		int index = 0;
		int indeks = 0;

		while(index < 300) { // makes the board 100x100
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

	public int getCellState(List<List<Byte>> b,int x, int y) {
		try {
			return b.get(x).get(y);

		}

		catch(IndexOutOfBoundsException e) {
			//handle exception

			return 0;
		}	

	}


	public void rectangle() { //makes the grid rectangular (equally many rows and columns)

		int col = board.size(); //x
		int rows = board.get(col).size(); //y

		int index = 0;
		int indeks = 0;


		if(col > rows) {
			while(index <= 100) { // makes the board 100x100
				List <Byte> yBoard = new ArrayList<>();
				while (indeks <= 100) {
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


	public void draw() {  	
		drawGrid();
		drawBoard();

	}

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


	//fiks
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

	@Override
	public void nextGeneration() {

		List<List<Byte>> updated = new ArrayList<>();

		int index = 0;
		int indeks = 0;
		
		while(index < 300) { // makes the board 300x300
			List <Byte> yUpdated = new ArrayList<>();
			while (indeks < 300) {
				yUpdated.add(indeks,(byte)0);		
				indeks++;
			}
			indeks=0;
			updated.add(index,yUpdated);			
			index++;		
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
		//System.out.println("x = " + x + " y = " + y + " neighbours: " + nr); 
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
