package com.practico.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    Student student;
    @BeforeEach
    void setUp() {
        student = new Student(1L, "Quinteros", "Yuliana", LocalDate.of(1990, 10, 17));
   }

    @Test
    void getId() {
        assertEquals(1, student.getId());
    }

    @Test
    void getSurname() {
        assertEquals("Quinteros", student.getSurname());

    }

    @Test
    void getName() {
        assertEquals("Yuliana",student.getName());
    }

    @Test
    void getBirthday() {
        assertEquals(LocalDate.of(1990,10,17), student.getBirthday());
    }

    @Test
    void setId() {
        assertEquals(1,student.getId());
    }

    @Test
    void setSurname() {
        student.setSurname("Sardelli");
        assertEquals("Sardelli", student.getSurname());

    }

    @Test
    void setName() {
        student.setName("Patricio");
        assertEquals("Patricio", student.getName());

    }

    @Test
    void setBirthday() {
        assertEquals(LocalDate.of(1990,10,17) ,student.getBirthday());
    }

    @Test
    void testToString() {
       assertEquals("Student(id=1, surname=Quinteros, name=Yuliana, birthday=1990-10-17)", student.toString());
       }
}