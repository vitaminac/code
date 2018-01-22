package json;

import java.io.IOException;
import java.util.Objects;

public class JSONValue implements JSONSerializable {
    private final Type type;
    private final Object value;

    public JSONValue(String value) {
        if (value == null) {
            this.type = Type.null_;
        } else {
            this.type = Type.string;
        }
        this.value = value;
    }

    public JSONValue(char value) {
        this.type = Type.string;
        this.value = String.valueOf(value);
    }

    public JSONValue() {
        this.type = Type.null_;
        this.value = null;
    }

    public JSONValue(JSONObjectSerializable value) {
        if (value == null) {
            this.type = Type.null_;
        } else {
            this.type = Type.object;
        }
        this.value = value;
    }

    public JSONValue(boolean value) {
        this.type = Type.bool;
        this.value = value;
    }

    public JSONValue(int value) {
        this.type = Type.number;
        this.value = value;
    }

    public JSONValue(double value) {
        this.type = Type.number;
        this.value = value;
    }

    public static JSONValue build(Object o) throws ClassIsNotJSONSerializableException {
        if (o == null) {
            return new JSONValue();
        } else if (o instanceof String) {
            return new JSONValue((String) o);
        } else if (o instanceof Character) {
            return new JSONValue((Character) o);
        } else if (o instanceof JSONObjectSerializable) {
            return new JSONValue((JSONObjectSerializable) o);
        } else if (o instanceof Boolean) {
            return new JSONValue((Boolean) o);
        } else if (o instanceof Integer) {
            return new JSONValue((Integer) o);
        } else if (o instanceof Double) {
            return new JSONValue((Double) o);
        }
        throw new ClassIsNotJSONSerializableException();
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof JSONValue))
            return false;
        JSONValue jsonValue = (JSONValue) o;
        return (((this.type == Type.object) || (this.type == Type.array)) && (type == jsonValue.type)) && Objects.equals(value, jsonValue.value);
    }

    @Override
    public void writeJSON(JSONWriter jsonWriter) throws IOException {
        switch (this.type) {
            case null_:
                jsonWriter.writeOutput("null");
                return;
            case string:
                jsonWriter.writeOutput("\"" + this.value + "\"");
                return;
            case object:
                ((JSONObjectSerializable) this.value).writeJSON(jsonWriter);
                return;
            case bool:
                jsonWriter.writeOutput(String.valueOf(this.value));
                return;
            case number:
                jsonWriter.writeOutput(String.valueOf(this.value));
                return;
        }
        throw new RuntimeException(this.value.toString() + " is incompatible type");
    }

    private enum Type {
        string,
        number,
        object,
        array,
        bool,
        null_
    }
}
