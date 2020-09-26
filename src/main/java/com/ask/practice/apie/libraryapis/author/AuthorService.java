package com.ask.practice.apie.libraryapis.author;

import com.ask.practice.apie.libraryapis.exception.LibraryResourceAlreadyExistsException;
import com.ask.practice.apie.libraryapis.exception.LibraryResourceNotFoundException;
import com.ask.practice.apie.libraryapis.publisher.Publisher;
import com.ask.practice.apie.libraryapis.publisher.PublisherEntity;
import com.ask.practice.apie.libraryapis.utils.LibraryAPIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ask.practice.apie.libraryapis.utils.LibraryAPIUtils.getTraceIdIfNotSet;

@Service
public class AuthorService {
    private final static Logger log = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    AuthorRepository authorRepository;

    public Author addAuthor(Author author, @RequestHeader(value = "Trace-Id",defaultValue = "")String traceId) throws LibraryResourceAlreadyExistsException {
        traceId = getTraceIdIfNotSet(traceId);
        AuthorEntity authorEntity = new AuthorEntity(
                author.getFirstName(),
                author.getLastName(),
                author.getDateOfBirth(),
                author.getGender()
        );
        try {
            authorRepository.save(authorEntity);
        }catch (DataIntegrityViolationException de){
            log.error("Author [] already exists",author);
            throw new LibraryResourceAlreadyExistsException("Author"+ author+ "already exists",traceId);
        }
        author.setAuthorId(authorEntity.getAuthorId());
        return author;
    }

    public Author getAuthor(int authorId, @RequestHeader(value = "Trace-Id",defaultValue = "")String traceId) throws LibraryResourceNotFoundException {
        traceId = getTraceIdIfNotSet(traceId);
        Optional<AuthorEntity> authorEntity = authorRepository.findById(authorId);
        Author author = null;
        if(authorEntity.isPresent()){
            AuthorEntity ae = authorEntity.get();
            author = createAuthorFromEntity(ae);
        }else {
            throw new LibraryResourceNotFoundException("Author Id : "+ authorId+ "is not found",traceId);
        }
        return author;
    }

    public void updateAuthor(Author authorToBeUpdated, @RequestHeader(value = "Trace-Id",defaultValue = "")String traceId) throws LibraryResourceNotFoundException {
        traceId = getTraceIdIfNotSet(traceId);
        Optional<AuthorEntity> authorEntity = authorRepository.findById(authorToBeUpdated.getAuthorId());
        Author author = null;
        if(authorEntity.isPresent()){
            AuthorEntity ae = authorEntity.get();
            if(LibraryAPIUtils.doesStringValueExists(authorToBeUpdated.getFirstName())){
                ae.setFirstName(authorToBeUpdated.getFirstName());
            }
            if(LibraryAPIUtils.doesStringValueExists(authorToBeUpdated.getDateOfBirth())){
                ae.setDateOfBirth(authorToBeUpdated.getDateOfBirth());
            }
            authorRepository.save(ae);
            authorToBeUpdated = createAuthorFromEntity(ae);
        }else {
            throw new LibraryResourceNotFoundException("Author Id : "+ authorToBeUpdated.getFirstName()+ "is not found",traceId);
        }
    }

    public void deleteAuthor(int authorId, @RequestHeader(value = "Trace-Id",defaultValue = "")String traceId) throws LibraryResourceNotFoundException {
        traceId = getTraceIdIfNotSet(traceId);
        try {
            authorRepository.deleteById(authorId);
        }catch (EmptyResultDataAccessException e){
            throw new LibraryResourceNotFoundException("Author Id : "+ authorId+ "is not found",traceId);
        }
    }

    public List<Author> searchAuthor(String firstName,String lastName){
        List<AuthorEntity> authorEntities = null;
        if(LibraryAPIUtils.doesStringValueExists(firstName) && LibraryAPIUtils.doesStringValueExists(lastName)){
            authorEntities= authorRepository.findByFirstNameAndLastNameContaining(firstName,lastName);
        }else if(LibraryAPIUtils.doesStringValueExists(firstName)){
            authorEntities = authorRepository.findByFirstNameContaining(firstName);
        }else if (LibraryAPIUtils.doesStringValueExists(lastName)){
            authorEntities = authorRepository.findByLastNameContaining(lastName);
        }
        if(CollectionUtils.isEmpty(authorEntities)){
            return createAuthorListFromEntities(authorEntities);
        }
        return Collections.emptyList();
    }



    private Author createAuthorFromEntity(AuthorEntity authorEntity) {
        return new Author(authorEntity.getAuthorId(),authorEntity.getFirstName(),authorEntity.getLastName(),authorEntity.getDateOfBirth(),authorEntity.getGender());
    }

    private List<Author> createAuthorListFromEntities(List<AuthorEntity> authorEntities){
        return authorEntities.stream().map(this::createAuthorFromEntity).collect(Collectors.toList());
    }
}
