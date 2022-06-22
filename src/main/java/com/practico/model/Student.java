package com.practico.model;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Estudiante")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "surname", length = 30)
    private String surname;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "birthday")
    private LocalDate birthday;


}
