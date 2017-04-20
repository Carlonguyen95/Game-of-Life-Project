package golController;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javafx.scene.control.ToggleButton;

import java.io.File;
import java.util.Arrays;

import javax.swing.JOptionPane;

import golClasses.Readmetodet;

/**
 * This class has all the methods and controls that affects the GUI by user.
 * 
 * @author Carlo Nguyen
 * @author Haweya Jama
 * @author Idris Milamean
 */
public class Control implements Initializable{

    @FXML private Canvas graphics;
    @FXML private ColorPicker colorChangerBtn;
    @FXML private Slider sizeSliderBtn;
    @FXML private ToggleButton startPauseBtn;
    @FXML private Button resetBtn;
    @FXML private Button loadFileBtn;
    @FXML private Button loadUrlBtn;
    @FXML private ComboBox<String> speedBtn;
    @FXML private ListView listview;
    
    ObservableList<String> speedList = FXCollections.observableArrayList("1x", "2x", "4x");

    private GraphicsContext gc;
    private int cellSize = 10;
	private Timeline timeline = new Timeline();
    private byte[][] board = new byte[100][100];
    private Readmetodet FileRead;
    
    @Override
    public void initialize(java.net.URL location,java.util.ResourceBundle resources){
    	FileRead =new Readmetodet();
        gc = graphics.getGraphicsContext2D();
        colorChangerBtn.setValue(Color.BLACK);
        sizeSliderBtn.setValue(10.0);
        speedBtn.setValue("Speed"); 
        speedBtn.setItems(speedList);

        draw();
    }
    
    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }
    
	/**
	 * This method is connected to a togglebutton.
	 * Allows the user to start the game, and pause it anytime.
	 * When the Start-button is pressed, Animation() method will execute.
	 */
    public void toggleBtn() {
    	
    	if(startPauseBtn.isSelected()) {
    		startPauseBtn.setText("Pause");
    		
    		Animation();
    	}else {
    		startPauseBtn.setText("Start");
    		timeline.pause();
    	}
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
     * This method is connected to a combobox.
     * Allows user to select the direction/speed at which the Animation is expected to be played.
     */
    public void speedSlction() { 
        
        if(speedBtn.getValue().equals("1x")) { 
            timeline.setRate(1); 
        } 
        if(speedBtn.getValue().equals("2x")) { 
            timeline.setRate(2); 
        } 
        if(speedBtn.getValue().equals("4x")) { 
            timeline.setRate(4); 
        } 
      }    
    
    /**
     * This method is connected to a button.
     * Allows the user to upload a pattern-file into the board.
     * Format are filtered to .lif and .life
     * When pattern is loaded, clearBoard() clears the current board. 
     * Board gets assigned to new values and being drawn to the screen.
     * 
     * @throws Exception when no file is selected.
     */
    

    
   
    
    
    public void uploadPattern() throws Exception { //uploads pattern from file
    		clearBoard();
	    	FileChooser file = new FileChooser();
	    	// Filtered to only show files with format.
	    	file.getExtensionFilters().addAll(
	    			new ExtensionFilter("*.rle", "*.RLE"));
	    	
	    	// Shows the name of the uploaded file
	    	File path = file.showOpenDialog(null);
	    	
	    	if(path != null) {
	    		listview.getItems().add(path.getName());
	        	
	        	board = FileRead.readBoardFromDisk(path);
	        	
	        	drawBoard();
	    	}
    }
    
    /**
     * This method is connected to a button.
     * Allows user to input a pattern via URL into the board.
     * When URL is loaded, clearBoard() clears the current board. 
     * Board gets assigned to new values and being drawn to the screen.
     * 
     * @throws Exception when no URL is inserted.
     */
    
    
   /*public void loadURL() throws Exception {
    	String url = new String();
    	url = JOptionPane.showInputDialog(null, "Please enter a URL");
    	
    	if(url != null) {
    		clearBoard();
    		FileReader fileR = new FileReader(board);
    		board = u.readFromURL(url);
    		drawBoard();
    	}
    }*/
    

    /**
     * This method allows the user to selfdraw cells on the board, by dragclick with the mouse.
     * Each dragclick on the board will get the pointers coordinate X and Y relative to the Board.
     * The value X and Y get casted into integer values, then put into Board's array.
     * Board[x][y] is equal to 1 means there is a living cell on that coordinate.
     */
    public void drawCell(MouseEvent event) {
    	
    	double x = event.getX()/cellSize;
    	double y = event.getY()/cellSize;
    	
    	board[(int)x][(int)y] = 1;
    	drawBoard();
    }
    
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
     * This method draws the grid of the board.
     * for-loops for X and Y, which adds the size of the cell each iterate to the grid
     * according to the height and width of the Canvas.
     */
    public void drawGrid() {

    	gc.setFill(colorChangerBtn.getValue());
        gc.setStroke(colorChangerBtn.getValue());
        // Thickness of the grid
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
    	timeline.stop();
    	
    	if(startPauseBtn.isSelected()) {
    		startPauseBtn.setText("Start");
    		startPauseBtn.setSelected(false);
    	}
    }
    
    /**
     * this method updates the board by applying the GoL rules to the board. 
     * By using a temporary array that stores the previous state of the board, 
     * the next generation of the board is created.
     */
    public void nextGeneration() { 	
    	byte[][] updated = new byte[board.length][board[0].length];
    	
    	for(int i = 0; i < board.length; i++) { // copies board
    		for( int j =0; j < board[i].length; j++) {
    			updated[i][j] = board[i][j];
    		}
    	}
    	
    	updateBoard(updated);
    	gc.clearRect(0, 0, graphics.getWidth(), graphics.getHeight());
    	
    	for (int i = 0; i < updated.length; i++) {	
            for (int j = 0; j < updated[i].length; j++) {
 
            	if(updated[i][j] == 0) { //the cell is dead
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
     * This method makes the animation of the game by creating a timeline.
     * 
     * */
    public void Animation() {
    	
    	timeline.setCycleCount(Animation.INDEFINITE);
    	timeline.setAutoReverse(true);
    	
    	// Speed
    	KeyFrame keyframe =  new KeyFrame(Duration.millis(150), e -> {
    		nextGeneration();
    	});
    	
    	timeline.getKeyFrames().add(keyframe);
    	timeline.play();
    }
    
    /**
     * This method counts the number of neighbouring cells a given cell has,
     * by iterating through the board with two for-loops that only checks the 8
     * cells surrounding it.
     *@return the number of neighbours the cell has.
     *@param the x and y coordinates of the cell (placement of the cell in the grid)
     * */
    public int neighbours(int x, int y) {

    	int nr = 0; 
    	if(board[x][y] == 1) { //so that the cell doesn't count itself
    		nr = -1;
    	}
    	
    	for(int i = x-1; i <= x+1; i++){
    		
            if(i < board.length && i >= 0){ //cells on the edges (rows)
            	
                for(int j = y-1; j <= y + 1; j++){
                	
                    if(j < board[i].length && j >= 0){ // cells on the edges (columns)
                    	
                        if (board[i][j] == 1) {
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
     * @param the array which contains the previous generation
     * */
    public void updateBoard(byte[][] updated) {

    	for (int i = 0; i < updated.length; i++) {
    		
            for (int j = 0; j < updated[i].length; j++) {
            	                
            	if(board[i][j] == 0) { //the cell is dead
            		if(neighbours(i,j) == 3) {
            			updated[i][j] = 1;
            		}           	
                }
            	
            	else { // the cell is alive
                  if(neighbours(i,j)< 2 || neighbours(i,j) > 3) {
                    updated[i][j] = 0;
                  }               
                
            }
        }
    	
   	} 
    	
  } 
    
}