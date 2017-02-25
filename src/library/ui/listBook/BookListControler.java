package library.ui.listBook;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import library.database.DatabaseHandler;

public class BookListControler implements Initializable {
	
	ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableColumn<Book, String> colID;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, String> colPublisher;

    @FXML
    private TableColumn<Book, Boolean> colAvailability;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initColumns();
		loadData();
	}
	
	public void initColumns() {
		colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		colID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
		colPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
		colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
	}
	
	public void loadData() {
		DatabaseHandler databaseHandler = new DatabaseHandler();
		String qu = "SELECT * FROM BOOK";
		ResultSet rs = databaseHandler.execQuery(qu);
		try {
			while (rs.next()) {
				try {
					String title = rs.getString("tfTitle");
					String author = rs.getString("tfAuthor");
					String id = rs.getString("tfID");
					String publisher = rs.getString("tfPublisher");
					Boolean availability = rs.getBoolean("isAvail");
					
					list.add(new Book(title, id, author, publisher, availability));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tableView.getItems().setAll(list);
	}
	
	public static class Book {
		private final SimpleStringProperty title;
		private final SimpleStringProperty id;
		private final SimpleStringProperty author;
		private final SimpleStringProperty publisher;
		private final SimpleBooleanProperty availability;
		
		public Book(String title, String id, String author, String publisher, boolean availability) {
			this.title = new SimpleStringProperty(title);
			this.id = new SimpleStringProperty(id);
			this.author = new SimpleStringProperty(author);
			this.publisher = new SimpleStringProperty(publisher);
			this.availability = new SimpleBooleanProperty(availability);
		}

		public String getTitle() {
			return title.get();
		}

		public String getId() {
			return id.get();
		}

		public String getAuthor() {
			return author.get();
		}

		public String getPublisher() {
			return publisher.get();
		}

		public boolean getAvailability() {
			return availability.get();
		}
	}
}