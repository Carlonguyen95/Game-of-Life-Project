package golMVC;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

public class Launcher extends Application {
	
	private static void initAndShowGUI() {
		
		final JFXPanel fxPanel = new JFXPanel();
		
        JFrame window = new JFrame("Game of Life");
        
        window.add(new GameOfLife());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
		window.pack();
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				initFX(fxPanel);
			}
			
		});
		
	}

			private static void initFX(JFXPanel fxPanel) {
				
				Scene scene = createScene();
				fxPanel.setScene(scene);
				
			}

			private static Scene createScene() {
				
		        //Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
				Group root = new Group();
		        Scene scene = new Scene(root);
				return (scene);
			}

    /**@Override
    public void start(Stage primaryStage) throws Exception {
    	
        //Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        
        primaryStage.setTitle("Game of Life");
        primaryStage.setResizable(false);
        primaryStage.show();
        
    }**/
    
    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		
	}
}
