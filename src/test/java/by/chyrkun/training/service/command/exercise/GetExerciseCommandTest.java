package by.chyrkun.training.service.command.exercise;

import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.*;
import by.chyrkun.training.service.receiver.ExerciseReceiver;
import by.chyrkun.training.service.util.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class GetExerciseCommandTest {
    private RequestContent requestContent;

    @Mock
    private ExerciseReceiver receiver;

    @InjectMocks
    private GetExerciseCommand command;

    @BeforeEach
    void setUp() {
        requestContent = new RequestContent();
        requestContent.setSessionAttribute("user_id", 2);
        requestContent.setSessionAttribute("role", "student");
        requestContent.setRequestAttribute("id", 1);
    }

    @Test
    void testExecute() {
        Role role1 = new Role(1, "teacher");
        Role role2 = new Role(2, "student");
        User teacher = new User(1, "Teacher", PasswordHasher.getHash("Password"),
                "Firstname", "Surname", role1);
        User student = new User(2, "Student", PasswordHasher.getHash("Password"),
                "Firstname", "Surname", role2);
        Course course = new Course("Course", teacher);
        Task task = new Task(1, "Task", LocalDate.now(), LocalDate.now().plusDays(1), course);
        Exercise registration = new Exercise(task, student);
        Mockito.lenient().when(receiver.getById(Mockito.anyInt())).thenReturn(registration);
        command.execute(requestContent);
        assertEquals(registration, requestContent.getRequestAttributes().get("exercise"));
    }
}