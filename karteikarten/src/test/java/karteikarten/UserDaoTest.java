package karteikarten;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.kksystem.karteikarten.dao.classes.jdbc.UserDaoJdbcImpl;
import de.kksystem.karteikarten.dao.interfaces.UserDao;
import de.kksystem.karteikarten.model.classes.UserImpl;
import de.kksystem.karteikarten.model.interfaces.User;

public class UserDaoTest {

	private static final String DATABASE_XML_FILE_PATH = "src/test/resources/test-xml/full-export-db.xml";
	private static final String ADD_USER_XML_FILE_PATH = "src/test/resources/test-xml/testUser/expected-add-user.xml";
    //private static final String DELETE_USER_XML_FILE_PATH = "src/test/resources/test-xml/testDataDeleteUser/expected-add-user.xml";
	private static final String USER_TABLE = "Benutzer";
	private UserDao userDao;
	
	private void fullDatabaseImport(File file) throws ClassNotFoundException, DatabaseUnitException, IOException, SQLException {
		IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
		DBUnitUtils.fullDatabaseExport(connection);
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		IDataSet dataSet = builder.build(file);
		
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
	
	@Before
	public void setUp() throws ClassNotFoundException, DatabaseUnitException, IOException, SQLException {
		File xmlFile = new File(DATABASE_XML_FILE_PATH);
		boolean xmlFileExists = xmlFile.exists();
		assertTrue(xmlFileExists);
		userDao = new UserDaoJdbcImpl();
		fullDatabaseImport(xmlFile);
	}
	
	@After
    public void tearDown() throws Exception {
		//userDao.deleteUser(new UserImpl(newUserId, "", "", "", "", "", null));
		IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		IDataSet dataSet = builder.build(new FileInputStream(DATABASE_XML_FILE_PATH));

		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }
	

	@Test
	public void testAddUser() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		File expectedXmlFile = new File(ADD_USER_XML_FILE_PATH);
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		IDataSet expectedDataSet = builder.build(expectedXmlFile);
		ReplacementDataSet dataSet = new ReplacementDataSet(expectedDataSet);
		dataSet.addReplacementObject("[SYSTIMESTAMP]", timestamp.getTime());
		ITable expectedTable = dataSet.getTable(USER_TABLE);
		//ITable expectedTable = expectedDataSet.getTable(USER_TABLE);
		
		User user = new UserImpl("gian-test333", "giantest333@example.com", "test1231", "testnutzer1", "testnutzervorname1", timestamp);
		userDao.addUser(user);
		IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
		IDataSet actualDataSet = connection.createDataSet();
		ITable actualTable = actualDataSet.getTable(USER_TABLE);
		
	    ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable, 
	    		expectedTable.getTableMetaData().getColumns());
		
		Assertion.assertEquals(expectedTable, filteredTable);
	}

	@Test
    public void testDeleteUser() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException{
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
//        File expectedXmlFile = new File(ADD_USER_XML_FILE_PATH);
//        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
//        IDataSet expectedDataSet = builder.build(expectedXmlFile);
//        ReplacementDataSet dataSet = new ReplacementDataSet(expectedDataSet);
//        dataSet.addReplacementObject("[SYSTIMESTAMP]", timestamp.getTime());
//        ITable expectedTable = dataSet.getTable(USER_TABLE);



    }
}