package golController;

import golModel.Model;
import golModel.Cell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller implements Initializable{

    @FXML private Canvas graphics;
    @FXML private ColorPicker colorChanger;
    @FXML private Slider sizeSlider;

    private GraphicsContext gc;
    /*private int cellSize = 10;
    private byte [][] board = new byte[100][100];*/
    
    @Override
    public void initialize(java.net.URL location,java.util.ResourceBundle resources){

        gc = graphics.getGraphicsContext2D();
        colorChanger.setValue(Color.BLACK);
        sizeSlider.setValue(10.0);

        start_Game();
    }

    public void start_Game() {
    	
    	draw();
    }

    public void closeProgram(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    public void drawCell(MouseEvent event) {
    	
    	double x = event.getX()/cellSize;
    	double y = event.getY()/cellSize;
  
    	board[(int)x][(int)y] = 1;
    	
    	drawBoard();
    	
    }
    
    public void draw() {
    	
        drawGrid();
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
                	
                	draw();                
                }
            }
        }
    	
    	drawGrid();
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
    }
    

    }
    	


