package json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class JSONObject11 implements JSONObjectSerializable11 {
    private final Map<String, Object> map;
    private JSONObject11 parent;

    public JSONObject11() {
        this.map = new TreeMap<String, Object>();
    }

    private static JSONObject11 parseFromMap(@NotNull Map<String, Object> map) {
        JSONObject11 jsonObject = new JSONObject11();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof Double) {
                Double value = (Double) entry.getValue();
                if ((value == Math.floor(value)) && !Double.isInfinite(value)) {
                    jsonObject.put(entry.getKey(), value.longValue());
                } else {
                    jsonObject.put(entry.getKey(), value);
                }
            } else if (entry.getValue() instanceof Boolean) {
                jsonObject.put(entry.getKey(), (Boolean) entry.getValue());
            } else if (entry.getValue() instanceof Map) {
                jsonObject.put(entry.getKey(), parseFromMap((Map<String, Object>) entry.getValue()));
            } else {
                jsonObject.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return jsonObject;
    }

    public static @NotNull JSONObject11 parse(@NotNull String json) throws ParseException {
        JSONObject11 jsonObject = new JSONObject11();
        try {
            jsonObject.deserializeFromJSON(json);
            return jsonObject;
        } catch (DeserializationException e) {
            throw new ParseException(e);
        }
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (obj instanceof JSONObject11) {
            JSONObject11 other = (JSONObject11) obj;
            return this.map.equals(other.map);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        try {
            return this.serializeToJSON();
        } catch (SerializationException e) {
            e.printStackTrace();
            return "Malformed JSON Object";
        }
    }

    public void put(@NotNull String key, long value) {
        this.putToMap(key, value);
    }

    public void put(@NotNull String key, double value) {
        this.putToMap(key, value);
    }

    public void put(@NotNull String key, boolean value) {
        this.putToMap(key, value);
    }

    public void put(@NotNull String key, char value) {
        this.putToMap(key, String.valueOf(value));
    }

    private void putToMap(@NotNull String key, Object value) {
        this.map.put(key, value);
    }

    public void put(@NotNull String key, JSONSerializable1 value) {
        this.putToMap(key, value);
    }

    public void put(@NotNull String key, String value) {
        this.putToMap(key, value);
    }

    public void putArray(String key, @NotNull JSONArray1 array) {
        this.putToMap(key, array);
    }

    @Override
    public void deserializeFromJSONObject(@NotNull JSONObject11 json) throws DeserializationException {
        if (json != this) {
            this.clear();
            if (json != null) {
                this.map.putAll(json.map);
            }
        }
    }

    @Override
    public String serializeToJSON() throws SerializationException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append('{');
            for (Map.Entry<String, Object> entry : this.map.entrySet()) {
                sb.append("\"").append(entry.getKey()).append("\"");
                sb.append(':');
                Object value = entry.getValue();
                sb.append(this.serializeValue(value));
                sb.append(',');
            }
            if (this.map.size() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append('}');
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SerializationException(e);
        }
    }

    @Override
    public void deserializeFromJSON(@NotNull String json) throws DeserializationException {
        try {
            Type collectionType = new TypeToken<TreeMap<String, Object>>() {
            }.getType();
            Map<String, Object> map = new Gson().fromJson(json, collectionType);
            this.deserializeFromJSONObject(parseFromMap(map));
        } catch (Exception e) {
            throw new DeserializationException(e);
        }
    }

    @Override
    public @NotNull JSONObject11 serializeToJSONObject() throws SerializationException {
        return this;
    }

    private void clear() {
        this.map.clear();
    }

    public String getString(@NotNull String key) throws JSONKeyValueUndefined {
        return (String) this.getWithType(key, String.class);
    }

    private Object getWithType(String key, Class c) throws JSONKeyValueUndefined {
        if (key == null) {
            throw new JSONKeyValueUndefined(new NullPointerException("key must not be null"));
        }
        Object value = this.map.get(key);
        if (value != null) {
            try {
                return c.cast(value);
            } catch (ClassCastException e) {
                throw new JSONKeyValueUndefined(e);
            }
        } else {
            throw new JSONKeyValueUndefined(new NullPointerException("doesnt contain such value asociate to key " + key));
        }
    }

    public long getLong(@NotNull String key) throws JSONKeyValueUndefined {
        double value = this.getDouble(key);
        if (value == Math.floor(value)) {
            return (long) value;
        } else {
            throw new JSONKeyValueUndefined(new ClassCastException("cannot cast double " + String.valueOf(value) + " to long"));
        }
    }

    public double getDouble(@NotNull String key) throws JSONKeyValueUndefined {
        try {
            return (long) this.getWithType(key, Long.class);
        } catch (JSONKeyValueUndefined jsonKeyValueUndefined) {
            return (double) this.getWithType(key, Double.class);
        }
    }

    public JSONObject11 getJSONObject(@NotNull String key) throws JSONKeyValueUndefined {
        Object object = this.getWithType(key, Object.class);
        if (object instanceof JSONObject11) {
            return (JSONObject11) object;
        } else if (object instanceof JSONObjectSerializable11) {
            try {
                return ((JSONObjectSerializable11) object).serializeToJSONObject();
            } catch (SerializationException e) {
                throw new JSONKeyValueUndefined(e);
            }
        } else {
            throw new JSONKeyValueUndefined(new ClassCastException("can not cast type " + object.getClass().getName() + " to " + this.getClass().getName()));
        }
    }

    private String serializeValue(Object value) throws SerializationException {
        if (value instanceof JSONObjectSerializable11) {
            String referenceKey = null;
            if (this.parent != null) {
                referenceKey = this.parent.getReferenceKey((JSONObjectSerializable11) value);
            }
            if (referenceKey == null) {
                JSONObject11 jsonObject = ((JSONObjectSerializable11) value).serializeToJSONObject();
                jsonObject.setParent(this);
                return jsonObject.serializeToJSON();
            } else {
                return referenceKey;
            }
        } else {
            return JSONSerializable.stringify(value);
        }
    }

    private void setParent(JSONObject11 parent) {
        this.parent = parent;
    }

    private String getReferenceKey(JSONObjectSerializable11 object) {
        if (object == this) {
            return "#";
        }
        for (Map.Entry<String, Object> entry : this.map.entrySet()) {
            if (entry.getValue() == object) {
                return "#" + entry.getKey();
            }
        }
        return null;
    }
}
