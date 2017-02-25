package library.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {
	
	public DatabaseHandler() {
		createConnection();
		setBookTable();
	}

	private static DatabaseHandler handler;

	private static final String DB_URL = "jdbc:derby:database;create=true";
	private static Connection conn = null;
	private static Statement stm = null;

	public void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			conn = DriverManager.getConnection(DB_URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setBookTable() {
		String TABLE_NAME = "Book";
		try {
			stm = conn.createStatement();
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			if(tables.next()) {
				System.out.println("Table name: " + TABLE_NAME + " already exists. Ready to go!");
			} else {
				stm.execute("CREATE TABLE " + TABLE_NAME + "("
						+ "		tfID varchar(200) primary key,\n"
						+ "		tfTitle varchar(200),\n"
						+ "		tfAuthor varchar(200),\n"
						+ "		tfPublisher varchar(100),\n"
						+ "		isAvail boolean default true"
						+ ")");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet execQuery(String query) {
		ResultSet result;
		try {
			stm = conn.createStatement();
			result = stm.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	public boolean execAction(String qu) {
		try {
			stm = conn.createStatement();
			stm.execute(qu);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}