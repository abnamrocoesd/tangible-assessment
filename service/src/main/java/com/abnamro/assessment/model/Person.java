package com.abnamro.assessment.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Person
 */
@Table(name = "person")
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private LocalDate birthDate;

}