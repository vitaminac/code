package json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;

public interface JSON {
    static <T> String stringify(T thing) {
        StringBuilder sb = new StringBuilder();
        if (thing == null) {
            sb.append("null");
        } else if (Arrays.asList(Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Number.class).contains(thing.getClass())) {
            sb.append(thing);
        } else if (thing.getClass().equals(Character.class)) {
            sb.append(stringify(String.valueOf(thing)));
        } else if (thing.getClass().equals(String.class)) {
            sb.append('\"');
            for (char c : ((String) thing).toCharArray()) {
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
        } else if (thing.getClass().isArray()) {
            sb.append('[');
            int length = Array.getLength(thing);
            for (int i = length; i > 1; i--) {
                Object element = Array.get(thing, length - i);
                sb.append(stringify(element));
                sb.append(',');
            }
            if (length >= 0) sb.append(stringify(Array.get(thing, length - 1)));
            sb.append(']');
        } else if (Iterable.class.isAssignableFrom(thing.getClass())) {
            sb.append('[');
            Iterator<?> it = ((Iterable<?>) thing).iterator();
            while (it.hasNext()) {
                sb.append(stringify(it.next()));
                if (it.hasNext()) sb.append(',');
            }
            sb.append(']');
        } else {
            sb.append('{');
            Iterator<Field> it = Arrays.asList(thing.getClass().getDeclaredFields()).iterator();
            while (it.hasNext()) {
                Field field = it.next();
                if (!field.isAnnotationPresent(JsonIgnore.class)) {
                    sb.append('\"');
                    sb.append(field.getName());
                    sb.append('\"');
                    sb.append(':');
                    field.setAccessible(true);
                    try {
                        sb.append(stringify(field.get(thing)));
                    } catch (IllegalAccessException e) {
                        sb.append(stringify(null));
                    }
                }
                if (it.hasNext()) sb.append(',');
            }
            sb.append('}');
        }
        return sb.toString();
    }

    static <T> Object parse(String json) {
        char[] chars = json.toCharArray();
        int length = chars.length;
        int pos = 0;
        char token = chars[pos];
        if (token >= '0' && token <= '9' || token == '+' || token == '-') {
            double number = 0;
            int sign = 1;
            if (token == '-') {
                sign = -1;
            } else if (token != '+') {
                number = token - '0';
            }
            pos += 1;
            while (pos < length) {
                token = chars[pos];
                if (token >= '0' && token <= '9') {
                    number = (token - '0') + 10 * number;
                    pos += 1;
                } else break;
            }
            if (token == '.') {
                pos += 1;
                int digit = 10;
                while (pos < length) {
                    token = chars[pos];
                    if (token >= '0' && token <= '9') {
                        number += (double) (token - '0') / digit;
                        digit *= 10;
                        pos += 1;
                    } else break;
                }
            }
            number *= sign;
            if (token == 'E' || token == 'e') {
                int power = 0;
                sign = 1;
                token = chars[++pos];
                if (token == '-') {
                    sign = -1;
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
                number = number * Math.pow(10, power);
            }
            return number;
        } else {
            throw new IllegalArgumentException("JSON Malformed");
        }
    }
}
