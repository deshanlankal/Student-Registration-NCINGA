package com.ava.studentenroll.service;

import com.ava.studentenroll.model.Student;
import com.ava.studentenroll.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class StudentServiceImplementation implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImplementation(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student saveStudent(Student student, MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                student.setImageName(imageFile.getOriginalFilename());
                student.setImageType(imageFile.getContentType());
                student.setImageData(imageFile.getBytes()); // Storing the image data
            }
            return studentRepository.save(student);
        } catch (IOException e) {
            throw new RuntimeException("Error saving student with profile image", e);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
    }

    @Override
    public Student updateStudent(String id, Student updatedStudent, MultipartFile imageFile) {
        Student student = getStudentById(id);

        // Update the student's information
        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setEmail(updatedStudent.getEmail());
        student.setCourse(updatedStudent.getCourse());
        student.setPhoneNumber(updatedStudent.getPhoneNumber());
        student.setAddress(updatedStudent.getAddress());
        student.setDob(updatedStudent.getDob());
        student.setGender(updatedStudent.getGender());

        // Handle image file if present
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                student.setImageName(imageFile.getOriginalFilename());
                student.setImageType(imageFile.getContentType());
                student.setImageData(imageFile.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error updating student with image", e);
        }

        return studentRepository.save(student);
    }

    @Override
    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found with email: " + email));
    }

    @Override
    public Student getStudentByPhoneNumber(String phoneNumber) {
        return studentRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Student not found with phone number: " + phoneNumber));
    }

    @Override
    public void deleteStudent(String id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }
}
