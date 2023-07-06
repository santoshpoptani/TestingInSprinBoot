package com.example.testingamigoescode;

import com.example.testingamigoescode.entity.StudentEnity;
import com.example.testingamigoescode.service.BusinessLayer;
import com.example.testingamigoescode.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusinessLayerTest {

    @Mock
    private StudentService studentService;
    private BusinessLayer underTest;

    @BeforeEach
    void setUp() {
        underTest = new BusinessLayer(studentService);
    }

    @Test
    void selectAllStudent() {
        //WHEN
        underTest.selectAllStudent();
        //THEN
        verify(studentService).getStudents();
    }

    @Test
    void canGetStudentwithId() {
        //GIVEN
        int id = 10;
        StudentEnity studentEnity = new StudentEnity(id, "san", 20);
        when(studentService.getStudent(id)).thenReturn(Optional.of(studentEnity));
        //WHEN
        StudentEnity actual = underTest.selectStudentById(id);
        //THEN
        assertThat(actual).isEqualTo(studentEnity);
    }

    @Test
    void willThrowExceptioWhenStudentReturnEmptyOptional() {
        //GIVEN
        int id = 10;
       ;
        when(studentService.getStudent(id)).thenReturn(Optional.empty());
        //WHEN

        //THEN
        assertThatThrownBy(()->underTest.selectStudentById(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(
                        "Student Not Found"
                );
    }
}