package com.practico.utils;

import com.practico.model.Student;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;

public class StudentComparador implements Comparator<Student> {
    @Override
    public int compare(Student student1, Student student2) {
        int student1Year = Period.between(student1.getBirthday(), LocalDate.now()).getYears();
        int student2Year = Period.between(student2.getBirthday(), LocalDate.now()).getYears();
        return student1Year - student2Year;
    }
}


