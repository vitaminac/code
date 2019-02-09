package code;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SQLResult {
    private List<String> headers;
    private List<List<String>> values;

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<List<String>> getValues() {
        return values;
    }

    public void setValues(List<List<String>> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SQLResult sqlResult = (SQLResult) o;
        return Objects.equals(
                this.getHeaders().stream().map(String::toUpperCase).collect(Collectors.toList()),
                sqlResult.getHeaders().stream().map(String::toUpperCase).collect(Collectors.toList())) &&
                Objects.equals(getValues(), sqlResult.getValues());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeaders(), getValues());
    }

    @Override
    public String toString() {
        return "SQLResult{" +
                "headers=" + headers +
                ", values=" + values +
                '}';
    }
}
