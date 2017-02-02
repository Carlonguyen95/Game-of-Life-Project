package MVC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Game of Life");
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);

        System.out.println("Test");


    }

}
