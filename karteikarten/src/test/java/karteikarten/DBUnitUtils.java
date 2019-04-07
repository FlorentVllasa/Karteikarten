package karteikarten;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class DBUnitUtils {
	private static final String PATH_FULL_EXPORT_XML_FILE = "src/test/resources/test-xml/full-export-db.xml";
	private static final String DB_URL = "jdbc:sqlite:src/main/resources/database/Projekt.db";
	
	public static IDatabaseConnection getDatabaseConnection() throws SQLException, DatabaseUnitException, ClassNotFoundException {
		Connection jdbcConnection = DriverManager.getConnection(DB_URL);
		return new DatabaseConnection(jdbcConnection);
	}
	
	public static void fullDatabaseExport(IDatabaseConnection connection) throws DataSetException, SQLException, FileNotFoundException, IOException {
		IDataSet fullDataSet = connection.createDataSet();
		FlatXmlDataSet.write(fullDataSet, new FileOutputStream(PATH_FULL_EXPORT_XML_FILE));
	}
	
}