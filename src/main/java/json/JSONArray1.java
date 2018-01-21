package json;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

public class JSONArray1 implements JSONSerializable1 {
    private final Object array;

    public JSONArray1(boolean[] array) {
        this.array = array;
    }

    public JSONArray1(byte[] array) {
        this.array = array;
    }

    public JSONArray1(char[] array) {
        this.array = array;
    }

    public JSONArray1(short[] array) {
        this.array = array;
    }

    public JSONArray1(int[] array) {
        this.array = array;
    }

    public JSONArray1(long[] array) {
        this.array = array;
    }

    public JSONArray1(float[] array) {
        this.array = array;
    }

    public JSONArray1(double[] array) {
        this.array = array;
    }

    public JSONArray1(String[] array) {
        this.array = array;
    }

    public JSONArray1(JSONSerializable1[] array) {
        this.array = array;
    }

    @Override
    public String toString() {
        try {
            return this.serializeToJSON();
        } catch (SerializationException e) {
            return "Malformed JSON Array";
        }
    }

    @Override
    public String serializeToJSON() throws SerializationException {
        if (this.array == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        try {
            int length = Array.getLength(array);
            for (int i = 0; i < length; i++) {
                Object value = Array.get(array, i);
                sb.append(JSONSerializable.stringify(value));
                sb.append(',');
            }
            if (length > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
        } catch (Exception e) {
            throw new SerializationException(e);
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public void deserializeFromJSON(@NotNull String json) throws DeserializationException {
        throw new DeserializationException(new NotImplement());
    }
}
