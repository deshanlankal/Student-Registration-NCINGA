package com.ava.studentenroll.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StudentRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String course;
    private String phoneNumber;
    private String address;
    private String dob;
    private String gender;
    private MultipartFile imageFile;
}
