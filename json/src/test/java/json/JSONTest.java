package json;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;

public class JSONTest {
    @Test
    public void stringifyNull() {
        assertEquals("null", JSON.stringify(null));
    }

    @Test
    public void stringifyBoolean() {
        assertEquals("true", JSON.stringify(true));
        assertEquals("true", JSON.stringify(Boolean.TRUE));
        assertEquals("false", JSON.stringify(false));
        assertEquals("false", JSON.stringify(Boolean.FALSE));
    }

    @Test
    public void stringifyByte() {
        assertEquals("15", JSON.stringify(0xf));
    }

    @Test
    public void stringifyChar() {
        assertEquals("\"c\"", JSON.stringify('c'));
    }

    @Test
    public void stringifyShort() {
        assertEquals("1234", JSON.stringify((short) 1234));
    }

    @Test
    public void stringifyInt() {
        assertEquals("123456", JSON.stringify(123456));
    }

    @Test
    public void stringifyFloat() {
        assertEquals("1.2E10", JSON.stringify(1.2E10f));
    }

    @Test
    public void stringifyLong() {
        assertEquals("1234567890123456789", JSON.stringify(1234567890123456789L));
    }

    @Test
    public void stringifyNegativeDouble() {
        assertEquals("1.258644444E-45", JSON.stringify(1.258644444E-45));
    }

    @Test
    public void stringifyPositiveDouble() {
        assertEquals("1.258644444E45", JSON.stringify(1.258644444E45));
    }

    @Test
    public void stringifyString() {
        assertEquals("\"string\"", JSON.stringify("string"));
    }

    @Test
    public void stringifyStringWithSpecialSymbol() {
        assertEquals("\"\\\"\\\\/\\b\\f\\n\\r\\t\"", JSON.stringify("\"\\/\b\f\n\r\t"));
    }

    @Test
    public void stringifyInputStream() {
        assertEquals("\"aGVsbG8=\"", JSON.stringify(new ByteArrayInputStream("hello".getBytes())));
    }

    @Test
    public void stringifyMap() {
        assertEquals("{\"a\":1,\"b\":\"str\",\"c\":[]}", JSON.stringify(new LinkedHashMap<>() {{
            this.put("a", 1);
            this.put("b", "str");
            this.put("c", new int[0]);
        }}));
    }

    @Test
    public void stringifySimpleObject() {
        assertEquals("{\"b\":73,\"c\":\"u\",\"d\":6.15,\"f\":8.6,\"i\":520,\"l\":1314,\"o\":null,\"os\":[],\"s\":5,\"str\":\"love\"}", JSON.stringify(new JSONObject()));
    }

    @Test
    public void stringifyNestedObject() {
        List<JSONObject> list = new ArrayList<>();
        list.add(new JSONObject());
        assertEquals("{\"b\":30,\"c\":\"I\",\"d\":3.1415,\"f\":3.14,\"i\":3,\"l\":30,\"o\":{\"b\":73,\"c\":\"u\",\"d\":6.15,\"f\":8.6,\"i\":520,\"l\":1314,\"o\":null,\"os\":[],\"s\":5,\"str\":\"love\"},\"os\":[{\"b\":73,\"c\":\"u\",\"d\":6.15,\"f\":8.6,\"i\":520,\"l\":1314,\"o\":null,\"os\":[],\"s\":5,\"str\":\"love\"}],\"s\":-1,\"str\":\"hola\"}",
                JSON.stringify(new JSONObject((byte) 30, "hola", 'I', 3.1415, 3.14f, 3, 30, new JSONObject(), (short) -1, list)));
    }

    @Test
    public void stringifyBooleanArray() {
        assertEquals("[true,false,true,true,false]", JSON.stringify(new boolean[]{true, false, true, true, false}));
    }

    @Test
    public void stringifyByteArray() {
        assertEquals("\"aGVsbG8=\"", JSON.stringify("hello".getBytes()));
    }

    @Test
    public void stringifyCharArray() {
        assertEquals("\"array\"", JSON.stringify(new char[]{'a', 'r', 'r', 'a', 'y'}));
    }

    @Test
    public void stringifyShortArray() {
        assertEquals("[1,2,3,4]", JSON.stringify(new short[]{1, 2, 3, 4}));
    }

    @Test
    public void stringifyFloatArray() {
        assertEquals("[1.1,2.1,3.1,4.1]", JSON.stringify(new float[]{1.1f, 2.1f, 3.1f, 4.1f}));
    }

    @Test
    public void stringifyLongArray() {
        assertEquals("[1,2,3,4]", JSON.stringify(new long[]{1, 2, 3, 4}));
    }

    @Test
    public void stringifyDoubleArray() {
        assertEquals("[1.1,2.1,3.1,4.1]", JSON.stringify(new double[]{1.1, 2.1, 3.1, 4.1}));
    }

    @Test
    public void stringifyStringArray() {
        assertEquals("[\"123\",\"456\",\"789\"]", JSON.stringify(new String[]{"123", "456", "789"}));
    }

    @Test(expected = UnsupportedJsonSerializationException.class)
    public void stringifyUnsupportedObject() {
        JSON.stringify(new StringBuilder());
    }

    @Test
    public void parseNull() {
        assertNull(JSON.parse("null", Object.class));
    }

    @Test
    public void parseBoolean() {
        assertTrue(JSON.parse("true", Boolean.class));
        assertFalse(JSON.parse("false", Boolean.class));
        assertTrue(JSON.parse("\t  \n \r true\r \n    \t", Boolean.class));
        assertFalse(JSON.parse("\t \n \r false\r \n    \t", Boolean.class));
    }

    @Test
    public void parseNumber() {
        assertEquals(BigDecimal.valueOf(123), JSON.parse("123", BigDecimal.class));
        assertEquals(BigDecimal.valueOf(123), JSON.parse("+123", BigDecimal.class));
        assertEquals(BigDecimal.valueOf(-123), JSON.parse("-123", BigDecimal.class));
        assertEquals(BigDecimal.valueOf(123.9), JSON.parse("123.9", BigDecimal.class));
        assertEquals(BigDecimal.valueOf(123.9), JSON.parse("+123.9", BigDecimal.class));
        assertEquals(BigDecimal.valueOf(-123.9), JSON.parse("-123.9", BigDecimal.class));
        assertEquals(0, BigDecimal.valueOf(0.001).compareTo((BigDecimal) JSON.parse("1e-3")));
        assertEquals(0, BigDecimal.valueOf(12390000d).compareTo(JSON.parse("123.9E5", BigDecimal.class)));
        assertEquals(0, BigDecimal.valueOf(12390000d).compareTo(JSON.parse("+123.9E5", BigDecimal.class)));
        assertEquals(0, BigDecimal.valueOf(-12390000d).compareTo(JSON.parse("-123.9E5", BigDecimal.class)));
        assertEquals(0, BigDecimal.valueOf(12390000d).compareTo(JSON.parse("123.9e5", BigDecimal.class)));
        assertEquals(0, BigDecimal.valueOf(12390000d).compareTo(JSON.parse("+123.9e5", BigDecimal.class)));
        assertEquals(0, BigDecimal.valueOf(-12390000d).compareTo(JSON.parse("-123.9e5", BigDecimal.class)));
        assertEquals(0, BigDecimal.valueOf(1.258644444E45).compareTo(JSON.parse("1.258644444E45", BigDecimal.class)));
    }

    @Test
    public void parseString() {
        assertEquals("str\\\"ing", JSON.parse("\"str\\\\\\\"ing\"", String.class));
    }
}