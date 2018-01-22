package testobject;

import json.JSONObject;

import java.util.ArrayList;

public class TestJSONObject implements JSONObject {
    private int i = 1;
    private double d = 1.23;
    private char c = 'c';
    private String s = "string";
    private TestJSONObject object = null;
    private ArrayList<TestJSONObject> subObjects = new ArrayList<>();

    public void setObject(TestJSONObject object) {
        this.object = object;
    }
}
