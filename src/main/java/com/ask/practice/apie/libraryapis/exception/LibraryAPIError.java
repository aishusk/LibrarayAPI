package com.ask.practice.apie.libraryapis.exception;

public class LibraryAPIError {
    private String traceId;
    private String errorMessage;

    public LibraryAPIError(String traceId, String errorMessage) {
        this.traceId = traceId;
        this.errorMessage = errorMessage;
    }

    public LibraryAPIError(){

    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "LibraryAPIError{" +
                "traceId='" + traceId + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
