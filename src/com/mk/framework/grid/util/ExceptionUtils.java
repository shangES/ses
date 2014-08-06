package com.mk.framework.grid.util;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author paul horn
 */
public final class ExceptionUtils {
    public static String formatStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        try {
            PrintWriter p = new PrintWriter(sw);
            t.printStackTrace(p);
        } catch (Exception e) {
        }
        return sw.toString();
    }
}
