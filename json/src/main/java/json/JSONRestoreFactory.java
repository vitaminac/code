package json;

import json.reader.JSONValue;

import java.util.HashMap;

public interface JSONRestoreFactory<T> {
    T build(HashMap<String, JSONValue> members);
}
