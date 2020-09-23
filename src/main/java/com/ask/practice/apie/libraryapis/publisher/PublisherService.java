package com.ask.practice.apie.libraryapis.publisher;

import com.ask.practice.apie.libraryapis.exception.LibraryResourceAlreadyExistsException;
import com.ask.practice.apie.libraryapis.exception.LibraryResourceNotFoundException;
import com.ask.practice.apie.libraryapis.utils.LibraryAPIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ask.practice.apie.libraryapis.utils.LibraryAPIUtils.doesStringValueExists;
import static com.ask.practice.apie.libraryapis.utils.LibraryAPIUtils.getTraceIdIfNotSet;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public void addPublisher(Publisher publisherToBeAdded, @RequestHeader(value = "Trace-Id",defaultValue = "")String traceId) throws LibraryResourceAlreadyExistsException {
        traceId = getTraceIdIfNotSet(traceId);

        PublisherEntity publisherEntity = new PublisherEntity(
                publisherToBeAdded.getName(),
                publisherToBeAdded.getEmailId(),
                publisherToBeAdded.getPhoneNumber());
        PublisherEntity addedPublisher = null;
        try {
            addedPublisher = publisherRepository.save(publisherEntity);
        }catch (DataIntegrityViolationException de){
            throw new LibraryResourceAlreadyExistsException("Trace-id: "+traceId+ " Publisher already exists!!");
        }
        publisherToBeAdded.setPublisherId(addedPublisher.getPublisherId());
    }

    public Publisher getPublisher(int publisherId, @RequestHeader(value = "Trace-Id",defaultValue = "")String traceId) throws LibraryResourceNotFoundException {
        traceId = getTraceIdIfNotSet(traceId);
        Optional<PublisherEntity> publisherEntity = publisherRepository.findById(publisherId);
        Publisher publisher = null;
        if(publisherEntity.isPresent()){
            PublisherEntity pe = publisherEntity.get();
            publisher = createPublisherFromEntity(pe);
        }else {
            throw new LibraryResourceNotFoundException("Trace-id: "+traceId+ " Publisher Id : "+ publisherId+ "is not found");
        }
        return publisher;
    }

    private Publisher createPublisherFromEntity(PublisherEntity pe) {
        return new Publisher(pe.getPublisherId(),pe.getName(),pe.getEmailId(),pe.getPhoneNumber());
    }

    public void updatePublisher(Publisher publisherToBeUpdated, @RequestHeader(value = "Trace-Id",defaultValue = "")String traceId) throws LibraryResourceNotFoundException {
        traceId = getTraceIdIfNotSet(traceId);
        Optional<PublisherEntity> publisherEntity = publisherRepository.findById(publisherToBeUpdated.getPublisherId());
        Publisher publisher = null;
        if(publisherEntity.isPresent()){
            PublisherEntity pe = publisherEntity.get();
            if(LibraryAPIUtils.doesStringValueExists(publisherToBeUpdated.getEmailId())){
                pe.setEmailId(publisherToBeUpdated.getEmailId());
            }
            if(LibraryAPIUtils.doesStringValueExists(publisherToBeUpdated.getPhoneNumber())){
                pe.setPhoneNumber(publisherToBeUpdated.getPhoneNumber());
            }
            publisherRepository.save(pe);
            publisherToBeUpdated = createPublisherFromEntity(pe);
        }else {
            throw new LibraryResourceNotFoundException("Trace-id: "+traceId+ "Publisher Id : "+ publisherToBeUpdated.getPublisherId()+ "is not found");
        }
    }

    public void deletePublisher(int publisherId, @RequestHeader(value = "Trace-Id",defaultValue = "")String traceId) throws LibraryResourceNotFoundException {
        traceId = getTraceIdIfNotSet(traceId);
        try {
            publisherRepository.deleteById(publisherId);
        }catch (EmptyResultDataAccessException e){
            throw new LibraryResourceNotFoundException("Trace-id: "+traceId+ "Publisher Id : "+ publisherId+ "is not found");
        }
    }

    public List<Publisher> searchPublisher(String publisherName,  @RequestHeader(value = "Trace-Id",defaultValue = "")String traceId) {
        traceId = getTraceIdIfNotSet(traceId);
        List<PublisherEntity> publisherList = null;
        if(doesStringValueExists(publisherName)){
           publisherList = publisherRepository.findByNameContaining(publisherName);
        }
        if(!CollectionUtils.isEmpty(publisherList)){
            return  createPublisherForSearchResoponse(publisherList);
        }else {
            return Collections.emptyList();
        }

    }

    private List<Publisher> createPublisherForSearchResoponse(List<PublisherEntity> publisherList) {
        return publisherList.stream().map(this::createPublisherFromEntity).collect(Collectors.toList());
    }
}
