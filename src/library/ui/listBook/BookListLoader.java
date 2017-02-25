package library.ui.listBook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BookListLoader extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Pane pane = FXMLLoader.load(getClass().getResource("BookList.fxml"));
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setTitle("Book List Table View");
		stage.show();
	}
}