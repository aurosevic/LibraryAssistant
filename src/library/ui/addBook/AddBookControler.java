package library.ui.addBook;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.database.DatabaseHandler;

public class AddBookControler implements Initializable {

	@FXML
    private AnchorPane rootPane;
	
	@FXML
	private JFXTextField tfTitle;

	@FXML
	private JFXTextField tfID;

	@FXML
	private JFXTextField tfAuthor;

	@FXML
	private JFXTextField tfPublisher;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnCancel;
	
	private DatabaseHandler databaseHandler;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		databaseHandler = new DatabaseHandler();
		checkData();
	}
	
	@FXML
	void addBook(ActionEvent event) {
		String title = tfTitle.getText();
		String id = tfID.getText();
		String author = tfAuthor.getText();
		String publisher = tfPublisher.getText();
		
		if (title.isEmpty() || id.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please fill all the fields");
			alert.showAndWait();
			return;
		}
		
		String qu = "INSERT INTO BOOK VALUES (" +
					"'" + id + "'," +
					"'" + title + "'," +
					"'" + author + "'," +
					"'" + publisher + "'," +
					"true" +
					")";

		System.out.println(qu);

		if (databaseHandler.execAction(qu)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Success");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Failed");
			alert.showAndWait();
		}
	}

	@FXML
	void cancel(ActionEvent event) {
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();
	}
	
	private void checkData() {
		String qu = "SELECT tfTitle FROM BOOK";
		ResultSet rs = databaseHandler.execQuery(qu);
		try {
			while (rs.next()) {
				try {
					String title = rs.getString("tfTitle");
					System.out.println(title);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}