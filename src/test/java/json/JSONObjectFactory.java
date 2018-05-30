package json;

import json.reader.JSONValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONObjectFactory implements JSONRestoreFactory<JSONObject> {
    @Override
    public JSONObject build(HashMap<String, JSONValue> members) throws MalformedJSONInput {
        byte b = (byte) members.get("b").getNumber();
        String str = members.get("str").getString();
        char c = members.get("c").getString().charAt(0);
        double d = members.get("d").getNumber();
        float f = (float) members.get("f").getNumber();
        int i = (int) members.get("i").getNumber();
        long l = (long) members.get("l").getNumber();
        JSONObject o = members.get("o").getObject(this);
        short s = (short) members.get("s").getNumber();
        List<JSONObject> list = new ArrayList<>();
        for (JSONValue value : members.get("os").getList()) {
            list.add(value.getObject(this));
        }
        return new JSONObject(b, str, c, d, f, i, l, o, s, list);
    }
}
