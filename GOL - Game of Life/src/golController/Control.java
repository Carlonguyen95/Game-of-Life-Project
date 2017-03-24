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

import golClasses.FileReader;

public class Control implements Initializable{

    @FXML private Canvas graphics;
    @FXML private ColorPicker colorChangerBtn;
    @FXML private Slider sizeSliderBtn;
    @FXML private ToggleButton startPauseBtn;
    @FXML private Button resetBtn;
    @FXML private ComboBox<String> speedBtn;
    @FXML private ListView listview;
    ObservableList<String> speedList = FXCollections.observableArrayList("1x", "2x", "4x");

    private GraphicsContext gc;
    private int cellSize = 10;
	private Timeline timeline = new Timeline();
    private byte[][] board = new byte[100][100];
    
    @Override
    public void initialize(java.net.URL location,java.util.ResourceBundle resources){

        gc = graphics.getGraphicsContext2D();
        colorChangerBtn.setValue(Color.GREEN);
        sizeSliderBtn.setValue(10.0);
        speedBtn.setValue("Speed"); 
        speedBtn.setItems(speedList);

        start_Game();
    }
    
    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }
    
    public void start_Game() {  	
    	draw();
    }
    
	/**
	 *	Buttons for USERs
	 * 
	 */
    
    public void toggleBtn() {
    	
    	if(startPauseBtn.isSelected()) {
    		startPauseBtn.setText("Pause");
    		
    		run();
    	}else {
    		startPauseBtn.setText("Start");
    		pause();
    	}
    }

    @FXML
    public void colorChange() {

        gc.setFill(colorChangerBtn.getValue());
        drawBoard();
    }

    @FXML
    public void sizeChange() {
    	
        cellSize = (int) sizeSliderBtn.getValue();
    	gc.clearRect(0, 0, graphics.getWidth(), graphics.getHeight());
        
    	for (int i = 0; i < board.length; i++) {
    		
            for (int j = 0; j < board[i].length; j++) {
            	                
                if (board[i][j] == 1) {
                	
                }
            }
        }
    	
    	draw();
    }
    
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
    
    @FXML
    public void uploadPattern() throws Exception { //uploads pattern from file
    	
    	FileChooser file = new FileChooser();
    	file.getExtensionFilters().addAll(
    			new ExtensionFilter("LIF, LIFE", "*.lif", "*.life"));
    	
    	File selectedFile = file.showOpenDialog(null);
    	listview.getItems().add(selectedFile.getName());
    	
    	if(selectedFile != null) {
    		FileReader f = new FileReader(board);
        	String path = selectedFile.getAbsolutePath();
        	
        	clearBoard();
        	board = f.readFromDisk(path);
        	drawBoard();
        	
    	}
    	
    	
    }
    

    
    
    /**
     *	Methods for USERs 
     *
     */

 
    
    
    public void drawCell(MouseEvent event) {
    	
    	double x = event.getX()/cellSize;
    	double y = event.getY()/cellSize;
  
    	board[(int)x][(int)y] = 1;
    	drawBoard();
    	
    }

    public void run() {    	
    	Animation();
    }
    
    public void pause() {
    	timeline.pause();
    }
    
    /**
     *	Draw and clear methods for board, grid and cells
     *
     */

    public void draw() {
    	
        drawGrid();
        drawBoard();
    }
    
    public void drawGrid() {

    	gc.setFill(colorChangerBtn.getValue());
        gc.setStroke(colorChangerBtn.getValue());
        gc.setLineWidth(1);

        for (int x = 0; x < graphics.getWidth(); x += cellSize) {
            gc.strokeLine(x, 900, x, 0);
        }
        for (int y = 0; y < graphics.getWidth(); y += cellSize) {
            gc.strokeLine(0, y, 900, y);
        }
    }

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

    @FXML
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
     *	Next Generation methods
     * 
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