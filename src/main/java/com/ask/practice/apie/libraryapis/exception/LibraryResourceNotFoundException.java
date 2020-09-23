package com.ask.practice.apie.libraryapis.exception;

public class LibraryResourceNotFoundException extends Exception {
    private String traceId;
    public LibraryResourceNotFoundException(String message,String traceId) {
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
