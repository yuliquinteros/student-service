package com.practico.service;


import com.practico.Exceptions.NoEntityException;
import com.practico.model.Student;
import com.practico.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student findById(Long id) throws NoEntityException {
        return studentRepository.findById(id).orElseThrow(() -> new NoEntityException("No existe estudiante con el id " + id + " solicitado"));
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) throws NoEntityException {
        Student stuOld = studentRepository.findById(student.getId()).orElseThrow(
                () -> new NoEntityException("No existe estudiante con " + student.getId() + " solicitado"));
        stuOld.setSurname(student.getSurname());
        stuOld.setName(student.getName());
        stuOld.setBirthday(student.getBirthday());
        return studentRepository.save(stuOld);
    }

    public void deleteStudent(Long id) throws NoEntityException {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new NoEntityException("El Estudiante con el id " + id + " no existe."));
        studentRepository.delete(student);
    }

}
