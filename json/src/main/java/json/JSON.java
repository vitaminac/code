package json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.*;

public class JSON {
    private static final Map<Class, JsonConverter> CACHE = new HashMap<>();

    public static String stringify(Object obj) {
        StringBuilder sb = new StringBuilder();
        if (obj == null) {
            sb.append("null");
        } else if (Arrays.asList(Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Number.class).contains(obj.getClass())) {
            sb.append(obj);
        } else if (obj.getClass().equals(Character.class)) {
            sb.append(stringify(String.valueOf(obj)));
        } else if (obj.getClass().equals(String.class)) {
            sb.append('\"');
            for (char c : ((String) obj).toCharArray()) {
                switch (c) {
                    case '\\':
                        sb.append("\\\\");
                        break;
                    case '"':
                        sb.append("\\\"");
                        break;
                    case '\b':
                        sb.append("\\b");
                        break;
                    case '\f':
                        sb.append("\\f");
                        break;
                    case '\n':
                        sb.append("\\n");
                        break;
                    case '\r':
                        sb.append("\\r");
                        break;
                    case '\t':
                        sb.append("\\t");
                        break;
                    default:
                        sb.append(c);
                }
            }
            sb.append('\"');
        } else if (obj.getClass().equals(char[].class)) {
            return stringify(String.valueOf((char[]) obj));
        } else if (byte[].class.equals(obj.getClass())) {
            return stringify(Base64.getEncoder().encodeToString((byte[]) obj));
        } else if (obj instanceof InputStream) {
            try {
                return stringify(((InputStream) obj).readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read input stream", e);
            }
        } else if (Iterator.class.isAssignableFrom(obj.getClass())) {
            Iterator<?> it = ((Iterator<?>) obj);
            sb.append('[');
            while (it.hasNext()) {
                sb.append(stringify(it.next()));
                if (it.hasNext()) sb.append(',');
            }
            sb.append(']');
        } else if (obj.getClass().isArray()) {
            return stringify(new Iterator<>() {
                private final int length = Array.getLength(obj);
                private int index = 0;

                @Override
                public boolean hasNext() {
                    return index < length;
                }

                @Override
                public Object next() {
                    return Array.get(obj, index++);
                }
            });
        } else if (Iterable.class.isAssignableFrom(obj.getClass())) {
            return stringify(((Iterable<?>) obj).iterator());
        } else if (Map.class.isAssignableFrom(obj.getClass())) {
            sb.append('{');
            Iterator<Map.Entry<String, ?>> it = ((Map) obj).entrySet().iterator();
            while (it.hasNext()) {
                var entry = it.next();
                sb.append(stringify(entry.getKey()));
                sb.append(':');
                sb.append(stringify(entry.getValue()));
                if (it.hasNext()) sb.append(',');
            }
            sb.append('}');
        } else if (obj.getClass().isAnnotationPresent(JsonSerializable.class)) {
            Class<?> clazz = obj.getClass();
            if (!CACHE.containsKey(obj.getClass())) {
                JsonSerializable jsonSerializable = clazz.getAnnotation(JsonSerializable.class);
                Class<? extends JsonConverter<?>> converterClazz = jsonSerializable.value();
                var supportClass = (Class<?>) ((ParameterizedType) converterClazz.getGenericSuperclass()).getActualTypeArguments()[0];
                if (!supportClass.isAssignableFrom(clazz)) {
                    throw new RuntimeException("converter doesn't support " + converterClazz.getSimpleName());
                }
                try {
                    CACHE.put(clazz, converterClazz.getConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException("Error while creating the " + clazz.getSimpleName(), e);
                }
            }
            sb.append(CACHE.get(clazz).stringify(obj));
        } else {
            throw new UnsupportedJsonSerializationException(obj.getClass());
        }
        return sb.toString();
    }

    private static <T> T parse(char[] json, int position, Class<T> clazz) {
        while (position < json.length && (json[position] == ' ' || json[position] == '\t' || json[position] == '\r' || json[position] == '\n'))
            position += 1;

        if (position == json.length) throw new UnexpectedEOFException(position);

        if (position + 4 <= json.length && json[position] == 'n' && json[position + 1] == 'u' && json[position + 2] == 'l' && json[position + 3] == 'l') {
            return null;
        } else if (clazz.equals(Boolean.class)) {
            if (position + 4 <= json.length &&
                    json[position] == 't' &&
                    json[position + 1] == 'r' &&
                    json[position + 2] == 'u' &&
                    json[position + 3] == 'e')
                return (T) Boolean.TRUE;
            if (position + 5 <= json.length &&
                    json[position] == 'f' &&
                    json[position + 1] == 'a' &&
                    json[position + 2] == 'l' &&
                    json[position + 3] == 's' &&
                    json[position + 4] == 'e')
                return (T) Boolean.FALSE;
            throw new UnexpectedTokenException(json[position], position);
        } else if (clazz.equals(BigDecimal.class)) {
            char token = json[position];
            BigDecimal number = BigDecimal.ZERO;
            boolean negative = false;
            if (token == '-') {
                negative = true;
            } else if (token != '+') {
                number = BigDecimal.valueOf(token - '0');
            }
            position += 1;
            while (position < json.length) {
                token = json[position];
                if (token >= '0' && token <= '9') {
                    number = number.multiply(BigDecimal.TEN).add(BigDecimal.valueOf(token - '0'));
                    position += 1;
                } else break;
            }
            if (token == '.') {
                position += 1;
                int precision = 1;
                while (position < json.length) {
                    token = json[position];
                    if (token >= '0' && token <= '9') {
                        number = number.add(BigDecimal.valueOf(token - '0').divide(BigDecimal.TEN.pow(precision++)));
                        position += 1;
                    } else break;
                }
            }
            if (negative) number = number.negate();
            if (token == 'E' || token == 'e') {
                int power = 0;
                negative = false;
                token = json[++position];
                if (token == '-') {
                    negative = true;
                } else if (token != '+') {
                    power = token - '0';
                }
                position += 1;
                while (position < json.length) {
                    token = json[position];
                    if (token >= '0' && token <= '9') {
                        power = (token - '0') + 10 * power;
                        position += 1;
                    } else break;
                }
                if (negative) {
                    number = number.movePointLeft(power);
                } else {
                    number = number.movePointRight(power);
                }
            }
            return (T) number;
        } else if (clazz.equals(String.class)) {
            if (json[position] != '\"') throw new UnexpectedTokenException(json[position], position);
            var sb = new StringBuilder();
            char c;
            while (++position < json.length && (c = json[position]) != '"') {
                if (c == '\\') {
                    c = json[++position];
                    switch (c) {
                        case '\\':
                            sb.append('\\');
                            break;
                        case '"':
                            sb.append('\"');
                            break;
                        case 'b':
                            sb.append('\b');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        default:
                            sb.append(c);
                    }
                } else {
                    sb.append(c);
                }
            }
            if (position == json.length) throw new UnexpectedEOFException(position);
            if (json[position] != '\"') throw new UnexpectedTokenException(json[position], position);
            return (T) sb.toString();
        } else if (clazz.isAssignableFrom(Map.class)) {
            if (json[position++] != '{') throw new UnexpectedTokenException(json[position], position);
            Map<String, Object> map = new HashMap<>();
            if (json[position++] != '}') throw new UnexpectedTokenException(json[position], position);
            return (T) map;
        } else if (clazz.isAssignableFrom(List.class)) {
            if (json[position++] != '[') throw new UnexpectedTokenException(json[position], position);
            Map<String, Object> map = new HashMap<>();
            if (json[position++] != ']') throw new UnexpectedTokenException(json[position], position);
            return (T) map;
        } else {
            throw new UnsupportedJsonSerializationException(clazz);
        }
    }

    public static <T> T parse(String json, Class<T> clazz) {
        return parse(json.toCharArray(), 0, clazz);
    }

    public static Object parse(String json) {
        char[] chars = json.toCharArray();
        int length = chars.length;
        int pos = 0;
        char token = chars[pos];
        if (pos + 3 < chars.length && chars[pos] == 'n' && chars[pos + 1] == 'u' && chars[pos + 2] == 'l' && chars[pos + 3] == 'l') {
            return null;
        } else if (token >= '0' && token <= '9' || token == '+' || token == '-') {
            BigDecimal number = BigDecimal.ZERO;
            boolean negative = false;
            if (token == '-') {
                negative = true;
            } else if (token != '+') {
                number = BigDecimal.valueOf(token - '0');
            }
            pos += 1;
            while (pos < length) {
                token = chars[pos];
                if (token >= '0' && token <= '9') {
                    number = number.multiply(BigDecimal.TEN).add(BigDecimal.valueOf(token - '0'));
                    pos += 1;
                } else break;
            }
            if (token == '.') {
                pos += 1;
                int precision = 1;
                while (pos < length) {
                    token = chars[pos];
                    if (token >= '0' && token <= '9') {
                        number = number.add(BigDecimal.valueOf(token - '0').divide(BigDecimal.TEN.pow(precision++)));
                        pos += 1;
                    } else break;
                }
            }
            if (negative) number = number.negate();
            if (token == 'E' || token == 'e') {
                int power = 0;
                negative = false;
                token = chars[++pos];
                if (token == '-') {
                    negative = true;
                } else if (token != '+') {
                    power = token - '0';
                }
                pos += 1;
                while (pos < length) {
                    token = chars[pos];
                    if (token >= '0' && token <= '9') {
                        power = (token - '0') + 10 * power;
                        pos += 1;
                    } else break;
                }
                if (negative) {
                    number = number.movePointLeft(power);
                } else {
                    number = number.movePointRight(power);
                }
            }
            return number;
        } else if (pos + 3 < chars.length && chars[pos] == 't' && chars[pos + 1] == 'r' && chars[pos + 2] == 'u' && chars[pos + 3] == 'e') {
            return Boolean.TRUE;
        } else if (pos + 4 < chars.length && chars[pos] == 'f' && chars[pos + 1] == 'a' && chars[pos + 2] == 'l' && chars[pos + 3] == 's' && chars[pos + 4] == 'e') {
            return Boolean.FALSE;
        } else if (token == '"') {
            StringBuilder sb = new StringBuilder();
            token = chars[++pos];
            while (token != '"') {
                if (token == '\\') {
                    token = chars[++pos];
                    switch (token) {
                        case '\\':
                            sb.append('\\');
                            break;
                        case '"':
                            sb.append('\"');
                            break;
                        case 'b':
                            sb.append('\b');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        default:
                            sb.append(token);
                    }
                } else {
                    sb.append(token);
                }
                token = chars[++pos];
            }
            return sb.toString();
        } else {
            throw new IllegalArgumentException("JSON Malformed");
        }
    }
}
