package com.example.testingamigoescode.DAO;

import com.example.testingamigoescode.AbstractTestContainer;
import com.example.testingamigoescode.entity.StudentEnity;
import com.example.testingamigoescode.utility.StudentRowmapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudentDAOJDBCimplTest extends AbstractTestContainer {

    private StudentDAOJDBCimpl undertest;
    private final StudentRowmapper rowmapper= new StudentRowmapper();

    @BeforeEach
    void setUp() {
        undertest = new StudentDAOJDBCimpl(
                getJDBCTemplate(),
                rowmapper
        );
    }

    @Test
    void insert() {
        //GIVEN
        String name = faker.name().fullName();
        StudentEnity studentEnity = new StudentEnity(
                name,
                20
        );

        //WHEN
        undertest.insert(studentEnity);
        StudentEnity actual = undertest.selectAllStudents()
                .stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow();
        //THEN
       assertThat(actual.getName().equals(name));
    }

    @Test
    void selectAllStudents() {
        //GIVEN
        StudentEnity studentEnity = new StudentEnity(
                faker.name().fullName(),
                20
        );
        undertest.insert(studentEnity);
        //WHEN
        List<StudentEnity> students = undertest.selectAllStudents();

        //THEN
        assertThat(students).isNotEmpty();
    }

    @Test
    void selectStudentById() {
        //GIVEN
        String name = faker.name().fullName();
        StudentEnity studentEnity = new StudentEnity(
                name,
                20
        );
        undertest.insert(studentEnity);

        int id = Math.toIntExact(undertest.selectAllStudents()
                .stream()
                        .filter(c->c.getName().equals(name))
                .map(StudentEnity::getId)
                .findFirst()
                .orElseThrow());

        //WHEN
        Optional<StudentEnity> actual = undertest.selectStudentById(id);
        //THEN
        assertThat(actual).isPresent().hasValueSatisfying(c->{
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(studentEnity.getName());
            assertThat(c.getAge()).isEqualTo(studentEnity.getAge());
        });
    }

    @Test
    void regectWithWrongId() {
        //GIVEN
int id = -1;
        //WHEN
        Optional<StudentEnity> acutal = undertest.selectStudentById(id);
        //THEN
        assertThat(acutal).isEmpty();
    }
}