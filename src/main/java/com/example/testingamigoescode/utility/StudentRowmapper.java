package com.example.testingamigoescode.utility;

import com.example.testingamigoescode.entity.StudentEnity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentRowmapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StudentEnity(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("age")
        );
    }
}
