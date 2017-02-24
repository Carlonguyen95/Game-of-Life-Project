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


public class BoardCtrl implements Initializable{

    @FXML private Canvas graphics;
    @FXML private ColorPicker colorChanger;
    @FXML private Slider sizeSlider;
    @FXML private ToggleButton startPause;
    @FXML private ToggleButton eraseBtn;

    private GraphicsContext gc;
    private int cellSize = 10;
	private Timeline timeline = new Timeline();
    private byte[][] board = new byte[100][100];
    /*private byte[][] board = {
    		{ 1, 0, 0, 1 },
    		{ 0, 1, 1, 0 },
    		{ 0, 1, 1, 0 },
    		{ 1, 0, 0, 1 }
    		
    		 };*/
    
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
    
    public void draw() {
    	
        drawGrid();
        drawBoard();
    }
    
    @FXML
    public void drawCell(MouseEvent event) {
    	
    	double x = event.getX()/cellSize;
    	double y = event.getY()/cellSize;
  
    	board[(int)x][(int)y] = 1;
    	drawBoard();
    	
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
    public void eraseCell(MouseEvent event) {
    	if (eraseBtn.isSelected()) {
    	double x = event.getX()/cellSize;
    	double y = event.getY()/cellSize;
  
    	gc.clearRect(cellSize, cellSize, x, y);
    	
    	System.out.println("Text");
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
    	}
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
    public void run() {    	
    	Animation();
    }
    
    @FXML
    public void stop(MouseEvent e) {
    	timeline.stop();
    	drawBoard();
    	
    	if(startPause.isSelected()) {
    		startPause.setText("Start");
    	}
    }
    
    @FXML
    public void pause() {
    	timeline.pause();
    }
    
    
    public void nextGeneration() {
    	
    	for (int i = 0; i < board.length; i++) {
    		
            for (int j = 0; j < board[i].length; j++) {
            	                
                if (board[i][j] == 1) {
                	 //Any live cell with fewer than two live neighbours dies.
           		 //Any live cell with more than three live neighbours dies
           		   //Any live cell with two or three live neighbours lives, unchanged, to the next generation.
           		   //Any dead cell with exactly three live neighbours will come to life. 
                	
                    gc.clearRect(i*cellSize, j*cellSize,cellSize,cellSize);

                }else {
                	 //Any dead cell with exactly three live neighbours will come to life. 
                    gc.fillRect(i*cellSize, j*cellSize,cellSize,cellSize);

                }
            }
        }
    	draw();
    	integrityRules();
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
    
    public void toggleBtn(MouseEvent e) {
    	
    	if(startPause.isSelected()) {
    		startPause.setText("Pause");
    		
    		run();
    	}else {
    		startPause.setText("Start");
    		pause();
    	}
    }
    
    
    
    
    
    public int numberOfNeigbours(int i, int j) {
    int nr = 0;
    //antar at alle celler som sjekkes ikke er kant-celler (har alltd 8 naboer)
    	
    // 1 lever, 0 dod
    
    if(board[i-1][j] == 1) { //under
    	nr++;
    
    }
    if(board[i+1][j] == 1) { //over
    nr++;	
    	
    	
    }
    if(board[i][j+1] == 1) { //hoyre
    	nr++;
    
    }
    if(board[i][j-1] == 1) { //venstre
    nr++;
    
    }
    
    if(board[i-1][j+1] == 1) { //hoyre diagonalt ned
    	nr++;
    
    }
      if(board[i-1][j-1] == 1) { //hoyre diagonalt opp
    nr++;
    
    }
    
       if(board[i+1][j+1] == 1) { //venstre diagonalt ned
    	nr++;
    
    }
    
    if(board[i+1][j-1] == 1) { //venstre diagonalt opp
    nr++;	
    	
    	
    }
 
    if(nr ==2 || nr == 3) {
    	return 1;
    }
    else {
    	return 0;
    }
   }
    
    
    
    public void integrityRules() {
   
   for(int i = 0; i<board.length; i++) {
	   for (int j = 0; j <board.length; j++) {
		   //Any live cell with fewer than two live neighbours dies.
		   if(board[i][j] == 1 && numberOfNeigbours(i,j) < 2) {
			   board[i][j] = 0;
			   gc.clearRect(i*cellSize, j*cellSize, cellSize, cellSize);
			   
		   }
				   
		   //Any live cell with more than three live neighbours dies
		   if(board[i][j] == 1 && numberOfNeigbours(i,j) == 0) {
			   board[i][j] = 0;
			   gc.clearRect(i*cellSize, j*cellSize, cellSize, cellSize);
		   }
    	
		   //Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		   if(board[i][j] == 1 &&  numberOfNeigbours(i,j) == 1 ) {
			   board[i][j] = 1;
			   gc.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
		   }
    	
		   //Any dead cell with exactly three live neighbours will come to life. 
		   if(board[i][j] == 0 && numberOfNeigbours(i,j)== 1) {
			   board[i][j] = 1;
			   gc.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
		   }
    	
	  } 
   	}    
	   
    }    	
    	
}
    
