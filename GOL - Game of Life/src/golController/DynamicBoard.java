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
	private List <Byte> yBoard = new ArrayList<>();
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
		
		int index =0;
		while(index <= 100) { // makes the board 100x100
			yBoard.add((byte)0);
			board.add(yBoard);
			index++;
		}

	}
	
	@Override
	public void setCellState(int x, int y) throws Exception {
		try {
			yBoard.add(y,(byte)1);
			board.add(y,yBoard);
			if(board.get(x).get(y) == 1) {
				System.out.println("endret");
			}
			else {
				System.out.println("ikke endret");
			}
			
		}
		
		catch(IndexOutOfBoundsException e) {
			//handle exception
			e.printStackTrace();
			//	board.add(x).add(y);
		}
		
		
	}
	
	public int getCellState(int x, int y) {
		try {
			return board.get(x).get(y);
			
		}
		
		catch(IndexOutOfBoundsException e) {
			//handle exception
			
			return 0;
		}
		
		
	}
	
	
	public void rectangle() { //makes the grid rectangular (equally many rows and columns)
		
		int col = board.size();
		int rows = board.get(col).size();
		
		int i = 0;
		
		
		if(col > rows) {
			while(col != rows) {
				board.get(i).set(col,(byte)0);
				col++;
				rows++;
				i++;
				
			}
			
		}
		
		else if (col < rows) {
			//fix
			
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
                if (board.get(i).get(j) == 1) {
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
	                    gc.clearRect(i*cellSize, j*cellSize,cellSize,cellSize);
	                    // board.get(i).get(j) = 0;
	                }
	            }
	        }
	    	
	    	drawGrid();
	    }
	
	
	//use set/get cellstate methods
	@Override
	public void nextGeneration() {

		List<List<Byte>> updated = new ArrayList<>();
    	
    	for(int i = 0; i < board.size(); i++) { // copies board
    		for( int j =0; j < board.get(i).size(); j++) {
    			updated.get(i).set(j, (byte) board.get(i).get(j));
    		}
    	}
    	
    	updateBoard(updated);
    	gc.clearRect(0, 0, graphics.getWidth(), graphics.getHeight());
    	
    	for (int i = 0; i < updated.size(); i++) {	
            for (int j = 0; j < updated.get(i).size(); j++) {
 
            	if(updated.get(i).get(j) == 0) { //the cell is dead
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
    	//rectangular();
    	draw();
		
		
		
	}
	
	@Override
	public int neighbours(int x, int y) {
		
		int nr = 0; 
    	if(board.get(x).get(y) == 1) { //so that the cell doesn't count itself
    		nr = -1;
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
	
		
	//fiks
	  public void updateBoard( List<List<Byte>> updated) {
		  
		  for (int i = 0; i < updated.size(); i++) {
	    		
	            for (int j = 0; j < updated.get(i).size(); j++) {
	            	                
	            	if(board.get(i).get(j) == 0) { //the cell is dead
	            		if(neighbours(i,j) == 3) {
	            			updated.get(i).set(j,(byte)1);
	            	
	            		}           	
	                }
	            	
	            	else { // the cell is alive
	                  if(neighbours(i,j)< 2 || neighbours(i,j) > 3) {
	                    updated.get(i).set(j, (byte) 0);
	                  }               
	                
	            }
	        }
	    	
	   	} 
	    	
	  }
	    
	  
}
