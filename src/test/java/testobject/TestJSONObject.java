package testobject;

import json.JSONObjectSerializable;

public class TestJSONObject implements JSONObjectSerializable {
    private int i = 1;
    private double d = 1.23;
    private char c = 'c';
    private String s = "string";
    private TestJSONObject object = null;

    public void setObject(TestJSONObject object) {
        this.object = object;
    }
}
