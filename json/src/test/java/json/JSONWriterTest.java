package json;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JSONWriterTest {
    @Test
    public void booleanTest() {
        assertEquals("true", JSON.stringify(true));
    }

    @Test
    public void booleanArrayTest() {
        assertEquals("[true,false,true,true,false]", JSON.stringify(new boolean[]{true, false, true, true, false}));
    }

    @Test
    public void byteTest() {
        assertEquals("15", JSON.stringify(0xf));
    }

    @Test
    public void charTest() {
        assertEquals("\"c\"", JSON.stringify('c'));
    }

    @Test
    public void charArrayTest() {
        assertEquals("[\"a\",\"r\",\"r\",\"a\",\"y\"]", JSON.stringify(new char[]{'a', 'r', 'r', 'a', 'y'}));
    }

    @Test
    public void objectTest() {
        assertEquals("{\"b\":73,\"c\":\"u\",\"d\":6.15,\"f\":8.6,\"i\":520,\"l\":1314,\"o\":null,\"os\":[],\"s\":5,\"str\":\"love\"}", JSON.stringify(new JSONObject()));
    }

    @Test
    public void complexObjectTest() {
        List<JSONObject> list = new ArrayList<>();
        list.add(new JSONObject());
        assertEquals("{\"b\":30,\"c\":\"I\",\"d\":3.1415,\"f\":3.14,\"i\":3,\"l\":30,\"o\":{\"b\":73,\"c\":\"u\",\"d\":6.15,\"f\":8.6,\"i\":520,\"l\":1314,\"o\":null,\"os\":[],\"s\":5,\"str\":\"love\"},\"os\":[{\"b\":73,\"c\":\"u\",\"d\":6.15,\"f\":8.6,\"i\":520,\"l\":1314,\"o\":null,\"os\":[],\"s\":5,\"str\":\"love\"}],\"s\":-1,\"str\":\"hola\"}", JSON.stringify(new JSONObject((byte) 30, "hola", 'I', 3.1415, 3.14f, 3, 30, new JSONObject(), (short) -1, list)));
    }

    @Test
    public void doubleArrayTest() {
        assertEquals("[1.1,2.1,3.1,4.1]", JSON.stringify(new double[]{1.1, 2.1, 3.1, 4.1}));
    }

    @Test
    public void writeDoubleNegative() {
        assertEquals("1.258644444E-45", JSON.stringify(1.258644444E-45));
    }

    @Test
    public void doublePositive() {
        assertEquals("1.258644444E45", JSON.stringify(1.258644444E45));
    }

    @Test
    public void writeFloat() {
        assertEquals("1.2E10", JSON.stringify(1.2E10f));
    }

    @Test
    public void floutArrayTest() {
        assertEquals("[1.1,2.1,3.1,4.1]", JSON.stringify(new float[]{1.1f, 2.1f, 3.1f, 4.1f}));
    }

    @Test
    public void intTest() {
        assertEquals("123456", JSON.stringify(123456));
    }

    @Test
    public void longArrayTest() {
        assertEquals("[1,2,3,4]", JSON.stringify(new long[]{1, 2, 3, 4}));
    }

    @Test
    public void nullTest() {
        assertEquals("null", JSON.stringify(null));
    }

    @Test
    public void shortTest() {
        assertEquals("1234", JSON.stringify((short) 1234));
    }

    @Test
    public void writeShortArray() {
        assertEquals("[1,2,3,4]", JSON.stringify(new short[]{1, 2, 3, 4}));
    }

    @Test
    public void writeString() {
        assertEquals("\"string\"", JSON.stringify("string"));
    }

    @Test
    public void writeStringArray() {
        assertEquals("[\"123\",\"456\",\"789\"]", JSON.stringify(new String[]{"123", "456", "789"}));
    }

    @Test
    public void writeStringWithSpecialSymbol() {
        assertEquals("\"\\\"\\\\/\\b\\f\\n\\r\\t\"", JSON.stringify("\"\\/\b\f\n\r\t"));
    }

    @Test
    public void byteArrayTest() {
        assertEquals("\"aGVsbG8=\"", JSON.stringify("hello".getBytes()));
    }

    @Test
    public void inputstreamTest() {
        assertEquals("\"aGVsbG8=\"", JSON.stringify(new ByteInputStream("hello".getBytes(), 5)));
    }
}