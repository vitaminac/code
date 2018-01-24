package testobject;

import json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class TestJSONObject implements JSONObject {
    private final char c;
    private final double d;
    private final int i;
    private TestJSONObject object = null;
    private final String s;
    private ArrayList<TestJSONObject> subObjects = new ArrayList<>();

    public TestJSONObject() {
        this.c = 'c';
        this.d = 1.23;
        this.i = 1;
        this.s = "string";
    }

    public TestJSONObject(char c, double d, int i, TestJSONObject object, String s) {
        this.c = c;
        this.d = d;
        this.i = i;
        this.object = object;
        this.s = s;
    }

    public void setSubObjects(ArrayList<TestJSONObject> subObjects) {
        this.subObjects = subObjects;
    }

    public void setObject(TestJSONObject object) {
        this.object = object;
    }

    @Override
    public int hashCode() {

        return Objects.hash(c, d, i, s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TestJSONObject))
            return false;
        TestJSONObject that = (TestJSONObject) o;
        return c == that.c && Double.compare(that.d, d) == 0 && i == that.i && Objects.equals(s, that.s);
    }
}
