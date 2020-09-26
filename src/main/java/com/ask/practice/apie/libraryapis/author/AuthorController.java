package com.ask.practice.apie.libraryapis.author;

import com.ask.practice.apie.libraryapis.exception.LibraryResourceAlreadyExistsException;
import com.ask.practice.apie.libraryapis.exception.LibraryResourceBadRequestException;
import com.ask.practice.apie.libraryapis.exception.LibraryResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ask.practice.apie.libraryapis.utils.LibraryAPIUtils.doesStringValueExists;
import static com.ask.practice.apie.libraryapis.utils.LibraryAPIUtils.getTraceIdIfNotSet;

@RestController
@RequestMapping(value = "v1/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping
    public ResponseEntity<?> addAuthors(@RequestBody Author author, @RequestHeader(value = "Trace-Id",defaultValue = "") String traceId) throws LibraryResourceAlreadyExistsException {
        traceId = getTraceIdIfNotSet(traceId);
        Author author1 =  authorService.addAuthor(author,traceId);
        return new ResponseEntity<>(author1, HttpStatus.OK);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<?> getAuthor(@PathVariable int authorId, @RequestHeader(value = "Trace-Id",defaultValue = "") String traceId) throws LibraryResourceNotFoundException {
        traceId = getTraceIdIfNotSet(traceId);
        Author author = null;
        author = authorService.getAuthor(authorId, traceId);
        return new ResponseEntity<>(author,HttpStatus.OK);
    }

    @PostMapping("/{authorId}")
    public ResponseEntity<?> updatePublisher(@PathVariable int authorId, @RequestBody Author author
            ,@RequestHeader(value = "Trace-Id",defaultValue = "") String traceId) throws LibraryResourceNotFoundException {
        traceId = getTraceIdIfNotSet(traceId);
        author.setAuthorId(authorId);
        authorService.updateAuthor(author, traceId);

        return new ResponseEntity<>(author,HttpStatus.CREATED);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<?> deletePublisher(@PathVariable int authorId, @RequestHeader(value = "Trace-Id",defaultValue = "") String traceId) throws LibraryResourceNotFoundException {
        traceId = getTraceIdIfNotSet(traceId);
        authorService.deleteAuthor(authorId, traceId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPublisher(@RequestParam String firstName,@RequestParam String lastName, @RequestHeader(value = "Trace-Id",defaultValue = "") String traceId) throws LibraryResourceBadRequestException {
        traceId = getTraceIdIfNotSet(traceId);
        if(!doesStringValueExists(firstName) && !doesStringValueExists(lastName)){
            throw new LibraryResourceBadRequestException("Please enter firstname or lastName to search author",traceId);
        }

        return new ResponseEntity<>(authorService.searchAuthor(firstName,lastName),HttpStatus.OK);
    }


}

