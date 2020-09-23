package com.ask.practice.apie.libraryapis.publisher;


import com.ask.practice.apie.libraryapis.exception.LibraryResourceAlreadyExistsException;
import com.ask.practice.apie.libraryapis.exception.LibraryResourceBadRequestException;
import com.ask.practice.apie.libraryapis.exception.LibraryResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.ask.practice.apie.libraryapis.utils.LibraryAPIUtils.doesStringValueExists;
import static com.ask.practice.apie.libraryapis.utils.LibraryAPIUtils.getTraceIdIfNotSet;

@RestController
@RequestMapping(path ="v1/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/{publisherId}")
    public ResponseEntity<?> getPublisher(@PathVariable int publisherId, @RequestHeader(value = "Trace-Id",defaultValue = "") String traceId) throws LibraryResourceNotFoundException {
        traceId = getTraceIdIfNotSet(traceId);
        Publisher publisher = null;
           publisher = publisherService.getPublisher(publisherId, traceId);
        return new ResponseEntity<>(publisher,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addPublisher(@Valid @RequestBody Publisher publisher, @RequestHeader(value = "Trace-Id",defaultValue = "") String traceId) throws LibraryResourceAlreadyExistsException {
        traceId = getTraceIdIfNotSet(traceId);
        publisherService.addPublisher(publisher, traceId);
        return new ResponseEntity<>(publisher,HttpStatus.CREATED);
    }

    @PostMapping("/{publisherId}")
    public ResponseEntity<?> updatePublisher(@PathVariable int publisherId, @RequestBody Publisher publisher
                                            ,@RequestHeader(value = "Trace-Id",defaultValue = "") String traceId) throws LibraryResourceNotFoundException {
            traceId = getTraceIdIfNotSet(traceId);
            publisher.setPublisherId(publisherId);
            publisherService.updatePublisher(publisher, traceId);

        return new ResponseEntity<>(publisher,HttpStatus.CREATED);
    }

    @DeleteMapping("/{publisherId}")
    public ResponseEntity<?> deletePublisher(@PathVariable int publisherId, @RequestHeader(value = "Trace-Id",defaultValue = "") String traceId) throws LibraryResourceNotFoundException {
       traceId = getTraceIdIfNotSet(traceId);
            publisherService.deletePublisher(publisherId, traceId);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPublisher(@RequestParam String publisherName, @RequestHeader(value = "Trace-Id",defaultValue = "") String traceId) throws LibraryResourceBadRequestException {
        traceId = getTraceIdIfNotSet(traceId);
        if(!doesStringValueExists(publisherName)){
            throw new LibraryResourceBadRequestException("Please enter name to search publisher",traceId);
        }

        return new ResponseEntity<>(publisherService.searchPublisher(publisherName,traceId),HttpStatus.OK);
    }
}
