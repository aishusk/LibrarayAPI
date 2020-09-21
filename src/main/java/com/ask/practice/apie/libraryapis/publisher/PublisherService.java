package com.ask.practice.apie.libraryapis.publisher;

import com.ask.practice.apie.libraryapis.exception.LibraryResourceAlreadyExistsException;
import com.ask.practice.apie.libraryapis.exception.LibraryResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public void addPublisher(Publisher publisherToBeAdded) throws LibraryResourceAlreadyExistsException {
        PublisherEntity publisherEntity = new PublisherEntity(
                publisherToBeAdded.getName(),
                publisherToBeAdded.getEmailId(),
                publisherToBeAdded.getPhoneNumber());
        PublisherEntity addedPublisher = null;
        try {
            addedPublisher = publisherRepository.save(publisherEntity);
        }catch (DataIntegrityViolationException de){
            throw new LibraryResourceAlreadyExistsException("Publisher already exists!!");
        }
        publisherToBeAdded.setPublisherId(addedPublisher.getPublisherId());
    }

    public Publisher getPublisher(int publisherId) throws LibraryResourceNotFoundException {
        Optional<PublisherEntity> publisherEntity = publisherRepository.findById(publisherId);
        Publisher publisher = null;
        if(publisherEntity.isPresent()){
            PublisherEntity pe = publisherEntity.get();
            publisher = createPublisherFromEntity(pe);
        }else {
            throw new LibraryResourceNotFoundException("Publisher Id : "+ publisherId+ "is not found");
        }
        return publisher;
    }

    private Publisher createPublisherFromEntity(PublisherEntity pe) {
        return new Publisher(pe.getPublisherId(),pe.getName(),pe.getEmailId(),pe.getPhoneNumber());
    }
}
