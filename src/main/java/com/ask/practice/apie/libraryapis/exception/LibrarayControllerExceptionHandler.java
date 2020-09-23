package com.ask.practice.apie.libraryapis.exception;

import com.ask.practice.apie.libraryapis.utils.LibraryAPIUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;

@ControllerAdvice
public class LibrarayControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(LibraryResourceNotFoundException.class)
    public ResponseEntity<LibraryAPIError> handleLibraryResourceNotFoundException(
            LibraryResourceNotFoundException e, WebRequest webRequest
    ){
        return new ResponseEntity<>(new LibraryAPIError(e.getTraceId(),e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LibraryResourceAlreadyExistsException.class)
    public ResponseEntity<LibraryAPIError> handleLibraryResourceAlreadyExistsException(
            LibraryResourceNotFoundException e, WebRequest webRequest
    ){
        return new ResponseEntity<>(new LibraryAPIError(e.getTraceId(),e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LibraryResourceBadRequestException.class)
    public ResponseEntity<LibraryAPIError> handleLibraryBadRequestException(
            LibraryResourceNotFoundException e, WebRequest webRequest
    ){
        return new ResponseEntity<>(new LibraryAPIError(e.getTraceId(),e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<LibraryAPIError> handleAllException(
            LibraryResourceNotFoundException e, WebRequest webRequest
    ){
        String traceId = getTraceId(webRequest);
        return new ResponseEntity<>(new LibraryAPIError(e.getTraceId(),e.getMessage()), HttpStatus.NOT_FOUND);
    }

    private String getTraceId(WebRequest webRequest) {
        return !LibraryAPIUtils.doesStringValueExists(webRequest.getHeader("Trace-Id"))? UUID.randomUUID().toString():webRequest.getHeader("Trace-Id");
    }


}
