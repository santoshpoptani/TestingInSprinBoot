package com.example.testingamigoescode.DAO;


import com.example.testingamigoescode.entity.StudentEnity;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {

     int insert(StudentEnity studentEnity);
     List<StudentEnity> selectAllStudents();
     Optional<StudentEnity> selectStudentById(int id);

}
