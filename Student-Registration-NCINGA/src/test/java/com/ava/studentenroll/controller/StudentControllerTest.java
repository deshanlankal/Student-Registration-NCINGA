package com.ava.studentenroll.controller;

import com.ava.studentenroll.model.Student;
import com.ava.studentenroll.repository.StudentSearchRepository;
import com.ava.studentenroll.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Automatically wired MockMvc

    @MockBean
    private StudentService studentService;  // Mocked student service

    @MockBean
    private StudentSearchRepository studentSearchRepository;


    // The controller will be injected automatically
    @InjectMocks
    private StudentController studentController;

//    @Test
//    void createStudent() {
//        Student student = new Student("1", "John", "Doe", "john@example.com", "Science", "987654321", "456 Main St", "1999-02-02", "Male", null, null, null);
//
//        when(studentService.saveStudent(student)).thenReturn(student);
//
//        mockMvc.perform(post("/api/students")
//                .contentType("application/json")
//                .content("{\"firstname\": "}"))
//    }

    @Test
    void testCreateStudent() {
    }

    @Test
    void getAllStudents() {
    }

    @Test
    void getStudentById() throws Exception {
        // Arrange
        Student student = new Student("1", "Alice", "Smith", "alice@example.com", "Math", "123456789", "123 Main St", "2000-01-01", "Female", null, null, null);

        // Mock the service call
        when(studentService.getStudentById(anyString())).thenReturn(student);

        // Act & Assert
        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andExpect(jsonPath("$.firstName").value("Alice"))  // Check first name in response
                .andExpect(jsonPath("$.lastName").value("Smith"))   // Check last name
                .andExpect(jsonPath("$.email").value("alice@example.com"))  // Check email
                .andExpect(jsonPath("$.course").value("Math"))  // Check course
                .andExpect(jsonPath("$.phoneNumber").value("123456789"))  // Check phone number
                .andExpect(jsonPath("$.address").value("123 Main St"))  // Check address
                .andExpect(jsonPath("$.dob").value("2000-01-01"))  // Check date of birth
                .andExpect(jsonPath("$.gender").value("Female"));

    }

    @Test
    void updateStudentWithImage() {
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void getStudentByEmail() {
    }

    @Test
    void getStudentByPhoneNumber() {
    }

    @Test
    void search() {
    }
}