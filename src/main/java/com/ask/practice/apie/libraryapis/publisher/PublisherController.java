package com.ask.practice.apie.libraryapis.publisher;


import com.ask.practice.apie.libraryapis.exception.LibraryResourceAlreadyExistsException;
import com.ask.practice.apie.libraryapis.exception.LibraryResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ask.practice.apie.libraryapis.utils.LibraryAPIUtils.doesStringValueExists;

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
           return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
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

    @PostMapping("/{publisherId}")
    public ResponseEntity<?> updatePublisher(@PathVariable int publisherId, @RequestBody Publisher publisher) {
        try {
            publisher.setPublisherId(publisherId);
            publisherService.updatePublisher(publisher);
        } catch (LibraryResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(publisher,HttpStatus.CREATED);
    }

    @DeleteMapping("/{publisherId}")
    public ResponseEntity<?> deletePublisher(@PathVariable int publisherId) {
        try {
            publisherService.deletePublisher(publisherId);
        } catch (LibraryResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPublisher(@RequestParam String publisherName){
        if(!doesStringValueExists(publisherName)){
            return new ResponseEntity<>("Please enter name to search publisher",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(publisherService.searchPublisher(publisherName),HttpStatus.OK);
    }
}
