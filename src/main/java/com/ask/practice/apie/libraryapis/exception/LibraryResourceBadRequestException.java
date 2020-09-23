package com.ask.practice.apie.libraryapis.exception;

public class LibraryResourceBadRequestException extends Exception {
    private String traceId;
    public LibraryResourceBadRequestException(String message, String traceId) {
        super(message);
        this.traceId = traceId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
