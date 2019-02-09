package code;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JudgeSQL {
    private static final Gson gson = new Gson();
    private static Map<String, File> sql = new HashMap<>();
    private static Map<String, File> testcase = new HashMap<>();
    private static Map<String, File> output = new HashMap<>();

    private Connection connection;

    @BeforeClass
    public static void setUpClass() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final List<Utils.Pair> sqlResources = Utils.getResources(JudgeSQL.class, "sql");
        final List<Utils.Pair> testcaseResources = Utils.getResources(JudgeSQL.class, "json");
        final List<Utils.Pair> outputResources = Utils.getResources(JudgeSQL.class, "output.json");
        for (Utils.Pair pair : sqlResources) {
            sql.put(pair.packageName + "." + pair.file.getName().substring(0, pair.file.getName().length() - 4), pair.file);
        }
        for (Utils.Pair pair : testcaseResources) {
            testcase.put(pair.packageName + "." + pair.file.getName().substring(0, pair.file.getName().length() - 5), pair.file);
        }
        for (Utils.Pair pair : outputResources) {
            output.put(pair.packageName + "." + pair.file.getName().substring(0, pair.file.getName().length() - 12), pair.file);
        }
    }

    @Before
    public void setUp() throws Exception {
        this.connection = DriverManager.getConnection("jdbc:h2:mem:db?allowMultiQueries=true");
    }

    @After
    public void tearDown() throws Exception {
        this.connection.close();
    }

    @Test
    public void testSQL() throws Exception {
        for (Map.Entry<String, File> entry : sql.entrySet()) {
            try (Reader sqlReader = new FileReader(entry.getValue());
                 Reader testCaseReader = new FileReader(testcase.get(entry.getKey()));
                 Reader outputReader = new FileReader(output.get(entry.getKey()))) {
                Statement statement = this.connection.createStatement();

                String testcaseConfig = IOUtils.toString(testCaseReader);
                final SQLSchema sqlTestCase = gson.fromJson(testcaseConfig, SQLSchema.class);
                statement.execute(sqlTestCase.toString());

                String outputConfig = IOUtils.toString(outputReader);
                SQLResult expected = gson.fromJson(outputConfig, SQLResult.class);


                String SQL = IOUtils.toString(sqlReader);
                final ResultSet rs = statement.executeQuery(SQL);
                StringBuilder sb = new StringBuilder();
                ResultSetMetaData metadata = rs.getMetaData();
                sb.append('{');
                sb.append("\"headers\":");
                sb.append('[');
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    if (i > 1) {
                        sb.append(',');
                    }
                    sb.append('"');
                    sb.append(metadata.getColumnName(i));
                    sb.append('"');
                }
                sb.append(']');
                sb.append(',');

                sb.append("\"values\":");
                sb.append('[');
                sb.append('[');
                while (rs.next()) {
                    for (int i = 1; i <= metadata.getColumnCount(); i++) {
                        if (i > 1) {
                            sb.append(',');
                        }
                        String value = rs.getString(i);
                        if (value == null) {
                            sb.append("null");
                        } else {
                            sb.append('"');
                            sb.append(value);
                            sb.append('"');
                        }
                    }
                }
                sb.append(']');
                sb.append(']');

                sb.append('}');

                String output = sb.toString();
                SQLResult current = gson.fromJson(output, SQLResult.class);
                assertEquals(expected, current);
            }
        }
    }
}
