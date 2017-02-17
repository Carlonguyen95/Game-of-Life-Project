package golMVC;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.awt.Point;

public class Controller implements Initializable{

    @FXML private Canvas graphics;
    @FXML private ColorPicker colorChanger;
    @FXML private Slider sizeSlider;

    private GraphicsContext gc;
    private int cellSize = 25;
    private byte [][] board = new byte[100][100];
    /*private byte[][] board = {
    		
		    {1, 0, 0, 1},
		    {0, 1, 1, 0},
		    {0, 1, 1, 0},
		    {1, 0, 0, 1},
};*/
    
    
    @Override
    public void initialize(java.net.URL location,java.util.ResourceBundle resources){

        gc = graphics.getGraphicsContext2D();
        colorChanger.setValue(Color.BLACK);
        sizeSlider.setValue(5.0);

        start_Game();
    }

    public void start_Game() {
    	
    	draw();
    }

    public void closeProgram(ActionEvent event) {
    	
        System.exit(0);
    }
    
    
    public void drawCell(MouseEvent event) {
    	
    	Point p = new Point();
    	p.x = (int) event.getX();
    	p.x = (int) event.getY();
    	
    	
    	    	
    	for (int i = 0; i < board.length; i++) {
    		
            for (int j = 0; j < board[i].length; j++) {
            	
                System.out.print(board[i][j]);
                
                if (board[(int) event.getX()][(int) event.getY()] == 1) {
                	
                    gc.fillRect(i*cellSize, j*cellSize,cellSize,cellSize);
                    gc.setFill(colorChanger.getValue());
                }
            }
            //System.out.println(" ");
        }
    	
    	draw();

    }

    public void drawGrid() {
    	
        gc.setFill(colorChanger.getValue());
        gc.setStroke(colorChanger.getValue());
        gc.setLineWidth(1);

        for (double x = 0; x < graphics.getWidth(); x += cellSize) {
            gc.strokeLine(x, 1000, x, 0);
        }
        for (double y = 0; y < graphics.getHeight(); y += cellSize) {
            gc.strokeLine(0, y, 1000, y);
        }
    }

    public void drawBoard() {

    	for (int i = 0; i < board.length; i++) {
    		
            for (int j = 0; j < board[i].length; j++) {
            	
                System.out.print(board[i][j]);
                
                if (board[i][j] == 1) {
                	
                    gc.fillRect(i*cellSize, j*cellSize,cellSize,cellSize);
                    gc.setFill(colorChanger.getValue());
                }
            }
            //System.out.println(" ");
        }
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
        gc.clearRect(0,0,graphics.getWidth(),graphics.getHeight());
        
    	for (int i = 0; i < board.length; i++) {
    		
            for (int j = 0; j < board[i].length; j++) {
            	
                System.out.print(board[i][j]);
                
                if (board[i][j] == 1) {
                	
                	draw();                
                }
            }
            //System.out.println(" ");
        }
    	
    }

    @FXML
    public void clearBoard() {
    	
        //gc.clearRect(0,0,graphics.getWidth(),graphics.getHeight());
        
    	for (int i = 0; i < board.length; i++) {
    		
            for (int j = 0; j < board[i].length; j++) {
            	
                System.out.print(board[i][j]);
                
                if (board[i][j] == 1) {
                	//gc.clearRect(0,0,graphics.getWidth(),graphics.getHeight());
                    gc.clearRect(i*cellSize, j*cellSize,cellSize,cellSize);
                }
            }
            //System.out.println(" ");
        }
    	
    	drawGrid();
    }
    
    
    public void mouseClicked(MouseEvent event) {

    	draw();
    }
    
}

