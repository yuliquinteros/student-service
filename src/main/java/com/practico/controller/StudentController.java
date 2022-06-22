package com.practico.controller;


import com.practico.Exceptions.NoEntityException;
import com.practico.model.Student;
import com.practico.service.StudentService;
import org.aspectj.lang.annotation.DeclareError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

        //Consigna: Obtener un estudiante
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id){

        try{
            Student student = studentService.findById(id);
            return ResponseEntity.ok(student);
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST + "Estudiante no encontrado en el listado ");

        }
    }
    //Consigna: Obtener todos los estudiantes
    @GetMapping("/list")
    public ResponseEntity<List<Student>> getStudents(){
        return ResponseEntity.ok(studentService.findAll());
    }

    //Consigna: Crear un estudiante
    @PostMapping(value = "/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student stu = studentService.createStudent(student);
        return new ResponseEntity<Student>(stu, HttpStatus.CREATED);
    }

    //Consigna: Actualizar un estudiante
    @PutMapping("/update")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student stu = new Student();
        try {
            stu = studentService.updateStudent(student);
            return ResponseEntity.ok(stu);
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).body(stu);
        }
    }
    //Consigna: Eliminar un estudiante
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id){
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Estudiante eliminado del listado");
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST + "El Estudiante NO pudo ser eliminado");
        }
    }


    //Consigna: calcular la edad promedio de los estudiantes
    @GetMapping("/edadpromedio")
    public int getAverageAge(){
        List<Student> studentList = studentService.findAll();
        return (int) studentList.stream().mapToInt(objeto -> Period.between(objeto.getBirthday(),LocalDate.now()).getYears())
                .average().orElseThrow();
    }

    //Consigna: mostrar mayor y menor edad
    @GetMapping("/mayoredad")
    public Student getGreater(){
        List<Student> studentList = studentService.findAll();
        return studentList.stream().max((a1, a2) -> Period.between(a1.getBirthday(), LocalDate.now()).getYears() -
                Period.between(a2.getBirthday(), LocalDate.now()).getYears()).orElseThrow();
    }

    @GetMapping("/menoredad")
    public List<Student> getMinor(){
        List<Student> studentList = studentService.findAll();

        Comparator<? super Student> comparatorYear = null;
        return studentList.stream().filter(y -> Period.between(y.getBirthday(), LocalDate.now()).getYears()<18).
                sorted(comparatorYear).limit(3).collect(Collectors.toList());
    }

    //Consigna: Listar estudiantes mayores de edad >18
    @GetMapping("/mayores18")
    public List<Student> getGreaters(){
        List<Student> studentList = studentService.findAll();
        return studentList.stream().filter(objeto -> Period.between(objeto.getBirthday(),LocalDate.now()).getYears()>=18 )
                .collect(Collectors.toList());
    }
    //Consigna: Listar estudiantes menores de edad <18
    @GetMapping("/menores18")
    public List<Student> getMinors(){
        List<Student> studentList = studentService.findAll();
        return studentList.stream().filter(objeto -> Period.between(objeto.getBirthday(),LocalDate.now()).getYears()<18 )
                .sorted(Comparator.comparing(Student::getSurname))
                .collect(Collectors.toList());
    }
    Comparator<Student> comparatorYear = (a1, a2) -> Period.between(a2.getBirthday(), LocalDate.now()).getYears() -
            Period.between(a1.getBirthday(), LocalDate.now()).getYears();

}
