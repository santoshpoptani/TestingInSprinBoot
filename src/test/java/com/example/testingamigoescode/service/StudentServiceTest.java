package com.example.testingamigoescode.service;

import com.example.testingamigoescode.entity.StudentEnity;
import com.example.testingamigoescode.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    private StudentService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private StudentRepository repository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getStudents() {
        //WHEN
        underTest.getStudents();

        //THEN
        verify(repository)
                .findAll();
    }

    @Test
    void getStudent() {
        //GIVEN
long id = 1;
        //WHEN
underTest.getStudent(id);
        //THEN
        verify(repository).findById(id);
    }

    @Test
    void saveStudent() {
        //GIVEN
        StudentEnity studentEnity = new StudentEnity(1,"san",20);
        //WHEN
        underTest.saveStudent(studentEnity);

        //THEN
        verify(repository).save(studentEnity);
    }
}