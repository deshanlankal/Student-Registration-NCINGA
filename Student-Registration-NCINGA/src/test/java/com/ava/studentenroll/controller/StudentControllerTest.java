package com.ava.studentenroll.controller;

import com.ava.studentenroll.model.Student;
import com.ava.studentenroll.repository.StudentSearchRepository;
import com.ava.studentenroll.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private StudentSearchRepository studentSearchRepository;

    @InjectMocks
    private StudentController studentController;

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student("1", "name", "name", "name", "name", "name", "name", "name", "name", null, null, null);
        when(studentService.saveStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""

                                {
                            "id": "1",
                            "firstName": "name",
                            "lastName": "name",
                            "email": "name",
                            "course": "name",
                            "phoneNumber": "name",
                            "address": "name",
                            "dob": "name",
                            "gender": "name"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("name"))
                .andExpect(jsonPath("$.lastName").value("name"))
                .andExpect(jsonPath("$.email").value("name"));
    }

    @Test
    void getStudentById() throws Exception {
        Student student = new Student("1", "Alice", "Smith", "alice@example.com", "Math", "123456789", "123 Main St", "2000-01-01", "Female", null, null, null);
        when(studentService.getStudentById(anyString())).thenReturn(student);

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.email").value("alice@example.com"))
                .andExpect(jsonPath("$.course").value("Math"))
                .andExpect(jsonPath("$.phoneNumber").value("123456789"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.dob").value("2000-01-01"))
                .andExpect(jsonPath("$.gender").value("Female"));
    }

    @Test
    void postToGetEndpointShouldReturn405() throws Exception {
        mockMvc.perform(post("/api/students/1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void getAllStudents() throws Exception {
        List<Student> students = List.of(
                new Student("1", "Alice", "Smith", "alice@example.com", "Math", "123456789", "123 Main St", "2000-01-01", "Female", null, null, null),
                new Student("2", "Bob", "Jones", "bob@example.com", "Science", "987654321", "456 Elm St", "1998-05-12", "Male", null, null, null)
        );

        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))//$.size(2)
                .andExpect(jsonPath("$[0].firstName").value("Alice"))
                .andExpect(jsonPath("$[1].firstName").value("Bob"));
    }


//    @Test
//    void updateStudentWithImage() throws Exception {
//        Student student = new Student("1", "Updated", "User", "updated@example.com", "Biology", "0001112222", "New Address", "2001-10-10", "Other", null, null, null);
//        String studentJson = new ObjectMapper().writeValueAsString(student);
//        MockMultipartFile imageFile = new MockMultipartFile("imageFile", "image.jpg", "image/jpeg", "fake-image-content".getBytes());
//        MockMultipartFile studentPart = new MockMultipartFile("student", "", "application/json", studentJson.getBytes());
//
//        when(studentService.updateStudent(anyString(), any(Student.class), any(MultipartFile.class)))
//                .thenReturn(student);
//
//        mockMvc.perform(multipart("/api/students/1")
//                        .file(studentPart)
//                        .file(imageFile)
//                        .with(request -> {
//                            request.setMethod("PUT"); // override POST to PUT
//                            return request;
//                        }))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName").value("Updated"));
//    }


    @Test
    void deleteStudent() throws Exception {
        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());
    }
    @Test
    void getStudentByEmail() throws Exception {
        Student student = new Student("1", "Jane", "Doe", "jane@example.com", "Art", "5555555555", "123 Art St", "1997-08-15", "Female", null, null, null);
        when(studentService.getStudentByEmail("jane@example.com")).thenReturn(student);

        mockMvc.perform(get("/api/students/email/jane@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jane@example.com"))
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    void getStudentByPhoneNumber() throws Exception {
        Student student = new Student("2", "Tom", "Brown", "tom@example.com", "Physics", "4444444444", "789 Maple St", "1995-03-22", "Male", null, null, null);
        when(studentService.getStudentByPhoneNumber("4444444444")).thenReturn(student);

        mockMvc.perform(get("/api/students/phone/4444444444"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value("4444444444"))
                .andExpect(jsonPath("$.firstName").value("Tom"));
    }

//    @Test
//    void search() throws Exception {
//        List<Student> results = List.of(
//                new Student("3", "Ella", "White", "ella@example.com", "History", "1112223333", "321 Birch St", "2002-11-20", "Female", null, null, null)
//        );
//
//        when(studentSearchRepository.search("Ella")).thenReturn(results);
//
//        mockMvc.perform(get("/api/students/search?query=Ella"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].firstName").value("Ella"));
//    }




}