package com.ask.practice.apie.libraryapis.author;

import com.ask.practice.apie.libraryapis.utils.Gender;

import javax.persistence.*;

@Entity
@Table(name = "AUTHOR")
public class AuthorEntity {

    @Column(name = "Author_Id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "authorId_generator")
    @SequenceGenerator(name = "authorId_generator",sequenceName = "publisher_sequence",allocationSize = 50)
    private Integer authorId;
    @Column(name = "First_Name")
    private String firstName;
    @Column(name = "Last_Name")
    private String lastName;
    @Column(name = "Date_Of_Birth")
    private String dateOfBirth;
    @Column(name = "Gender")
    private Gender gender;

    public AuthorEntity() {
    }

    public AuthorEntity(String firstName, String lastName, String dateOfBirth, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
