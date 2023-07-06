package com.example.testingamigoescode.DAO;


import com.example.testingamigoescode.entity.StudentEnity;
import com.example.testingamigoescode.utility.StudentRowmapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "jdbc")
public class StudentDAOJDBCimpl implements StudentDAO{
    private final JdbcTemplate jdbcTemplate;
    private  final StudentRowmapper studentRowmapper;
    public StudentDAOJDBCimpl(JdbcTemplate jdbcTemplate, StudentRowmapper studentRowmapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentRowmapper = studentRowmapper;
    }

    @Override
    public int insert(StudentEnity studentEnity) {
        var sql = """
                INSERT INTO student(name,age) VALUES(?,?)
                """;
        int update = jdbcTemplate.update(
                sql,
                studentEnity.getName(),
                studentEnity.getAge()
        );
        System.out.println(update);
        return update;
    }

    @Override
    public List<StudentEnity> selectAllStudents() {

        var sql = """
                SELECT * FROM student
                """;

        return jdbcTemplate.query(sql, studentRowmapper);


      /*

      Make utility class of rowmapper and put this code in there and inject Row mapper object in this class

      RowMapper<StudentEnity> studentEnityRowMapper = (rs, rowNum) -> {
            StudentEnity studentEnity = new StudentEnity(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age")
            );
            return studentEnity;
        };*/

    }

    @Override
    public Optional<StudentEnity> selectStudentById(int id) {
        var sql = """
                SELECT * FROM student
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql,studentRowmapper,id)
                .stream()
                .findFirst();

    }
}
