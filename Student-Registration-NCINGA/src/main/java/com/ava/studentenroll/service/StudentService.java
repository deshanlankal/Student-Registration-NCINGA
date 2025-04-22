package com.ava.studentenroll.service;

import com.ava.studentenroll.model.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {

    Student saveStudent(Student student, MultipartFile imageFile);

    Student saveStudent(Student student);
    List<Student> getAllStudents();
    Student getStudentById(String id);
    Student updateStudent(String id, Student student, MultipartFile imageFile);
    Student getStudentByEmail(String email);
    Student getStudentByPhoneNumber(String phoneNumber);
    void deleteStudent(String id);

}
