package com.example.testingamigoescode.journeys;

import com.example.testingamigoescode.entity.StudentEnity;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class StudentIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    public static String STUDENT_URI = "/api/v1/student";

    @Test
    void canRegisterTheStudent() {
        Faker faker = new Faker();
        String name = faker.name().fullName();
        int age = faker.number().numberBetween(20, 50);
        StudentEnity enity = new StudentEnity(name, age);

        //Post Request using webTestClient

        webTestClient.post()
                .uri(STUDENT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(enity), StudentEnity.class)
                .exchange()
                .expectStatus()
                .isOk();

        //Get all Students

        List<StudentEnity> GetAllStudent = webTestClient.get()
                .uri(STUDENT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<StudentEnity>() {
                })
                .returnResult()
                .getResponseBody();

        StudentEnity expected = new StudentEnity(name, age);

        assertThat(GetAllStudent)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expected);

        var id = GetAllStudent.stream()
                .filter(s -> s.getName().equals(name))
                .map(StudentEnity::getId)
                .findFirst()
                .orElseThrow();

        expected.setId(id);

        webTestClient.get()
                .uri(STUDENT_URI + "/{studentId}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<StudentEnity>() {})
                .isEqualTo(expected);

    }
}
