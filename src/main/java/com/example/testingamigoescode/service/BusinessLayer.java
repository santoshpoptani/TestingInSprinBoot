package com.example.testingamigoescode.service;

import com.example.testingamigoescode.entity.StudentEnity;
import com.example.testingamigoescode.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessLayer {

    private final StudentService studentService;

    public BusinessLayer(StudentService studentService) {
        this.studentService = studentService;
    }

    public List<StudentEnity> selectAllStudent(){
        return studentService.getStudents();
    }

    public StudentEnity selectStudentById(Integer id){
        return studentService.getStudent(id)
                .orElseThrow(()->new IllegalStateException("Student Not Found"));
    }

    public  void insertStudent(StudentEnity studentEnity){
        studentService.saveStudent(studentEnity);
    }
}
