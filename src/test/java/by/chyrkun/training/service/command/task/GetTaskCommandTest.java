package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.receiver.TaskRegistrationReceiver;
import by.chyrkun.training.service.resource.PageManager;
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
class GetTaskCommandTest {
    private RequestContent requestContent;

    @Mock
    private TaskReceiver taskReceiver;
    @Mock
    private TaskRegistrationReceiver taskRegistrationReceiver;

    @InjectMocks
    private GetTaskCommand command;

    @BeforeEach
    void setUp() {
        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setSessionAttribute("user_id", 1);
        requestContent.setSessionAttribute("role", "teacher");
        requestContent.setRequestAttribute("id", 1);
    }

    @Test
    void testExecute() {
        Role role = new Role(1, "teacher");
        User teacher = new User(1, "Login", "Password",
                "Firstname", "Surname", role);
        Course course = new Course("Course", teacher);
        Task task = new Task(1, "Task", LocalDate.now(), LocalDate.now().plusDays(1), course);
        Mockito.lenient().when(taskReceiver.getById(Mockito.anyInt())).thenReturn(task);
        Mockito.lenient().when(taskRegistrationReceiver.getAllByTask(Mockito.anyInt())).thenReturn(null);
        CommandResult result = command.execute(requestContent);
        assertEquals(task, requestContent.getRequestAttributes().get("task"));
        assertEquals(PageManager.getPage("fullpath.page.task"), result.getPage());
    }
}