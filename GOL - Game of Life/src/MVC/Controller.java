package MVC;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class Controller implements Initializable{

    // @FXML private Button startButton;
    @FXML private Canvas graphics;
   // @FXML private ColorPicker colorChanger;
    @FXML private Slider sizeSlider;

    GraphicsContext gc;
    private int cellSize = 30;
    
    @Override
    public void initialize(java.net.URL location,java.util.ResourceBundle resources){

        gc = graphics.getGraphicsContext2D();
       // colorChanger.setValue(Color.BLACK);

        start_Game();
    }



    private void start_Game() {

        drawGrid();
    }

    public void closeProgram(ActionEvent event) {
    	
        System.exit(0);
    }

    public void drawGrid() {
    	
        gc.setStroke(Color.BLACK);
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
                }
            }
            System.out.println(" ");
        }
    }

    public void draw() {
        drawGrid();
        drawBoard();
    }

    @FXML
    public void colorChange() {

  //      gc.setFill(colorChanger.getValue());
        drawBoard();
    }

    @FXML
    public void sizeChange() {
        cellSize = (int) sizeSlider.getValue();
        gc.clearRect(0,0,600,315);
        draw();
    }

    @FXML
    public void clearBoard() {
        gc.clearRect(0,0,600,315);
        sizeSlider.setValue(10);
        cellSize = (int) sizeSlider.getValue();
    //    colorChanger.setValue(Color.BLACK);
        gc.setFill(Color.BLACK);
        drawGrid();
    }
    
    private byte[][] board = {
						    {1, 0, 0, 1},
						    {0, 1, 1, 0},
						    {0, 1, 1, 0},
						    {1, 0, 0, 1}
		};
}
