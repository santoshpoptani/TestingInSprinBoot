package com.example.testingamigoescode.service;


import com.example.testingamigoescode.entity.StudentEnity;
import com.example.testingamigoescode.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<StudentEnity> getStudents() {
        return repository.findAll();
    }

    public Optional<StudentEnity> getStudent(long id) {
       return repository.findById(id);
    }

    public void saveStudent(StudentEnity studentEnity){
        repository.save(studentEnity);
    }
}
