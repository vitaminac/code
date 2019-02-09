package code;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SQLSchema {
    private Map<String, List<String>> headers;

    private Map<String, List<List<String>>> rows;

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public Map<String, List<List<String>>> getRows() {
        return rows;
    }

    public void setRows(Map<String, List<List<String>>> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> header : this.headers.entrySet()) {
            sb.append("CREATE TABLE ");
            sb.append(header.getKey());
            sb.append('(');
            final List<String> columns = header.getValue();
            final Iterator<String> it = columns.iterator();
            while (it.hasNext()) {
                String column = it.next();
                sb.append(column);
                sb.append(' ');
                sb.append(column.endsWith("Id") ? "INT" : "VARCHAR");
                if (it.hasNext()) {
                    sb.append(',');
                }
            }
            sb.append(')');
            sb.append(';');
            sb.append('\n');
        }
        for (Map.Entry<String, List<List<String>>> rows : this.rows.entrySet()) {
            for (List<String> row : rows.getValue()) {
                sb.append("INSERT INTO ");
                sb.append(rows.getKey());
                sb.append(" VALUES (");
                final Iterator<String> it = row.iterator();
                while (it.hasNext()) {
                    String value = it.next();
                    if (value.endsWith("Id")) {
                        sb.append(value);
                    } else {
                        sb.append('\'');
                        sb.append(value);
                        sb.append('\'');
                    }
                    if (it.hasNext()) {
                        sb.append(',');
                    }
                }
                sb.append(')');
                sb.append(';');
                sb.append('\n');
            }
        }
        return sb.toString();
    }
}
