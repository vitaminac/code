package json;

import json.reader.JSONValue;

import java.util.HashMap;

public interface JSONRestoreFactory<T extends JSON> {
    T build(HashMap<String, JSONValue> members) throws MalformedJSONInput;
}
