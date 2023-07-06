package com.example.testingamigoescode.endpoints;

import com.example.testingamigoescode.entity.StudentEnity;
import com.example.testingamigoescode.service.BusinessLayer;
import com.example.testingamigoescode.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class endpoints {
    private final BusinessLayer businessLayer;

    public endpoints(BusinessLayer businessLayer) {
        this.businessLayer = businessLayer;

    }

    @GetMapping
    public List<StudentEnity> getallstudents(){
        return businessLayer.selectAllStudent();
    }

    @GetMapping("/{studentId}")
    public StudentEnity getstudent(@PathVariable("studentId") int studentId){
        return businessLayer.selectStudentById(studentId);
    }

    @PostMapping
    public void savestudent(@RequestBody StudentEnity studentEnity){
        businessLayer.insertStudent(studentEnity);
    }
}
