package MVC;

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

public class Controller implements Initializable {

    @FXML
    private Canvas graphics;
    @FXML
    private ColorPicker colorChanger;
    @FXML
    private Slider sizeSlider;

    private GraphicsContext gc;
    private int cellSize = 25;
    private byte[][] board = new byte[100][100];
    /*private byte[][] board = {
            { 1, 0, 0, 1 },
            { 0, 1, 1, 0 },
            { 0, 1, 1, 0 },
            { 1, 0, 0, 1 }
    };*/





    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        gc = graphics.getGraphicsContext2D();
        colorChanger.setValue(Color.BLACK);
        sizeSlider.setValue(5.0);

      drawGrid();
      drawBoard();
    }




    public void updateBoard(MouseEvent event) {

        double x = event.getX() / cellSize;
        double y = event.getY() / cellSize;

        board[(int) x][(int) y] = 1;

     //
        drawBoard();
       // nextGeneration();
    }

    public void drawCell(MouseEvent event) {

        updateBoard(event);

    }

    public void start_Game() {

        Animation();
    }



    public void Animation() {

        Timeline timeline = new Timeline();

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);

        // speed
        KeyFrame keyf = new KeyFrame(Duration.millis(500), e -> {

            nextGeneration();

        });
        timeline.getKeyFrames().add(keyf);
        timeline.play();

    }

    public void nextGeneration() {
        System.out.println("HEI");

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] == 1) {

                    board[i][j] = 0;
                    gc.clearRect(i * cellSize, j * cellSize, cellSize, cellSize);

                } else {

                    board[i][j] = 1;
                    gc.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);

                }
            }


        }
    }

    public void drawBoard() {

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] == 1) {

                    gc.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                    gc.setFill(colorChanger.getValue());
                }

            }
        }
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


    public void draw() {

        drawGrid();
        drawBoard();
        nextGeneration();


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

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] == 1) {

                    gc.clearRect(i * cellSize, j * cellSize, cellSize, cellSize);
                    board[i][j] = 0;
                }
            }
        }

        drawGrid();
    }


}