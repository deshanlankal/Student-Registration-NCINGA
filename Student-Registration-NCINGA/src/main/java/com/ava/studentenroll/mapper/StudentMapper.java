package com.ava.studentenroll.mapper;

import com.ava.studentenroll.dto.StudentRequestDTO;
import com.ava.studentenroll.dto.StudentResponseDTO;
import com.ava.studentenroll.model.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class StudentMapper {

    public static Student toEntity(StudentRequestDTO dto) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setCourse(dto.getCourse());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setAddress(dto.getAddress());
        student.setDob(dto.getDob());
        student.setGender(dto.getGender());

        MultipartFile image = dto.getImageFile();
        if (image != null && !image.isEmpty()) {
            try {
                student.setImageName(image.getOriginalFilename());
                student.setImageType(image.getContentType());
                student.setImageData(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to process image file", e);
            }
        }
        return student;
    }

    public static StudentResponseDTO toDTO(Student student) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setCourse(student.getCourse());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setAddress(student.getAddress());
        dto.setDob(student.getDob());
        dto.setGender(student.getGender());
        dto.setImageName(student.getImageName());
        dto.setImageType(student.getImageType());
        dto.setImageData(student.getImageData());
        return dto;
    }
}
