package com.web.crawler.response;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorResponse {

    public String errorCode;
    public String errorMessage;
    public String exceptionClass;
    public String exceptionTrace;

    public ErrorResponse (Throwable e) {
        errorMessage = e.getMessage();
        exceptionClass = e.getClass().getName();

        if (e.getCause() != null && e.getCause().getMessage() != null) {
            errorMessage += " Root Cause -> " + e.getCause().getMessage();
        }
        exceptionClass = e.getClass().getName();
        if (!(e instanceof Exception)) {
            exceptionTrace = stackTrace(e);
        }
    }

    public static String stackTrace(Throwable e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
