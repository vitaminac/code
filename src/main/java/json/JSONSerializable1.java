package json;

import org.jetbrains.annotations.NotNull;

interface JSONSerializable1 {
    String serializeToJSON() throws SerializationException;

    void deserializeFromJSON(@NotNull String json) throws DeserializationException;
}
