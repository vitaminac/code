package json;

import json.writer.JSONObjectWriter;

import java.io.IOException;

public interface JSON {
    void writeJSON(JSONObjectWriter writer) throws IOException;
}
