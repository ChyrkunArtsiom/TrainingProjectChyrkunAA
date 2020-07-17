package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.receiver.TaskReceiver;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class GetTasksByCourseCommandTest {
    private RequestContent requestContent;

    @Mock
    private TaskReceiver taskReceiver;

    @InjectMocks
    private GetTasksByCourseCommand command;

    @BeforeEach
    void setUp() {
        requestContent = new RequestContent();
        requestContent.setRequestAttribute("id", 1);
    }

    @Test
    void testExecute() {
        Role role = new Role(1, "teacher");
        User teacher = new User(1, "Login", "Password",
                "Firstname", "Surname", role);
        Course course = new Course("Course", teacher);
        Task task1 = new Task(1, "Task", LocalDate.now(), LocalDate.now().plusDays(1), course);
        Task task2 = new Task(2, "Task2", LocalDate.now(), LocalDate.now().plusDays(5), course);
        List<Task> tasks = List.of(task1, task2);
        Mockito.lenient().when(taskReceiver.getByCourse(Mockito.anyInt())).thenReturn(tasks);
        command.execute(requestContent);
        assertEquals(tasks, requestContent.getRequestAttributes().get("tasks"));
    }
}