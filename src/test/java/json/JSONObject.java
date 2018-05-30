package json;

import json.writer.JSONObjectWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JSONObject implements JSON {
    private final byte b;
    private final char c;
    private final double d;
    private final float f;
    private final int i;
    private final long l;
    private final JSON o;
    private final List<JSONObject> os;
    private final short s;
    private final String str;

    public JSONObject() {
        this((byte) 'I', "love", 'u', 6.15, 8.6f, 520, 1314, null, (short) 5, new ArrayList<>());
    }

    public JSONObject(byte b, String str, char c, double d, float f, int i, long l, JSON o, short s, List<JSONObject> os) {
        this.b = b;
        this.c = c;
        this.d = d;
        this.f = f;
        this.i = i;
        this.l = l;
        this.o = o;
        this.s = s;
        this.str = str;
        this.os = os;
    }

    @Override
    public int hashCode() {

        return Objects.hash(b, c, d, f, i, l, o, s, str, os);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JSONObject that = (JSONObject) o;
        return b == that.b && c == that.c && Double.compare(that.d, d) == 0 && Float
                .compare(that.f, f) == 0 && i == that.i && l == that.l && s == that.s && Objects
                .equals(this.o, that.o) && Objects.equals(str, that.str) && Objects.equals(os, that.os);
    }

    @Override
    public String toString() {
        return "JSONObject{" + "b=" + b + ", c=" + c + ", d=" + d + ", f=" + f + ", i=" + i + ", l=" + l + ", o=" + o + ", s=" + s + ", str='" + str + '\'' + ", os=" + os + '}';
    }

    @Override
    public void writeJSON(JSONObjectWriter writer) throws IOException {
        writer.write("b", b);
        writer.write("str", str);
        writer.write("c", c);
        writer.write("d", d);
        writer.write("f", f);
        writer.write("i", i);
        writer.write("l", l);
        writer.write("o", o);
        writer.write("s", s);
        writer.write("os", os);
    }
}
