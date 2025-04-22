package com.ava.studentenroll.dto;

import lombok.Data;

@Data
public class StudentResponseDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String course;
    private String phoneNumber;
    private String address;
    private String dob;
    private String gender;
    private String imageName;
    private String imageType;
    private byte[] imageData;
}
