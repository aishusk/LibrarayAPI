package com.ask.practice.apie.libraryapis.publisher;


import com.ask.practice.apie.libraryapis.exception.LibraryResourceAlreadyExistsException;
import com.ask.practice.apie.libraryapis.exception.LibraryResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="v1/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/{publisherId}")
    public ResponseEntity<?> getPublisher(@PathVariable int publisherId){
        Publisher publisher = null;
        try {
           publisher = publisherService.getPublisher(publisherId);
        }catch (LibraryResourceNotFoundException e){
            new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(publisher,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addPublisher(@RequestBody Publisher publisher) {
        try {
           publisherService.addPublisher(publisher);
        } catch (LibraryResourceAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(publisher,HttpStatus.CREATED);
    }
}
