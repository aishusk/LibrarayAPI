package com.ask.practice.apie.libraryapis.publisher;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {

    @Mock
    PublisherRepository publisherRepositoryMock;

    PublisherService publisherService;

    @BeforeAll
    public void setUp(){
        publisherService = new PublisherService();
    }

    @Test
    void addPublisher() {
//        Mockito.when(publisherRepositoryMock.save(any(PublisherEntity.class))).thenReturn(createPublisheraEntity());
    
    }

    @Test
    void getPublisher() {
    }

    @Test
    void updatePublisher() {
    }

    @Test
    void deletePublisher() {
    }

    @Test
    void searchPublisher() {
    }



    public static Publisher createPublisher(){
        return new Publisher(0,"test","test@gmail.com","222-222-222");
    }

    public static PublisherEntity createPublisheraEntity(){
        return new PublisherEntity("test","test@gmail.com","222-222-222");
    }
}