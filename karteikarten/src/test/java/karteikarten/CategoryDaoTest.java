package karteikarten;

import de.kksystem.karteikarten.dao.classes.jdbc.CategoryDaoJdbcImpl;
import de.kksystem.karteikarten.dao.classes.jdbc.UserDaoJdbcImpl;
import de.kksystem.karteikarten.dao.interfaces.CategoryDao;
import de.kksystem.karteikarten.dao.interfaces.UserDao;
import de.kksystem.karteikarten.model.classes.CategoryImpl;
import de.kksystem.karteikarten.model.classes.UserImpl;
import de.kksystem.karteikarten.model.interfaces.Category;
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
import java.sql.Timestamp;

import static org.junit.Assert.assertTrue;

public class CategoryDaoTest {
    private static final String DATABASE_XML_FILE_PATH = "src/test/resources/test-xml/full-export-db.xml";
    private static final String ADD_CATEGORY_XML_FILE_PATH = "src/test/resources/test-xml/test-category/expected-add-category.xml";
    private static final String DELETE_CATEGORY_XML_FILE_PATH = "src/test/resources/test-xml/test-category/expected-delete-category.xml";
    private static final String UPDATE_CATEGORY_XML_FILE_PATH = "src/test/resources/test-xml/test-category/expected-update-category-name.xml";
    private static final String CATEGORY_TABLE = "Kategorie";
    private CategoryDao categoryDao;

    @Before
    public void setUp() throws ClassNotFoundException, DatabaseUnitException, IOException, SQLException {
        File xmlFile = new File(DATABASE_XML_FILE_PATH);
        boolean xmlFileExists = xmlFile.exists();
        assertTrue(xmlFileExists);
        categoryDao = new CategoryDaoJdbcImpl();
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
    public void testAddCategory() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException {
        ITable expectedTable = DBUnitUtils.setExpected(ADD_CATEGORY_XML_FILE_PATH, CATEGORY_TABLE);
        Category category = new CategoryImpl("cat",  null, 32);
        categoryDao.addCategory(category);
        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(CATEGORY_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @Test
    public void testDeleteCategory() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException{
        ITable expectedTable = DBUnitUtils.setExpected(DELETE_CATEGORY_XML_FILE_PATH, CATEGORY_TABLE);

        Category category = new CategoryImpl(60,"mathe", null, 32);
        categoryDao.deleteCategory(category);

        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(CATEGORY_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @Test
    public void testUpdateCategory() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException{
        ITable expectedTable = DBUnitUtils.setExpected(UPDATE_CATEGORY_XML_FILE_PATH, CATEGORY_TABLE);

        Category category = new CategoryImpl(60,"mathe", null, 32);
        categoryDao.updateCategorieName(category, "mathe3");

        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(CATEGORY_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }
}
