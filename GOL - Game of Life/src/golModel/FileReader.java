package golModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileReader {
	
	private ListView listview;

    public void readGameBoard(Reader r) throws IOException {
    	
    }
    
    public void readGameBoardFromDisk() throws FileNotFoundException, IOException {
    	FileChooser file = new FileChooser();
    	file.getExtensionFilters().addAll(
    			new ExtensionFilter("JPG, PNG, GIF Files", "*.jpg", "*.png", "*.gif"));
    	
    	File selectedFile = file.showOpenDialog(null);
    	
    	if(selectedFile != null) {
    		listview.getItems().add(selectedFile.getName());
    		readGameBoard(new FileReader(selectedFile));
    	}else {
    		System.out.println("File is invalid!");
    	}
    }
}
