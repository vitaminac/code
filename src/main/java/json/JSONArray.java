package json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONArray implements JSONSerializable {
    private final List<JSONValue> elements = new ArrayList<>();

    public JSONArray(boolean[] array) {
        this.addAll(array);
    }

    public JSONArray(short[] array) {
        this.addAll(array);
    }

    public JSONArray(int[] array) {
        this.addAll(array);
    }

    public JSONArray(long[] array) {
        this.addAll(array);
    }

    public JSONArray(double[] array) {
        this.addAll(array);
    }

    public JSONArray(float[] array) {
        this.addAll(array);
    }

    public JSONArray(char[] array) {
        this.addAll(array);
    }

    public JSONArray(String[] array) {
        this.addAll(array);
    }

    public void addAll(boolean[] array) {
        for (boolean anArray : array) {
            this.elements.add(new JSONValue(anArray));
        }
    }

    public void addAll(short[] array) {
        for (int anArray : array) {
            this.elements.add(new JSONValue(anArray));
        }
    }

    public void addAll(int[] array) {
        for (int anArray : array) {
            this.elements.add(new JSONValue(anArray));
        }
    }

    public void addAll(char[] array) {
        for (char anArray : array) {
            this.elements.add(new JSONValue(anArray));
        }
    }

    public void addAll(long[] array) {
        for (long anArray : array) {
            this.elements.add(new JSONValue(anArray));
        }
    }

    public void addAll(float[] array) {
        for (float anArray : array) {
            this.elements.add(new JSONValue(anArray));
        }
    }

    public void addAll(double[] array) {
        for (double anArray : array) {
            this.elements.add(new JSONValue(anArray));
        }
    }

    public void addAll(String[] array) {
        for (String anArray : array) {
            this.elements.add(new JSONValue(anArray));
        }
    }

    @Override
    public void writeJSON(JSONWriter jsonWriter) throws IOException {
        jsonWriter.writeOutput("[");
        for (int i = 0; i < this.elements.size(); i++) {
            if (i > 0) {
                jsonWriter.writeOutput(",");
            }
            jsonWriter.pushLocation(String.valueOf(i));
            jsonWriter.writeJSONValue(this.elements.get(i));
            jsonWriter.popLocation();
        }
        jsonWriter.writeOutput("]");
    }
}
