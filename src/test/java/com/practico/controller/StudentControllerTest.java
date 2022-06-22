package com.practico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.practico.model.Student;
import com.practico.repository.StudentRepository;
import com.practico.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
class StudentControllerTest {

  @MockBean
    StudentService studentService;

  @MockBean
    StudentRepository studentRepository;

  @Autowired
  private MockMvc mockMvc;

    List<Student> listStudents;
    Student student;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        student = new Student(1L , "Quinteros" , "Yuli" , LocalDate.of(1990,10,17));
        listStudents = new ArrayList<>();
        listStudents.add( new Student(1L, "Quinteros", "Yuli", LocalDate.of(1990,10,17)));
        listStudents.add(new Student(2L, "Gomez", "Aldana", LocalDate.of(2008,05,28)));
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }


    @Test
    void getStudents() throws Exception {
        List<Student> studentList = new ArrayList<>();

        studentList.add(new Student(1L,"Quinteros","Yuli", LocalDate.of(1990,10,17)));
        studentList.add(new Student(2L,"Gomez","Aldana", LocalDate.of(2008,05,28)));
        when(studentService.findAll()).thenReturn(studentList);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$[0].surname").value("Quinteros"))
                .andExpect(jsonPath("$[1].surname").value("Gomez"));

    }
    @Test
    void getStudentById() throws Exception {
        Student student = new Student(1L, "Quinteros", "Yuliana", LocalDate.of(1990, 10, 17));

        when(studentService.findById(1L)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.surname").value("Quinteros"))
        ;
    }

    @Test
    void createStudent() throws Exception {
        Student newStudent = new Student(null, "Yuliana", "Quinteros", LocalDate.of(1990,10,17));
        Student createdStudent = new Student(1L, "Yuliana", "Quinteros", LocalDate.of(1990,10,17));

        when(studentService.createStudent(any())).thenReturn(createdStudent);
        mockMvc.perform(MockMvcRequestBuilders.post("/students/create").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.surname").value("Yuliana"));

    }

    @Test
    void updateStudent() throws Exception {
        Student newStudent = new Student(null, "Yuliana", "Quinteros", LocalDate.of(1990,10,17));
        Student updatedStudent = new Student(1L, "Yuli", "Quinteros", LocalDate.of(1990,10,17));

        when(studentService.updateStudent(any())).thenReturn(updatedStudent);

        mockMvc.perform(MockMvcRequestBuilders.put("/students/update").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.surname").value("Yuli"));

    }

    @Test
    void deleteStudent() throws Exception {
            doNothing().when(studentService).deleteStudent(1L);
            mockMvc.perform(MockMvcRequestBuilders.delete("/students/delete/{id}", 1L))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(content().string("Estudiante eliminado del listado"))
                    .andDo(print());
    }
}