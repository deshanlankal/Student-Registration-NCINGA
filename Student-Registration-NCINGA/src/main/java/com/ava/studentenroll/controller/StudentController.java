package com.ava.studentenroll.controller;


import com.ava.studentenroll.model.Student;
import com.ava.studentenroll.repository.StudentSearchRepository;
import com.ava.studentenroll.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:3000")
//@RequiredArgsConstructor
public class StudentController {


    private final StudentSearchRepository studentSearchRepository;

    private final StudentService studentService;

    public StudentController(StudentService studentService, StudentSearchRepository studentSearchRepository) {

        this.studentService = studentService;
        this.studentSearchRepository = studentSearchRepository;
    }


    // Create a new student
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student savedStudent = studentService.saveStudent(student);
        return ResponseEntity.ok(savedStudent);
    }

    @PostMapping("/student")
    public ResponseEntity<?> createStudent(@RequestPart String student,
                                           @RequestPart MultipartFile imageFile) {
        try {
            // Convert student JSON string to Student object
            ObjectMapper objectMapper = new ObjectMapper();
            Student studentObj = objectMapper.readValue(student, Student.class);

            // Save student with the image
            Student savedStudent = studentService.saveStudent(studentObj, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving student: " + e.getMessage());
        }
    }

    // Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

//    // Update student by ID
//    @PutMapping("/{id}")
//    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student student) {
//        Student updatedStudent = studentService.updateStudent(id, student, imageFile);
//        return ResponseEntity.ok(updatedStudent);
//    }

    @PutMapping("/student/{id}")
    public ResponseEntity<?> updateStudentWithImage(@PathVariable String id,
                                                    @RequestPart String student,
                                                    @RequestPart(required = false) MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Student studentObj = objectMapper.readValue(student, Student.class);
            Student updatedStudent = studentService.updateStudent(id, studentObj, imageFile);
            return ResponseEntity.ok(updatedStudent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating student: " + e.getMessage());
        }
    }


    // Delete student by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getStudentByEmail(@PathVariable String email) {
        Student student = studentService.getStudentByEmail(email);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.ok(null); // No student found
        }
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<?> getStudentByPhoneNumber(@PathVariable String phoneNumber) {
        Student student = studentService.getStudentByPhoneNumber(phoneNumber);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.ok(null); // No student found
        }
    }

    //Search feature
//    @GetMapping("/search/{text}")
//    public List<Student> search(
//            @PathVariable String text,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size
//    ) {
//        return studentSearchRepository.findByText(text, page, size);
//    }

    @GetMapping("/search/{text}")
    public List<Student> search(@PathVariable String text) {
        return studentSearchRepository.findByText(text);
    }
}
