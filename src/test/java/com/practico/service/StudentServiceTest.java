package com.practico.service;

import com.practico.Exceptions.NoEntityException;
import com.practico.model.Student;
import com.practico.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    Student student;


    @BeforeEach
    void setUp() {
         student = new Student(1L, "Quinteros", "Yuliana",LocalDate.of(1990, 10, 17));
    }

    @Test
    void findById() throws NoEntityException {
       when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

       assertNotNull(studentService.findById(1L));

    }

    @Test
    void findAll() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(2L, "Gomez", "Aldana", LocalDate.of(2008, 05, 28)));
        studentList.add(new Student(3L, "Talab", "Yasmin", LocalDate.of(2010, 03, 13)));
        studentList.add(new Student(4L, "Aradó", "Anahí", LocalDate.of(2005, 9, 8)));
        studentList.add(new Student(5L, "Maximo", "Patricio", LocalDate.of(1986, 01, 26)));
        when(studentRepository.findAll()).thenReturn(studentList);
         assertFalse(studentService.findAll().isEmpty());
    }

    @Test
    void createStudent() {
        Student newStudent = new Student(null, "Miguel", "Arias",LocalDate.now());
        Student createStudent = new Student(5L, "Maximo", "Patricio", LocalDate.of(1986, 01, 26));
        when(studentRepository.save(newStudent)).thenReturn(createStudent);

        assertNotNull(studentService.createStudent(newStudent));
    }

    @Test
    void updateStudent() throws NoEntityException {
        when(studentRepository.save(student)).thenReturn(student);
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student));
        Student updateStudent = studentService.updateStudent(student);
        assertNotNull(updateStudent);

    }

    @Test
    void deleteStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        doNothing().when(studentRepository).delete(student);
        studentRepository.delete(student);
        assertFalse(studentRepository.findById(student.getId()).isPresent());


    }
}