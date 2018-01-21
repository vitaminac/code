package json;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public interface JSONObjectSerializable11 extends JSONSerializable1 {
    default void deserializeFromJSONFile(@NotNull String path) throws DeserializationException {
        StringBuilder bd = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.lines().forEach(bd::append);
            this.deserializeFromJSON(bd.toString());
        } catch (IOException e) {
            throw new DeserializationException(e);
        }
    }

    void deserializeFromJSONObject(@NotNull JSONObject11 json) throws DeserializationException;

    default void serializeToJSONFile(@NotNull String path) throws SerializationException {
        File file = new File(path);
        if (!file.exists()) {
            try {
                File parentDirectory = file.getParentFile();
                if (parentDirectory != null) {
                    parentDirectory.mkdir();
                }
                file.createNewFile();
            } catch (IOException e) {
                throw new SerializationException(e);
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(this.serializeToJSON());
        } catch (IOException e) {
            e.printStackTrace();
            throw new SerializationException(e);
        }
    }

    default String serializeToJSON() throws SerializationException {
        return this.serializeToJSONObject().serializeToJSON();
    }

    default void deserializeFromJSON(@NotNull String json) throws DeserializationException {
        try {
            this.deserializeFromJSONObject(JSONObject11.parse(json));
        } catch (ParseException e) {
            throw new DeserializationException(e);
        }
    }

    @NotNull JSONObject11 serializeToJSONObject() throws SerializationException;
}
