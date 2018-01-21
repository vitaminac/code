package json;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.io.PrintWriter;

public class JSONKeyValueUndefined extends Exception {
    private final Exception exception;

    public JSONKeyValueUndefined(@NotNull Exception e) {
        this.exception = e;
    }

    @Override
    public String getMessage() {
        return this.exception.getMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        return this.exception.getCause();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        this.exception.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        this.exception.printStackTrace(s);
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return this.exception.getStackTrace();
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.exception.setStackTrace(stackTrace);
    }
}
