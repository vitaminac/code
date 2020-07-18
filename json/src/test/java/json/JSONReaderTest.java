package json;

import json.reader.JSONReader;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class JSONReaderTest {
    @Test
    public void booleanTest() throws Exception {
        assertTrue((Boolean) JSON.parse("true"));
        assertFalse((Boolean) JSON.parse("false"));
    }

    @Test
    public void readComplexObject() throws Exception {
        Reader reader = this
                .getUnderlyingReader("{\"b\":30,\"str\":\"hola\",\"c\":\"I\",\"d\":3.1415,\"f\":3.14,\"i\":3,\"l\":30,\"o\":{\"b\":73,\"str\":\"love\",\"c\":\"u\",\"d\":6.15,\"f\":8.6,\"i\":520,\"l\":1314,\"o\":null,\"s\":5,\"os\":[]},\"s\":-1,\"os\":[{\"b\":73,\"str\":\"love\",\"c\":\"u\",\"d\":6.15,\"f\":8.6,\"i\":520,\"l\":1314,\"o\":null,\"s\":5,\"os\":[]}]}");
        final JSONReader jsonReader = new JSONReader(reader);
        List<JSONObject> list = new ArrayList<>();
        list.add(new JSONObject());
        final JSONObject object = jsonReader.getObject(new JSONObjectFactory());
        assertEquals(new JSONObject((byte) 30, "hola", 'I', 3.1415, 3.14f, 3, 30, new JSONObject(), (short) -1, list), object);
    }

    @Test
    public void numberTest() {
        assertEquals(BigDecimal.valueOf(123), JSON.parse("123"));
        assertEquals(BigDecimal.valueOf(123), JSON.parse("+123"));
        assertEquals(BigDecimal.valueOf(-123), JSON.parse("-123"));
        assertEquals(BigDecimal.valueOf(123.9), JSON.parse("123.9"));
        assertEquals(BigDecimal.valueOf(123.9), JSON.parse("+123.9"));
        assertEquals(BigDecimal.valueOf(-123.9), JSON.parse("-123.9"));
        assertEquals(0, BigDecimal.valueOf(12390000d).compareTo((BigDecimal) JSON.parse("123.9E5")));
        assertEquals(0, BigDecimal.valueOf(12390000d).compareTo((BigDecimal) JSON.parse("+123.9E5")));
        assertEquals(0, BigDecimal.valueOf(-12390000d).compareTo((BigDecimal) JSON.parse("-123.9E5")));
        assertEquals(0, BigDecimal.valueOf(12390000d).compareTo((BigDecimal) JSON.parse("123.9e5")));
        assertEquals(0, BigDecimal.valueOf(12390000d).compareTo((BigDecimal) JSON.parse("+123.9e5")));
        assertEquals(0, BigDecimal.valueOf(-12390000d).compareTo((BigDecimal) JSON.parse("-123.9e5")));
        assertEquals(0, BigDecimal.valueOf(1.258644444E45).compareTo((BigDecimal) JSON.parse("1.258644444E45")));
        assertEquals(0, BigDecimal.valueOf(0.001).compareTo((BigDecimal) JSON.parse("1e-3")));
    }

    @Test
    public void readObject() throws Exception {
        Reader reader = this
                .getUnderlyingReader("{\"b\":73,\"str\":\"love\",\"c\":\"u\",\"d\":6.15,\"f\":8.6,\"i\":520," + "\"l\":1314,\"o\":null,\"s\":5,\"os\":[]}");
        final JSONReader jsonReader = new JSONReader(reader);
        final JSONObject object = jsonReader.getObject(new JSONObjectFactory());
        assertEquals(new JSONObject(), object);
    }

    @Test
    public void stringTest() {
        assertEquals("str\\\"ing", JSON.parse("\"str\\\\\\\"ing\""));
    }

    @Test
    public void nullTest() {
        assertNull(JSON.parse("null"));
    }

    private Reader getUnderlyingReader(String str) {
        return new StringReader(str);
    }
}