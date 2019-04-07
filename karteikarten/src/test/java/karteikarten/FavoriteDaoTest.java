package karteikarten;

import de.kksystem.karteikarten.dao.classes.jdbc.FavoritelistDaoJdbcImpl;
import de.kksystem.karteikarten.dao.classes.jdbc.UserDaoJdbcImpl;
import de.kksystem.karteikarten.dao.interfaces.FavoritelistDao;
import de.kksystem.karteikarten.dao.interfaces.UserDao;
import de.kksystem.karteikarten.model.classes.FavoritelistImpl;
import de.kksystem.karteikarten.model.classes.UserImpl;
import de.kksystem.karteikarten.model.interfaces.Favoritelist;
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

public class FavoriteDaoTest {

    private static final String DATABASE_XML_FILE_PATH = "src/test/resources/test-xml/full-export-db.xml";
    private static final String ADD_FAVORITE_XML_FILE_PATH = "src/test/resources/test-xml/test-favorite/expected-add-favorite.xml";
    private static final String DELETE_FAVORITE_XML_FILE_PATH = "src/test/resources/test-xml/test-favorite/expected-delete-favorite.xml";
    private static final String UPDATE_FAVORITE_XML_FILE_PATH = "src/test/resources/test-xml/test-favorite/expected-update-favorite.xml";
    private static final String FAVORITE_TABLE = "Favoritenliste";
    private FavoritelistDao favoritelistDao;

    @Before
    public void setUp() throws ClassNotFoundException, DatabaseUnitException, IOException, SQLException {
        File xmlFile = new File(DATABASE_XML_FILE_PATH);
        boolean xmlFileExists = xmlFile.exists();
        assertTrue(xmlFileExists);
        favoritelistDao = new FavoritelistDaoJdbcImpl();
        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        DBUnitUtils.fullDatabaseExport(connection);
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
    public void testAddFavoriteList() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException {
        ITable expectedTable = DBUnitUtils.setExpected(ADD_FAVORITE_XML_FILE_PATH, FAVORITE_TABLE);

        Favoritelist favoritelist = new FavoritelistImpl(null, 37);
        favoritelistDao.addFavoritelist(null, favoritelist.getUserId());

        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(FAVORITE_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @Test
    public void testDeleteFavoriteList() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException{
        ITable expectedTable = DBUnitUtils.setExpected(DELETE_FAVORITE_XML_FILE_PATH, FAVORITE_TABLE);

        Favoritelist favoritelist = new FavoritelistImpl(21, null, 34);
        favoritelistDao.delete(favoritelist.getFavoritelistId());
        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(FAVORITE_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @Test
    public void testUpdateFavoritlist() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException{
        ITable expectedTable = DBUnitUtils.setExpected(UPDATE_FAVORITE_XML_FILE_PATH, FAVORITE_TABLE);

        Favoritelist favoritelist = new FavoritelistImpl(21, null, 34);
        favoritelistDao.updateFavoritelist(favoritelist.getFavoritelistId(), favoritelist.getName(), favoritelist.getUserId());

        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(FAVORITE_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }
}
