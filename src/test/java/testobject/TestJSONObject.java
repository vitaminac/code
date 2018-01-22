package testobject;

import json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class TestJSONObject implements JSONObject {
    private char c = 'c';
    private double d = 1.23;
    private int i = 1;
    private TestJSONObject object = null;
    private String s = "string";
    private ArrayList<TestJSONObject> subObjects = new ArrayList<>();

    public void setI(int i) {
        this.i = i;
    }

    public void setD(double d) {
        this.d = d;
    }

    public void setC(char c) {
        this.c = c;
    }

    public void setS(String s) {
        this.s = s;
    }

    public void setSubObjects(ArrayList<TestJSONObject> subObjects) {
        this.subObjects = subObjects;
    }

    public void setObject(TestJSONObject object) {
        this.object = object;
    }
}
