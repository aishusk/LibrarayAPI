package com.ask.practice.apie.libraryapis.exception;

public class LibraryResourceAlreadyExistsException extends Exception {
    private String traceId;
    public LibraryResourceAlreadyExistsException(String message,String traceId) {
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
