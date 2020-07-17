package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.receiver.TaskReceiver;
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
class DeleteTaskCommandTest {
    private RequestContent requestContent;

    @Mock
    private TaskReceiver receiver;

    @InjectMocks
    private DeleteTaskCommand command;

    @BeforeEach
    void setUp() {
        String[] task_id = {"1"};
        String[] course_id = {"1"};

        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setRequestParameter("task_id", task_id);
        requestContent.setRequestParameter("course_id", course_id);
    }

    @Test
    void execute() {
        Role role = new Role(1, "teacher");
        User teacher = new User(1, "Login", "Password",
                "Firstname", "Surname", role);
        Course course = new Course(1, "Course", teacher);
        Task task = new Task(1, "Task", LocalDate.now(), LocalDate.now().plusDays(1), course);
        Mockito.lenient().when(receiver.getById(Mockito.anyInt())).thenReturn(task);
        Mockito.lenient().when(receiver.delete(Mockito.anyInt())).thenReturn(true);
        CommandResult result = command.execute(requestContent);
        assertEquals(PageManager.getProperty("shortpath.page.course") + "/" + task.getCourse().getId(), result.getPage());
        assertEquals(CommandResult.ResponseType.REDIRECT, result.getResponseType());
    }
}