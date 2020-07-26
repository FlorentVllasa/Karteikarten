package karteikarten;

import de.kksystem.karteikarten.dao.classes.jdbc.PictureDaoJdbcImpl;
import de.kksystem.karteikarten.dao.classes.jdbc.UserDaoJdbcImpl;
import de.kksystem.karteikarten.dao.interfaces.PictureDao;
import de.kksystem.karteikarten.dao.interfaces.UserDao;
import de.kksystem.karteikarten.model.classes.CategoryImpl;
import de.kksystem.karteikarten.model.classes.PictureImpl;
import de.kksystem.karteikarten.model.classes.UserImpl;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.Picture;
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

public class PictureDaoTest {
    private static final String DATABASE_XML_FILE_PATH = "src/test/resources/test-xml/full-export-db.xml";
    private static final String ADD_PICTURE_XML_FILE_PATH = "src/test/resources/test-xml/test-picture/expected-add-picture.xml";
    private static final String DELETE_PICTURE_XML_FILE_PATH = "src/test/resources/test-xml/test-picture/expected-delete-picture.xml";
    private static final String UPDATE_PICTURE_XML_FILE_PATH = "src/test/resources/test-xml/test-picture/expected-update-picture.xml";
    private static final String PICTURE_TABLE = "Bild";
    private PictureDao pictureDao;


    @Before
    public void setUp() throws ClassNotFoundException, DatabaseUnitException, IOException, SQLException {
        File xmlFile = new File(DATABASE_XML_FILE_PATH);
        boolean xmlFileExists = xmlFile.exists();
        assertTrue(xmlFileExists);
        pictureDao = new PictureDaoJdbcImpl();
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
    public void testAddPicture() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException {
        ITable expectedTable = DBUnitUtils.setExpected(ADD_PICTURE_XML_FILE_PATH, PICTURE_TABLE);

        Picture picture = new PictureImpl("C:/test.jpg", null);
        pictureDao.addPicture(picture);
        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(PICTURE_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @Test
    public void testDeletePicture() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException{
        ITable expectedTable = DBUnitUtils.setExpected(DELETE_PICTURE_XML_FILE_PATH, PICTURE_TABLE);

        Picture picture = new PictureImpl(20, "C:/Users/Gian/Desktop/plan.jpg", null);
        pictureDao.deletePicture(picture.getPictureId());

        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(PICTURE_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @Test
    public void testUpdatePicture() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException{
        ITable expectedTable = DBUnitUtils.setExpected(UPDATE_PICTURE_XML_FILE_PATH, PICTURE_TABLE);

        Picture picture = new PictureImpl(20, "C:/Users/plan.jpg", null);
        pictureDao.updatePicture(picture);

        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(PICTURE_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }
}
