/**
 * This class has all the methods and controls that affects the GUI by user.
 * 
 * @author Carlo Nguyen
 * @author Haweya Jama
 * @author Idris Milamean
 */

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
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JOptionPane;

import golClasses.DynamicBoard;
import golClasses.FileConverter;
import golClasses.Pool;
import golClasses.Error;

public class Control implements Initializable{

	// Data field to FXML
	@FXML private Canvas graphics;
	@FXML private ColorPicker colorChangerBtn;
	@FXML private Slider sizeSliderBtn;
	@FXML private ToggleButton startPauseBtn;
	@FXML private Button resetBtn;
	@FXML private Button loadFileBtn;
	@FXML private Button loadUrlBtn;
	@FXML private ComboBox<String> speedBtn;
	@FXML private ListView<String> listview;

	ObservableList<String> speedList = FXCollections.observableArrayList("1x", "2x", "3x","4x");

	// Data field
	private GraphicsContext gc;
	private Timeline timeline = new Timeline();
	private byte[][] gameBoard = new byte[100][100];

	//Objects
	FileConverter FileRead;
	DynamicBoard board;
	Pool p;
	
	@Override
	public void initialize(java.net.URL location,java.util.ResourceBundle resources){

		// Setting up Graphics
		gc = graphics.getGraphicsContext2D();

		// Setting up Buttons
		colorChangerBtn.setValue(Color.BLACK);
		sizeSliderBtn.setValue(5.0);
		speedBtn.setValue("Speed"); 
		speedBtn.setItems(speedList);

		FileRead = new FileConverter();

		// Setting up Board
		DynamicBoard board = new DynamicBoard(gc, graphics,colorChangerBtn, sizeSliderBtn);
		//board = Collections.synchronizedList(new ArrayList(byte);

		this.board = board;


		/* starts threads
		(due to a lack of time we couldn't finish implementing threads. some of
		 * these parts are commented out so that the game can run.
		 * */
		try {

			//  p.setTask(() -> {board.nextGenerationConcurrent();});
			// p.clearWorkers();
		}
		catch(Exception e) { //specify exception
			//  p.clearWorkers();
		}

		board.draw();
		
		KeyFrame keyframe =  new KeyFrame(Duration.millis(150), e -> {
			//board.checkIncrease();
			board.nextGeneration();
		});
		
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.setAutoReverse(true);
		timeline.getKeyFrames().add(keyframe);
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

			timeline.play();
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
		board.draw();
	}

	/**
	 * This method is connected to a slider.
	 * Allows the user to select size to the board and drawn cells.
	 * The method sets the new size to cellSize, gc clears the board, then draw() is executed.
	 * And draws everything to the screen.
	 */
	public void sizeChange() {
		board.sizeChange();
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
		if(speedBtn.getValue().equals("3x")) { 
			timeline.setRate(3); 
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
		FileChooser file = new FileChooser();
		// Filtered to only show files with format.
		file.getExtensionFilters().addAll(
				new ExtensionFilter("*.rle", "*.RLE"));

		// Shows the name of the uploaded file
		File path = file.showOpenDialog(null);

		try{
			if(path != null) {
				board.clearBoard();
				listview.getItems().add(path.getName());
				FileConverter fileR = new FileConverter();
				gameBoard = fileR.readBoardFromDisk(path);
				board.setBoard(gameBoard);
				board.drawBoard();
		}	}
		catch (NumberFormatException e) {  // wrong format in file
			Error.formatError();
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
	public void loadURL() throws Exception {
		String url = new String();
		url = JOptionPane.showInputDialog(null, "Please enter a URL");
		
		try{
			if(url != null) {
				board.clearBoard();
	
				FileConverter fileR = new FileConverter();
				gameBoard = fileR.readFromURL(url);
				board.setBoard(gameBoard);
				board.drawBoard();
			}
		}		
		catch (NumberFormatException e) {  // wrong format in file
			Error.urlError();
		}
		catch (MalformedURLException m) { // invalid URL
			Error.malformedURLError();
		}
		catch (IOException ioe) { //general IO exception
			Error.generalError();
		}
	}


	/**
	 * This method allows the user to draw cells on the board themself, by dragclick with the mouse.
	 * Each dragclick on the board will get the pointers coordinate X and Y relative to the Board.
	 * The value X and Y get casted into integer values, then put into Board's array.
	 * Board[x][y] is equal to 1 means there is a living cell on that coordinate.
	 */
	public void drawCell(MouseEvent event) {

		int x = (int) event.getX()/board.getCellSize();
		int y = (int) event.getY()/board.getCellSize();


		try {
			if(x>=0 && y>= 0 && x <gc.getCanvas().getWidth() && y < gc.getCanvas().getHeight())  {
				board.board.get(x).set(y, (byte) 1);
				gc.fillRect(x*board.getCellSize(), y*board.getCellSize(),board.getCellSize()-1,board.getCellSize()-1);
				gc.setFill(colorChangerBtn.getValue());

			}
		} 
		
		catch (Exception e) {	
			Error err = new Error();
			err.generalError();
		}
	}

	/**
	 * this method clears the board by killing all the alive cells.
	 * */
	public void resetBoard() {
		gameBoard = board.getBoard();
		board.clearBoard();


		if(startPauseBtn.isSelected()) {
			startPauseBtn.setText("Start");
			startPauseBtn.setSelected(false);

			timeline.stop();
		}
	}
}