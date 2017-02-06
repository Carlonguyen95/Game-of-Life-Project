package MVC;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
	
	//Objekter relatert til GUI
	@FXML private Canvas graphics;
	@FXML private ColorPicker colorPicker;
	@FXML private Slider sizeSlider;
	private List<Point> plist;

	//Denne metoden starter ved oppstart
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
    	colorPicker.setValue(Color.BLUE);
        sizeSlider.setValue(5.0);
    	plist = new ArrayList<Point>();
    	draw();

    }
    
    //Henter x og y kords. Bestemmelse på strl av obj.
    private static class Point {
    	public double x, y;
    	public void draw(GraphicsContext gc, Paint color, double size) {
			gc.setFill(color);
    		gc.fillArc(x, y, size, size, 0, 360, ArcType.ROUND);
    	}
    }
    
    public void draw() {
    	GraphicsContext gc = graphics.getGraphicsContext2D();
        for ( Point p : plist ) {
            p.draw(gc, colorPicker.getValue(), sizeSlider.getValue());
        }
    }
    
    public void mouseClicked(MouseEvent event) {
        Point p = new Point();
        p.x = event.getX();
        p.y = event.getY();
        plist.add(p);
    	draw();
    }
    
    public void exitEvent(ActionEvent event) {
        System.exit(0);
    }
    
    public void clearEvent(ActionEvent event) {
        plist.clear();
        draw();
    }

    public void newSizeEvent(MouseEvent e) {
        draw();
    }
    
    public void newColorEvent(ActionEvent event) {
        draw();
    }


}

