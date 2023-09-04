package br.com.mateus.commercemanagementsystem.model.temp;

import java.util.List;

import jakarta.persistence.ManyToMany;

public class Course {
    
    @ManyToMany
    private List<Student> students;
}
