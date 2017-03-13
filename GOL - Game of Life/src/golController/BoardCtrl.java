package golController;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.control.ToggleButton;


/**
 * @author Guestacc
 *
 */
public class BoardCtrl implements Initializable{

    @FXML private Canvas graphics;
    @FXML private ColorPicker colorChanger;
    @FXML private Slider sizeSlider;
    @FXML private ToggleButton startPause;

    private GraphicsContext gc;
    private int cellSize = 10;
	private Timeline timeline = new Timeline();
    private byte[][] board = new byte[100][100];

    
    @Override
    public void initialize(java.net.URL location,java.util.ResourceBundle resources){

        gc = graphics.getGraphicsContext2D();
        colorChanger.setValue(Color.GREEN);
        sizeSlider.setValue(10.0);

        start_Game();
    }
    
    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }
    
    public void start_Game() {
    	
    	draw();
    }
    
    
    public void toggleBtn(MouseEvent e) {
    	
    	if(startPause.isSelected()) {
    		startPause.setText("Pause");
    		
    		run();
    	}else {
    		startPause.setText("Start");
    		pause();
    	}
    }
    

    @FXML
    public void colorChange() {

        gc.setFill(colorChanger.getValue());
        drawBoard();
    }

    @FXML
    public void sizeChange(MouseEvent e) {
    	
        cellSize = (int) sizeSlider.getValue();
    	gc.clearRect(0, 0, graphics.getWidth(), graphics.getHeight());
        
    	for (int i = 0; i < board.length; i++) {
    		
            for (int j = 0; j < board[i].length; j++) {
            	                
                if (board[i][j] == 1) {
                	
                }
            }
        }
    	
    	draw();
    }
    
    @FXML
    public void drawCell(MouseEvent event) {
    	
    	double x = event.getX()/cellSize;
    	double y = event.getY()/cellSize;
  
    	board[(int)x][(int)y] = 1;
    	drawBoard();
    	
    }
    
    
    public void run() {    	
    	Animation();
    }
    
    @FXML
    public void stop(MouseEvent e) {
    	timeline.stop();
    	drawBoard();
    	
    	if(startPause.isSelected()) {
    		startPause.setText("Start");
    		startPause.setSelected(false);
    	}
    }
    
    @FXML
    public void pause() {
    	timeline.pause();
    }
    
    
    /**
     *Draw metoder  
     */
    public void draw() {
    	
        drawGrid();
        drawBoard();
    }
    
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
    	gc.clearRect(0, 0, graphics.getWidth(), graphics.getHeight());
    	for (int i = 0; i < board.length; i++) {
    		
            for (int j = 0; j < board[i].length; j++) {
            	                
                if (board[i][j] == 1) {
                	
                    gc.fillRect(i*cellSize, j*cellSize,cellSize,cellSize);
                    gc.setFill(colorChanger.getValue());
                }
            }
        }
    }
    


    @FXML
    public void clearBoard() {
    	
    	gc.clearRect(cellSize, cellSize, graphics.getWidth(), graphics.getHeight());
    	
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
    	
    	if(startPause.isSelected()) {
    		startPause.setText("Start");
    		startPause.setSelected(false);
    	}
    }
    
    

    
    
    public void nextGeneration() { 	
    	byte[][] updated = new byte[board.length][board[0].length];
    	
    	for(int i = 0; i < board.length; i++) { // copies board
    		for( int j =0; j < board[i].length; j++) {
    			updated[i][j] = board[i][j];
    		}
    	}
    	
    	updateBoard(updated);
 
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
    		
            if(i < board.length && i > 0){ //cells on the edges (rows)
            	
                for(int j = y-1; j <= y + 1; j++){
                	
                    if(j < board[i].length && j > 0){ // cells on the edges (columns)
                    	
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