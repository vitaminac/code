package json;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JSONObjectTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void putByte() {
        JSONObject11 jsonObject = new JSONObject11();
        byte b = 0xf;
        jsonObject.put("key", b);
        assertEquals("{" + "\"key\":15" + "}", jsonObject.toString());
    }

    @Test
    public void putInteger() {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", 1);
        assertEquals("{" + "\"key\":1" + "}", jsonObject.toString());
    }

    @Test
    public void putShort() {
        JSONObject11 jsonObject = new JSONObject11();
        short value = 2;
        jsonObject.put("key", value);
        assertEquals("{" + "\"key\":2" + "}", jsonObject.toString());
    }

    @Test
    public void putFloat() {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", 2.0f);
        assertEquals("{" + "\"key\":2.0" + "}", jsonObject.toString());
    }

    @Test
    public void putDouble() {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", 1.0);
        assertEquals("{" + "\"key\":1.0" + "}", jsonObject.toString());
    }

    @Test
    public void putBoolean() {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", true);
        assertEquals("{" + "\"key\":true" + "}", jsonObject.toString());
    }

    @Test
    public void putChar() {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", 'a');
        assertEquals("{" + "\"key\":\"a\"" + "}", jsonObject.toString());
    }

    @Test
    public void putString() {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", "value");
        assertEquals("{" + "\"key\":\"value\"" + "}", jsonObject.toString());
    }

    @Test
    public void putJSONObject() throws Exception {
        JSONObject11 value = new JSONObject11();
        value.put("key0", false);
        value.put("key1", 1);
        value.put("key2", 2.5);
        value.put("key3", '3');
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", value);
        String json = jsonObject.toString();
        assertEquals("{\"key\":{\"key0\":false,\"key1\":1,\"key2\":2.5,\"key3\":\"3\"}}", json);
        JSONObject11 otherJsonObject = new JSONObject11();
        otherJsonObject.deserializeFromJSON(json);
        assertEquals(jsonObject.toString(), otherJsonObject.toString());
        assertEquals(jsonObject, otherJsonObject);
        value.put("key4", "4");
        json = jsonObject.toString();
        assertEquals("{\"key\":{\"key0\":false,\"key1\":1,\"key2\":2.5,\"key3\":\"3\",\"key4\":\"4\"}}", json);
        otherJsonObject = new JSONObject11();
        otherJsonObject.deserializeFromJSON(json);
        assertEquals(jsonObject.toString(), otherJsonObject.toString());
        assertEquals(jsonObject, otherJsonObject);
    }

    @Test
    public void putArrayInteger() {
        JSONObject11 jsonObject = new JSONObject11();
        int[] array = new int[]{1, 2, 3, 4};
        jsonObject.putArray("key", new JSONArray1(array));
        assertEquals("{" + "\"key\":[1,2,3,4]" + "}", jsonObject.toString());
        array[3] = 5;
        assertEquals("{" + "\"key\":[1,2,3,5]" + "}", jsonObject.toString());
    }

    @Test
    public void putArrayLong() {
        JSONObject11 jsonObject = new JSONObject11();
        long[] array = new long[]{1, 2, 3, 4};
        jsonObject.putArray("key", new JSONArray1(array));
        assertEquals("{" + "\"key\":[1,2,3,4]" + "}", jsonObject.toString());
        array[3] = 5;
        assertEquals("{" + "\"key\":[1,2,3,5]" + "}", jsonObject.toString());
    }

    @Test
    public void putArrayShort() {
        JSONObject11 jsonObject = new JSONObject11();
        short[] array = new short[]{1, 2, 3, 4};
        jsonObject.putArray("key", new JSONArray1(array));
        assertEquals("{" + "\"key\":[1,2,3,4]" + "}", jsonObject.toString());
        array[3] = 5;
        assertEquals("{" + "\"key\":[1,2,3,5]" + "}", jsonObject.toString());
    }

    @Test
    public void putArrayFloat() {
        JSONObject11 jsonObject = new JSONObject11();
        float[] array = new float[]{1.1f, 2.1f, 3.1f, 4.1f};
        jsonObject.putArray("key", new JSONArray1(array));
        assertEquals("{" + "\"key\":[1.1,2.1,3.1,4.1]" + "}", jsonObject.toString());
        array[3] = 5.1f;
        assertEquals("{" + "\"key\":[1.1,2.1,3.1,5.1]" + "}", jsonObject.toString());
    }

    @Test
    public void putArrayDouble() {
        JSONObject11 jsonObject = new JSONObject11();
        double[] array = new double[]{1.1, 2.1, 3.1, 4.1};
        jsonObject.putArray("key", new JSONArray1(array));
        assertEquals("{" + "\"key\":[1.1,2.1,3.1,4.1]" + "}", jsonObject.toString());
        array[3] = 5.1;
        assertEquals("{" + "\"key\":[1.1,2.1,3.1,5.1]" + "}", jsonObject.toString());
    }

    @Test
    public void putArrayByte() {
        String string = "balabala";
        byte[] array = string.getBytes();
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.putArray("key", new JSONArray1(array));
        assertEquals("{" + "\"key\":" + String.join(",", Arrays.toString(array).split(", ")) + "}", jsonObject.toString());
    }

    @Test
    public void putArrayBoolean() {
        boolean[] array = new boolean[]{true, false, true, true, false};
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.putArray("key", new JSONArray1(array));
        assertEquals("{\"key\":[true,false,true,true,false]}", jsonObject.toString());
    }

    @Test
    public void putArrayChar() {
        String string = "balabala";
        char[] array = string.toCharArray();
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.putArray("key", new JSONArray1(array));
        assertEquals("{\"key\":[\"b\",\"a\",\"l\",\"a\",\"b\",\"a\",\"l\",\"a\"]}", jsonObject.toString());
        array[3] = 'b';
        assertEquals("{\"key\":[\"b\",\"a\",\"l\",\"b\",\"b\",\"a\",\"l\",\"a\"]}", jsonObject.toString());
    }

    @Test
    public void putArraString() {
        String[] array = new String[]{"123", "456", "789"};
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.putArray("key", new JSONArray1(array));
        assertEquals("{\"key\":[\"123\",\"456\",\"789\"]}", jsonObject.toString());
    }

    @Test
    public void putArrayJSONSerializable() {
        JSONObject11 value1 = new JSONObject11();
        value1.put("key0", true);
        value1.put("key1", 1);
        value1.put("key2", 1.1);
        value1.put("key3", '1');
        value1.put("key4", "1");
        JSONObject11 value2 = new JSONObject11();
        value2.put("key0", false);
        value2.put("key1", 2);
        value2.put("key2", 2.2);
        value2.put("key3", '2');
        value2.put("key4", "2");
        JSONObject11 jsonObject = new JSONObject11();
        JSONSerializable1[] array = new JSONSerializable1[]{value1, value2};
        jsonObject.putArray("key", new JSONArray1(array));
        assertEquals("{\"key\":[{\"key0\":true,\"key1\":1,\"key2\":1.1,\"key3\":\"1\",\"key4\":\"1\"},{\"key0\":false,\"key1\":2,\"key2\":2.2,\"key3\":\"2\",\"key4\":\"2\"}]}", jsonObject.toString());
    }

    @Test
    public void equalsTest() {
        JSONObject11 jsonObject1 = new JSONObject11();
        JSONObject11 jsonObject2 = new JSONObject11();
        assertEquals(jsonObject1, jsonObject2);
    }

    @Test(expected = JSONKeyValueUndefined.class)
    public void getStringWithWrongType() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", 1);
        Object value = jsonObject.getString("key");
    }

    @Test(expected = JSONKeyValueUndefined.class)
    public void getStringWithoutElemets() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.getString("key");
    }

    @Test(expected = JSONKeyValueUndefined.class)
    public void getStringWithNullKey() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.getString(null);
    }

    @Test
    public void getString() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        String string = "string";
        jsonObject.put("key", string);
        assertEquals(string, jsonObject.getString("key"));
    }

    @Test
    public void getDoubleWithWrongTypeInteger() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", 1);
        Double value = jsonObject.getDouble("key");
        assertEquals(1.0, value, 0);
    }

    @Test(expected = JSONKeyValueUndefined.class)
    public void getDoubleWithWrongTypeBoolean() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", true);
        jsonObject.getDouble("key");
    }

    @Test(expected = JSONKeyValueUndefined.class)
    public void getDoubleWithWrongTypeString() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", "1");
        Object value = jsonObject.getDouble("key");
    }

    @Test(expected = JSONKeyValueUndefined.class)
    public void getDoubleWithoutElemets() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.getDouble("key");
    }

    @Test(expected = JSONKeyValueUndefined.class)
    public void getDoubleWithNullKey() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.getDouble(null);
    }

    @Test
    public void getDouble() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", 2.1);
        assertEquals(2.1, jsonObject.getDouble("key"), 0);
    }

    @Test(expected = JSONKeyValueUndefined.class)
    public void getLongWithWrongTypeDouble() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", 1.1);
        jsonObject.getLong("key");
    }

    @Test
    public void getLongWithWrongTypeFloorDouble() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", 1.0);
        jsonObject.getLong("key");
    }

    @Test(expected = JSONKeyValueUndefined.class)
    public void getLongWithWrongTypeBoolean() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", true);
        jsonObject.getLong("key");
    }

    @Test(expected = JSONKeyValueUndefined.class)
    public void getLongWithWrongTypeString() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", "1");
        Object value = jsonObject.getLong("key");
    }

    @Test(expected = JSONKeyValueUndefined.class)
    public void getLongWithoutElemets() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.getLong("key");
    }

    @Test(expected = JSONKeyValueUndefined.class)
    public void getLongWithNullKey() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.getLong(null);
    }

    @Test
    public void getLong() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", 2);
        assertEquals(2, jsonObject.getLong("key"));
    }

    @Test
    public void getJSONObject() throws Exception {
        JSONObject11 value1 = new JSONObject11();
        value1.put("key0", true);
        value1.put("key1", 1);
        value1.put("key2", 1.1);
        value1.put("key3", '1');
        value1.put("key4", "1");
        JSONObject11 value2 = new JSONObject11();
        value2.put("key0", false);
        value2.put("key1", 2);
        value2.put("key2", 2.2);
        value2.put("key3", '2');
        value2.put("key4", "2");
        JSONObject11 value = new JSONObject11();
        value.put("key1", value1);
        value.put("key2", value2);
        JSONObject11 jsonObject = new JSONObject11();
        jsonObject.put("key", value);

        // then get
        JSONObject11 getValue = jsonObject.getJSONObject("key");
        assertEquals(value, getValue);
        JSONObject11 getValue1 = getValue.getJSONObject("key1");
        assertEquals(value1, getValue1);
        JSONObject11 getValue2 = getValue.getJSONObject("key2");
        assertEquals(value2, getValue2);

        // serialize and then deserialize
        String json = jsonObject.serializeToJSON();
        JSONObject11 deserializedObject = new JSONObject11();
        deserializedObject.deserializeFromJSON(json);
        assertEquals(jsonObject, deserializedObject);
        JSONObject11 deserializedObjectGetValue = deserializedObject.getJSONObject("key");
        assertEquals(value, deserializedObjectGetValue);
        JSONObject11 deserializedObjectGetValue1 = deserializedObjectGetValue.getJSONObject("key1");
        assertEquals(value1, deserializedObjectGetValue1);
        JSONObject11 deserializedObjectGetValue2 = deserializedObjectGetValue.getJSONObject("key2");
        assertEquals(value2, deserializedObjectGetValue2);
    }

    @Test
    public void putRecursiveJSONObject() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        JSONObject11 jsonObjectCircularRef = new JSONObject11();
        jsonObject.put("json", jsonObjectCircularRef);
        jsonObjectCircularRef.put("json", jsonObject);
        assertEquals("{\"json\":{\"json\":#}}", jsonObject.serializeToJSON());
    }

    @Test
    public void putSelfRecursiveJSONObject() throws Exception {
        JSONObject11 jsonObject = new JSONObject11();
        JSONObject11 jsonObjectSelfRef = new JSONObject11();
        jsonObject.put("json", jsonObjectSelfRef);
        jsonObjectSelfRef.put("json", jsonObjectSelfRef);
        assertEquals("{\"json\":{\"json\":#json}}", jsonObject.serializeToJSON());
    }
}