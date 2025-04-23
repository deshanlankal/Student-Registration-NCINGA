package com.ava.studentenroll.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "students") // Mongo collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String course;

    private String phoneNumber;
    private String address;

    private String dob;

    private String gender;


    //the image handling this
    //all the time that took to build this == 1 HOUR

    private String imageName;
    private String imageType;

    private byte[] imageData;

}
