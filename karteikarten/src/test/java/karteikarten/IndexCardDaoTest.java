package karteikarten;

import de.kksystem.karteikarten.dao.classes.jdbc.CategoryDaoJdbcImpl;
import de.kksystem.karteikarten.dao.classes.jdbc.IndexCardDaoJdbcImpl;
import de.kksystem.karteikarten.dao.interfaces.CategoryDao;
import de.kksystem.karteikarten.dao.interfaces.IndexCardDao;
import de.kksystem.karteikarten.model.classes.CategoryImpl;
import de.kksystem.karteikarten.model.classes.IndexCardImpl;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
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

public class IndexCardDaoTest {
    private static final String DATABASE_XML_FILE_PATH = "src/test/resources/test-xml/full-export-db.xml";
    private static final String UPDATE_INDEXCARD_QUESTION_XML_FILE_PATH = "src/test/resources/test-xml/test-indexcard/expected-update-indexcard-question.xml";
    private static final String UPDATE_INDEXCARD_ANSWER_XML_FILE_PATH = "src/test/resources/test-xml/test-indexcard/expected-update-indexcard-answer.xml";
    private static final String INDEXCARD_TABLE = "Karteikarte";
    private IndexCardDao indexCardDao;

    @Before
    public void setUp() throws ClassNotFoundException, DatabaseUnitException, IOException, SQLException {
        File xmlFile = new File(DATABASE_XML_FILE_PATH);
        boolean xmlFileExists = xmlFile.exists();
        assertTrue(xmlFileExists);
        indexCardDao = new IndexCardDaoJdbcImpl();
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
    public void testUpdateIndexCardQuestion() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException{
        ITable expectedTable = DBUnitUtils.setExpected(UPDATE_INDEXCARD_QUESTION_XML_FILE_PATH, INDEXCARD_TABLE);

        IndexCard indexCard = new IndexCardImpl(13, "question", "answer", null, 41,  23);
        indexCardDao.updateQuestion(indexCard.getQuestion(), indexCard.getIndexCardId());

        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(INDEXCARD_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }

    @Test
    public void testUpdateIndexCardAnswer() throws DatabaseUnitException, ClassNotFoundException, IOException, SQLException{
        ITable expectedTable = DBUnitUtils.setExpected(UPDATE_INDEXCARD_ANSWER_XML_FILE_PATH, INDEXCARD_TABLE);

        IndexCard indexCard = new IndexCardImpl(13, "question", "answer", null, 41,  23);
        indexCardDao.updateAnswer(indexCard.getAnswer(), indexCard.getIndexCardId());

        IDatabaseConnection connection = DBUnitUtils.getDatabaseConnection();
        IDataSet actualDataSet = connection.createDataSet();
        ITable actualTable = actualDataSet.getTable(INDEXCARD_TABLE);

        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }
}
