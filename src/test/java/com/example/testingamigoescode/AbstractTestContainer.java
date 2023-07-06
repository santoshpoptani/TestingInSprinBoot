package com.example.testingamigoescode;

import net.datafaker.Faker;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public abstract class AbstractTestContainer {

    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().dataSource(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        ).load();
        flyway.migrate();
    }

    @Container
    protected static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("student-dao-unit-test")
                    .withUsername("santosh")
                    .withPassword("password");

    @DynamicPropertySource
    private static void regertoDataSourceProperties(DynamicPropertyRegistry register) {
        register.add(
                "spring.datasource.url",
                postgreSQLContainer::getJdbcUrl
        );
        register.add(
                "spring.datasource.username",
                postgreSQLContainer::getUsername
        );

        register.add(
                "spring.datasource.password",
                postgreSQLContainer::getPassword
        );
    }

    private  static DataSource getdataSource(){
        return DataSourceBuilder.create()
                .driverClassName(postgreSQLContainer.getDriverClassName())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .url(postgreSQLContainer.getJdbcUrl())
                .build();
    }

    protected JdbcTemplate getJDBCTemplate(){
        return new JdbcTemplate(getdataSource());
    }

    protected static Faker faker = new Faker();

}
