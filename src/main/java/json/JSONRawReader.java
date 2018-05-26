package json;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

class JSONRawReader {
    private static char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static void _assert(boolean b, char c, int position) throws MalformedJSONInput {
        if (!b) {
            throw new MalformedJSONInput(c, position);
        }
    }

    private final Reader underlyingReader;
    private char lastChar = ' ';
    private int position = -1;
    private boolean ended = false;
    private boolean started = false;

    JSONRawReader(Reader underlyingReader) {
        this.underlyingReader = underlyingReader;
    }

    public boolean readBoolean() throws IOException, MalformedJSONInput {
        this.next();
        if (this.lastChar == 't') {
            this.next();
            if (this.lastChar == 'r') {
                this.next();
                if (this.lastChar == 'u') {
                    this.next();
                    if (this.lastChar == 'e') {
                        return true;
                    }
                }
            }
        } else if (this.lastChar == 'f') {
            this.next();
            if (this.lastChar == 'a') {
                this.next();
                if (this.lastChar == 'l') {
                    this.next();
                    if (this.lastChar == 's') {
                        this.next();
                        if (this.lastChar == 'e') {
                            return false;
                        }
                    }
                }
            }
        }
        throw new MalformedJSONInput(this.lastChar, this.position);
    }

    public double readNumber() throws IOException, MalformedJSONInput {
        StringBuilder sb = new StringBuilder();
        _assert(this.match(digits) || this.lastChar == '-', this.lastChar, this.position);
        sb.append(this.lastChar);
        while (this.match(digits)) {
            sb.append(this.lastChar);
        }
        if (this.lastChar == '.') {
            sb.append('.');
            _assert(this.match(digits) || this.lastChar == '-', this.lastChar, this.position);
            sb.append((char) this.lastChar);
            while (this.match(digits)) {
                sb.append(this.lastChar);
            }

        }
        if (this.lastChar == 'e' || this.lastChar == 'E') {
            sb.append('E');
            _assert(this.match(digits) || this.lastChar == '+' || this.lastChar == '-', this.lastChar, this.position);
            sb.append(lastChar);
            while (this.match(digits)) {
                sb.append(this.lastChar);
            }
            return Double.parseDouble(sb.toString());
        } else {
            return Double.parseDouble(sb.toString());
        }
    }

    public String readString() throws IOException, MalformedJSONInput {
        StringBuilder sb = new StringBuilder();
        _assert(this.match('\"'), this.lastChar, this.position);
        while (!this.ended) {
            sb.append(this.lastChar);
        }
        return sb.toString();
    }

    boolean match(char c) throws IOException {
        this.next();
        return this.lastChar == c;
    }

    private boolean match(char[] chars) throws IOException {
        this.next();
        return Arrays.toString(chars).chars().anyMatch(c -> this.lastChar == c);
    }

    private void next() throws IOException {
        if (this.ended) {
            throw new JSONEOFException(this.position);
        } else {
            int chr;
            do {
                chr = this.underlyingReader.read();
                ++this.position;
            } while (chr == ' ' || chr == '\t' || chr == '\n' || chr == '\r');
            if (chr < 0) {
                this.ended = true;
            } else if (chr == '\"') {
                if (!this.started) {
                    this.started = true;
                } else {
                    this.ended = true;
                }
            } else if (chr == '\\') {
                // escape character
                this.lastChar = (char) this.underlyingReader.read();
                ++this.position;
            } else {
                this.lastChar = (char) chr;
            }
        }
    }
}
