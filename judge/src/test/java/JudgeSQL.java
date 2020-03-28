import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.h2.jdbc.JdbcSQLException;
import org.junit.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

public final class JudgeSQL {
    private static class SQLTest {
        private File sql;
        private File testcase;
        private File output;

        public SQLTest(File sql, File testcase, File output) {
            this.sql = sql;
            this.testcase = testcase;
            this.output = output;
        }
    }

    private static class SQLTestBuilder {
        private File sql;
        private File testcase;
        private File output;

        private void sql(File sql) {
            this.sql = sql;
        }

        private void testcase(File testcase) {
            this.testcase = testcase;
        }

        private void output(File output) {
            this.output = output;
        }

        private SQLTest build() {
            if (sql != null && testcase != null && output != null) {
                final SQLTest returnValue = new SQLTest(this.sql, this.testcase, this.output);
                this.sql = null;
                this.testcase = null;
                this.output = null;
                return returnValue;
            } else {
                return null;
            }
        }
    }

    private static String readAllString(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] buffer = new char[1024];
        int len;
        while ((len = reader.read(buffer)) >= 0) {
            sb.append(buffer, 0, len);
        }
        return sb.toString();
    }

    private static final Gson gson = new Gson();
    private static Map<String, SQLTest> sql = new HashMap<>();

    @BeforeClass
    public static void setUpClass() throws Exception {
        Map<String, File> sql = new HashMap<>();
        for (Utils.Pair pair : Utils.getResources(JudgeSQL.class, "sql")) {
            sql.put(pair.file.getName().substring(0, pair.file.getName().length() - 4), pair.file);
        }
        Map<String, File> testcase = new HashMap<>();
        for (Utils.Pair pair : Utils.getResources(JudgeSQL.class, "json")) {
            testcase.put(pair.file.getName().substring(0, pair.file.getName().length() - 5), pair.file);
        }
        Map<String, File> output = new HashMap<>();
        for (Utils.Pair pair : Utils.getResources(JudgeSQL.class, "output.json")) {
            output.put(pair.file.getName().substring(0, pair.file.getName().length() - 12), pair.file);
        }
        SQLTestBuilder builder = new SQLTestBuilder();
        for (String name : sql.keySet()) {
            builder.sql(sql.get(name));
            builder.testcase(testcase.get(name));
            builder.output(output.get(name));
            JudgeSQL.sql.put(name, builder.build());
        }
    }

    private Connection connection;

    @Before
    public void setUp() throws Exception {
        this.connection = DriverManager.getConnection("jdbc:h2:mem:db?allowMultiQueries=true");
    }

    @After
    public void tearDown() throws Exception {
        this.connection.close();
    }

    @Test
    public void testAllSQL() throws Exception {
        for (Map.Entry<String, SQLTest> entry : sql.entrySet()) {
            if (entry.getValue() != null) {
                testSQL(entry.getValue());
            } else {
                System.out.println("\u001B[33m" + "Skipped " + entry.getKey() + "\033[0;30m");
            }
        }
    }


    public void testSQL(SQLTest sqlTest) throws Exception {
        if (sqlTest == null || sqlTest.sql == null || sqlTest.testcase == null || sqlTest.output == null) {
            System.out.println("\u001B[33m" + "Skipped " + (sqlTest == null ? "unknown source" : sqlTest.sql) + "\033[0;30m");
            return;
        }
        try (Reader sqlReader = new FileReader(sqlTest.sql);
             Reader testCaseReader = new FileReader(sqlTest.testcase);
             Reader outputReader = new FileReader(sqlTest.output)) {
            Statement statement = this.connection.createStatement();

            statement.execute("DROP ALL OBJECTS");

            String testcaseConfig = readAllString(testCaseReader);
            final SQLSchema sqlTestCase = gson.fromJson(testcaseConfig, SQLSchema.class);
            statement.execute(sqlTestCase.toString());

            Type resultType = new TypeToken<ArrayList<SQLResult>>() {
            }.getType();

            String outputConfig = readAllString(outputReader);
            ArrayList<SQLResult> expected = gson.fromJson(outputConfig, resultType);


            String SQL = readAllString(sqlReader);
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            final Iterator<String> it = Arrays.asList(SQL.split("\n\n")).iterator();
            while (it.hasNext()) {
                String sql = it.next();
                try {
                    final ResultSet rs = statement.executeQuery(sql);
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
                    while (rs.next()) {
                        sb.append('[');
                        for (int i = 1; i <= metadata.getColumnCount(); i++) {
                            if (i > 1) {
                                sb.append(',');
                            }
                            String value = rs.getString(i);
                            if (value == null) {
                                sb.append("null");
                            } else {
                                sb.append('"');
                                sb.append(value.replace("\n", "\\n"));
                                sb.append('"');
                            }
                        }
                        sb.append(']');
                        if (!rs.isLast()) {
                            sb.append(',');
                        }
                    }
                    sb.append(']');
                    sb.append('}');
                    if (it.hasNext()) {
                        sb.append(',');
                    }
                } catch (JdbcSQLException e) {
                    statement.execute(sql);
                }
            }
            sb.append(']');
            String output = sb.toString();
            ArrayList<SQLResult> current = gson.fromJson(output, resultType);
            Assert.assertEquals(gson.toJson(expected), gson.toJson(current));
        }
    }

    @Test
    public void testOne() throws Exception {
        testSQL(sql.get("Practica"));
    }
}
