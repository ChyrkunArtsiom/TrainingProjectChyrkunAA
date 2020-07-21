package by.chyrkun.training.service.command.exercise;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.*;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.receiver.TaskRegistrationReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
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
class RegisterTaskCommandTest {
    private RequestContent requestContent;

    @Mock private TaskReceiver taskReceiver;
    @Mock private UserReceiver userReceiver;
    @Mock private TaskRegistrationReceiver taskRegistrationReceiver;

    @InjectMocks
    private RegisterTaskCommand command;

    @BeforeEach
    void setUp() {
        String[] task_id = {"1"};

        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setSessionAttribute("user_id", 2);
        requestContent.setRequestParameter("task_id", task_id);
    }

    @Test
    void testExecute() throws EntityNotFoundServiceException {
        Role role1 = new Role(1, "teacher");
        Role role2 = new Role(2, "student");
        User teacher = new User(1, "Teacher", "Password",
                "Firstname", "Surname", role1);
        User student = new User(2, "Student", "Password",
                "Firstname", "Surname", role2);
        Course course = new Course("Course", teacher);
        Task task = new Task(1, "Task", LocalDate.now(), LocalDate.now().plusDays(1), course);
        Mockito.lenient().when(userReceiver.getById(Mockito.anyInt())).thenReturn(student);
        Mockito.lenient().when(taskReceiver.getById(Mockito.anyInt())).thenReturn(task);
        Mockito.lenient().when(taskRegistrationReceiver.create(Mockito.any(Exercise.class))).thenReturn(true);
        CommandResult result = command.execute(requestContent);
        assertEquals(PageManager.getPage("shortpath.page.task") + "/" + task.getId(), result.getPage());
        assertEquals(CommandResult.ResponseType.REDIRECT, result.getResponseType());
    }
}