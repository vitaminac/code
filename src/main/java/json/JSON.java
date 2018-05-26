package json;

import java.io.IOException;

public interface JSON {
    void writeJSON(JSONObjectWriter writer) throws IOException;
}
