package json.reader;

import json.JSON;
import json.JSONRestoreFactory;
import json.MalformedJSONInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class JSONValue {
    private static final HashSet<Character> digit;
    private static final HashSet<Character> digit19;

    static {
        digit = new HashSet<>();
        digit19 = new HashSet<>();
        for (char c = '1'; c <= '9'; c++) {
            digit19.add(c);
        }
        digit.addAll(digit19);
        digit.add('0');
    }

    private final String value;
    private int pos;

    public JSONValue(String value) {
        StringBuilder sb = new StringBuilder();
        // pre-processing
        boolean str = false;
        char chr;
        for (int i = 0; i < value.length(); i++) {
            chr = value.charAt(i);
            if (str) {
                sb.append(chr);
            } else if (!(chr == ' ' || chr == '\t' || chr == '\n' || chr == '\r')) {
                sb.append(chr);
                if (chr == '\\') {
                    sb.append(value.charAt(++i));
                } else if (chr == '\"') {
                    str = !str;
                }
            }
        }
        sb.append('\0');
        this.value = sb.toString();
        this.pos = 0;
    }

    public boolean getBoolean() throws MalformedJSONInput {
        char c = this.next();
        if (c == 't') {
            if (this.matches('r')) {
                if (this.matches('u')) {
                    if (this.matches('e')) {
                        if (this.next() == '\0') {
                            return true;
                        }
                    }
                }
            }
        } else if (c == 'f') {
            if (this.matches('a')) {
                if (this.matches('l')) {
                    if (this.matches('s')) {
                        if (this.matches('e')) {
                            if (this.next() == '\0') {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        throw new MalformedJSONInput(this.value.charAt(this.pos - 1), this.pos - 1);
    }

    public List<JSONValue> getList() throws MalformedJSONInput {
        List<JSONValue> list = new ArrayList<>();
        char chr = this.next();
        if (chr == '[') {
            Stack<Character> stack = new Stack<>();
            StringBuilder sb = new StringBuilder();
            chr = this.next();
            while (!stack.isEmpty() || chr != ']') {
                do {
                    if (!stack.isEmpty() && stack.peek() == '"') {
                        sb.append(chr);
                        if (chr == '"') {
                            stack.pop();
                        } else if (chr == '\\') {
                            sb.append(this.next());
                        }
                    } else {
                        sb.append(chr);
                        if (chr == '{' || chr == '"' || chr == '[') {
                            stack.add(chr);
                        } else if (chr == '}') {
                            if (stack.peek().equals('{')) {
                                stack.pop();
                            } else {
                                throw new MalformedJSONInput(chr, this.pos - 1);
                            }
                        } else if (chr == ']') {
                            if (stack.peek().equals('[')) {
                                stack.pop();
                            } else {
                                throw new MalformedJSONInput(chr, this.pos - 1);
                            }
                        }
                    }
                    chr = this.next();
                } while (!stack.isEmpty() || (chr != ',' && chr != ']'));
                list.add(new JSONValue(sb.toString()));
                sb = new StringBuilder();
            }
            return list;
        } else {
            throw new MalformedJSONInput(chr, this.pos - 1);
        }
    }

    public double getNumber() throws MalformedJSONInput {
        StringBuilder sb = new StringBuilder();
        char chr = this.next();
        if (chr == '-') {
            sb.append(chr);
            chr = this.next();
        }
        if (digit19.contains(chr)) {
            while (digit.contains(chr)) {
                sb.append(chr);
                chr = this.next();
            }
        } else if (chr == '0') {
            sb.append('0');
            chr = this.next();
        } else {
            throw new MalformedJSONInput(chr, this.pos - 1);
        }
        if (chr == '.') {
            sb.append(chr);
            chr = this.next();
            while (digit.contains(chr)) {
                sb.append(chr);
                chr = this.next();
            }
            if (chr == 'e' || chr == 'E') {
                sb.append(chr);
                chr = this.next();
                if (chr == '+' || chr == '-' || digit.contains(chr)) {
                    sb.append(chr);
                    chr = this.next();
                    while (digit.contains(chr)) {
                        sb.append(chr);
                        chr = this.next();
                    }
                } else {
                    throw new MalformedJSONInput(chr, this.pos);
                }
            }
        }
        sb.append(chr);
        return Double.parseDouble(sb.toString());
    }

    public <T extends JSON> T getObject(JSONRestoreFactory<T> factory) throws MalformedJSONInput {
        char chr = this.next();
        if (chr == '{') {
            HashMap<String, JSONValue> members = new HashMap<>();
            Stack<Character> stack = new Stack<>();
            StringBuilder keyBuilder = new StringBuilder();
            StringBuilder valueBuilder = new StringBuilder();
            chr = this.next();
            while (chr != '}' || !stack.isEmpty()) {
                if (chr == '"') {
                    // parse key
                    chr = this.next();
                    while (chr != '"') {
                        keyBuilder.append(chr);
                        if (chr == '\\') {
                            keyBuilder.append(this.next());
                        }
                        chr = this.next();
                    }
                    chr = this.next();
                    if (chr == ':') {
                        chr = this.next();
                        do {
                            if (!stack.isEmpty() && stack.peek() == '"') {
                                valueBuilder.append(chr);
                                if (chr == '"') {
                                    stack.pop();
                                } else if (chr == '\\') {
                                    valueBuilder.append(this.next());
                                }
                            } else {
                                valueBuilder.append(chr);
                                if (chr == '{' || chr == '"' || chr == '[') {
                                    stack.add(chr);
                                } else if (chr == '}') {
                                    if (stack.peek().equals('{')) {
                                        stack.pop();
                                    } else {
                                        throw new MalformedJSONInput(chr, this.pos - 1);
                                    }
                                } else if (chr == ']') {
                                    if (stack.peek().equals('[')) {
                                        stack.pop();
                                    } else {
                                        throw new MalformedJSONInput(chr, this.pos - 1);
                                    }
                                }
                            }
                            chr = this.next();
                        } while (!stack.isEmpty() || (chr != ',' && chr != '}'));
                        members.put(keyBuilder.toString(), new JSONValue(valueBuilder.toString()));
                        keyBuilder = new StringBuilder();
                        valueBuilder = new StringBuilder();
                        if (chr == ',') {
                            chr = this.next();
                        }
                    } else {
                        throw new MalformedJSONInput(chr, this.pos - 1);
                    }
                } else {
                    throw new MalformedJSONInput(chr, this.pos - 1);
                }
            }
            return factory.build(members);
        } else if (chr == 'n') {
            if (this.matches('u')) {
                if (this.matches('l')) {
                    if (this.matches('l')) {
                        return null;
                    }
                }
            }
        }
        throw new MalformedJSONInput(chr, this.pos - 1);
    }

    // TODO: exception was wrong
    public String getString() throws MalformedJSONInput {
        char c = this.next();
        if (c == '"') {
            StringBuilder sb = new StringBuilder();
            c = this.next();
            while (c != '"') {
                if (c == '\\') {
                    sb.append(this.next());
                } else {
                    sb.append(c);
                }
                c = this.next();
            }
            return sb.toString();
        } else if (c == 'n') {
            this.matches('u');
            this.matches('l');
            this.matches('l');
            return null;
        } else {
            throw new MalformedJSONInput(c, this.pos - 1);
        }
    }

    private boolean matches(char c) {
        return c == this.value.charAt(this.pos++);
    }

    private char next() {
        if (this.pos < this.value.length()) {
            return this.value.charAt(this.pos++);
        } else {
            return '\0';
        }
    }
}
