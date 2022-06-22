package com.practico.utils;
import com.practico.model.Student;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Comparador {
    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "Quinteros", "Yuli", LocalDate.of(1990, 10, 17)));
        studentList.add(new Student(2L, "Gomez", "Aldana", LocalDate.of(2008, 05, 28)));
        studentList.add(new Student(3L, "Talab", "Yasmin", LocalDate.of(2010, 03, 13)));

        java.util.Comparator<Student> comparator = (student1, student2) -> Period.between(student1.getBirthday(), LocalDate.now()).getYears()
                - Period.between(student2.getBirthday(), LocalDate.now()).getYears();

        System.out.println(comparator.compare(studentList.get(0), studentList.get(1)));
    }
}