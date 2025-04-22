package com.ava.studentenroll.service;


import com.ava.studentenroll.model.Student;
import org.junit.jupiter.api.Test;
import com.ava.studentenroll.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class StudentServiceImplementationTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImplementation studentService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveStudentWithoutImage_shouldSaveStudent() {
        Student student = new Student();
        student.setFirstName("John");
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.saveStudent(student);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void saveStudentWithImage_shouldSaveStudentWithImageData() throws IOException {
        Student student = new Student();
        MockMultipartFile image = new MockMultipartFile("image", "photo.png", "image/png", "image-data".getBytes());

        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArgument(0));

        Student result = studentService.saveStudent(student, image);
        assertEquals("photo.png", result.getImageName());
        assertEquals("image/png", result.getImageType());
        assertNotNull(result.getImageData());
    }

    @Test
    void getAllStudents_shouldReturnList() {
        List<Student> students = List.of(new Student(), new Student());
        when(studentRepository.findAll()).thenReturn(students);

        assertEquals(2, studentService.getAllStudents().size());
    }

    @Test
    void getStudentById_shouldReturnStudent() {
        Student student = new Student();
        student.setId("123");
        when(studentRepository.findById("123")).thenReturn(Optional.of(student));

        Student result = studentService.getStudentById("123");
        assertEquals("123", result.getId());
    }

    @Test
    void getStudentById_shouldThrowIfNotFound() {
        when(studentRepository.findById("404")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.getStudentById("404"));
    }

    @Test
    void updateStudent_shouldUpdateFieldsAndImage() throws IOException {
        String id = "123";
        Student existing = new Student();
        existing.setId(id);

        Student updated = new Student();
        updated.setFirstName("NewName");

        MockMultipartFile image = new MockMultipartFile("image", "new.png", "image/png", "data".getBytes());

        when(studentRepository.findById(id)).thenReturn(Optional.of(existing));
        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArgument(0));

        Student result = studentService.updateStudent(id, updated, image);
        assertEquals("NewName", result.getFirstName());
        assertEquals("new.png", result.getImageName());
    }

    @Test
    void getStudentByEmail_shouldReturnStudent() {
        Student student = new Student();
        student.setEmail("test@example.com");

        when(studentRepository.findByEmail("test@example.com")).thenReturn(Optional.of(student));

        Student result = studentService.getStudentByEmail("test@example.com");
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void getStudentByPhoneNumber_shouldReturnStudent() {
        Student student = new Student();
        student.setPhoneNumber("1234567890");

        when(studentRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(student));

        Student result = studentService.getStudentByPhoneNumber("1234567890");
        assertEquals("1234567890", result.getPhoneNumber());
    }

    @Test
    void deleteStudent_shouldDeleteIfExists() {
        when(studentRepository.existsById("321")).thenReturn(true);

        studentService.deleteStudent("321");

        verify(studentRepository).deleteById("321");
    }

    @Test
    void deleteStudent_shouldThrowIfNotExists() {
        when(studentRepository.existsById("999")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> studentService.deleteStudent("999"));
    }
}