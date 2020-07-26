package karteikarten;

import de.kksystem.karteikarten.dao.classes.jdbc.LectionDaoJdbcImpl;
import de.kksystem.karteikarten.dao.classes.jdbc.UserDaoJdbcImpl;
import de.kksystem.karteikarten.dao.interfaces.LectionDao;
import de.kksystem.karteikarten.dao.interfaces.UserDao;
import de.kksystem.karteikarten.model.classes.CategoryImpl;
import de.kksystem.karteikarten.model.classes.LectionImpl;
import de.kksystem.karteikarten.model.classes.UserImpl;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.Lection;
import de.kksystem.karteikarten.model.interfaces.User;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class LectionDaoTest {
    private static final String DATABASE_XML_FILE_PATH = "src/test/resources/test-xml/full-export-db.xml";
    private static final String ADD_LECTION_XML_FILE_PATH = "src/test/resources/test-xml/test-lection/expected-add-lection.xml";
    private static final String DELETE_LECTION_XML_FILE_PATH = "src/test/resources/test-xml/test-lection/expected-delete-lection.xml";
    private static final String UPDATE_LECTION_NAME_XML_FILE_PATH = "src/test/resources/test-xml/test-lection/expected-update-lection-name.xml";
    private static final String UPDATE_LECTION_XML_FILE_PATH = "src/test/resources/test-xml/test-lection/expected-update-lection.xml";
    private static final String LECTION_TABLE = "Lektion";
    private LectionDao lectionDao;


    @Before
    public void setUp() throws ClassNotFoundException, DatabaseUnitException, IOException, SQLException {
        File xmlFile = new File(DATABASE_XML_FILE_PATH);
        boolean xmlFileExists = xmlFile.exists();
        assertTrue(xmlFileExists);
        lectionDao = new LectionDaoJdbcImpl();
        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        IDataSet dataSet = builder.build(xmlFile);

        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }

    @After
    public void tearDown() throws Exception {
        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        IDataSet dataSet = builder.build(new FileInputStream(DATABASE_XML_FILE_PATH));

        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }


    @Test
    public void testAddLection() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException {
        ITable expectedTable = DBUnitUtils.setExpected(ADD_LECTION_XML_FILE_PATH, LECTION_TABLE);

        Lection lection = new LectionImpl("lec", null, null, 58, 19);
        lectionDao.addLection(lection);

        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(LECTION_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @Test
    public void testDeleteLection() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException{
        ITable expectedTable = DBUnitUtils.setExpected(DELETE_LECTION_XML_FILE_PATH, LECTION_TABLE);

        Lection lection = new LectionImpl(50, "Cat2lekt2", null, null, 58, 14);
        lectionDao.deleteLection(lection);

        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(LECTION_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @Test
    public void testUpdateLectionName() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException{
        ITable expectedTable = DBUnitUtils.setExpected(UPDATE_LECTION_NAME_XML_FILE_PATH, LECTION_TABLE);

        Lection lection = new LectionImpl(50, "lec345", null, null, 58, 14);
        lectionDao.updateName(lection.getName(), lection.getLectionId());

        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(LECTION_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }


    @Test
    public void testUpdateLection() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException{
        ITable expectedTable = DBUnitUtils.setExpected(UPDATE_LECTION_XML_FILE_PATH, LECTION_TABLE);

        Lection lection = new LectionImpl(50, "lec345", null, null, 58, 14);
        lectionDao.updateLection(lection);

        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(LECTION_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }
}
