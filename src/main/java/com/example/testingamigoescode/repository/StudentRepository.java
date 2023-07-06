package com.example.testingamigoescode.repository;


import com.example.testingamigoescode.entity.StudentEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEnity,Long> {

}
