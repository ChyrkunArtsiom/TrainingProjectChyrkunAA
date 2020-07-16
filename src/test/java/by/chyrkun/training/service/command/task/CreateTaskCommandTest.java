package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.course.CreateCourseCommand;
import by.chyrkun.training.service.command.course.GetCoursesCommand;
import by.chyrkun.training.service.command.role.GetRolesCommand;
import by.chyrkun.training.service.command.user.CreateUserCommand;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.receiver.RoleReceiver;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class CreateTaskCommandTest {
    private RequestContent requestContent;

    @Mock private TaskReceiver taskReceiver;
    @Mock private CourseReceiver courseReceiver;
    @Mock private GetCoursesCommand nextCommand;

    @InjectMocks private CreateTaskCommand command;

    @BeforeEach
    void setUp() {
        String[] name = {"Task"};
        String[] course_id = {"1"};
        String[] startdate = {LocalDate.now().toString()};
        String[] deadline = {LocalDate.now().plusDays(1).toString()};

        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setRequestParameter("name", name);
        requestContent.setRequestParameter("course_id", course_id);
        requestContent.setRequestParameter("startdate", startdate);
        requestContent.setRequestParameter("deadline", deadline);
    }

    @Test
    void execute() throws EntityNotFoundServiceException {
        Role role = new Role(1, "teacher");
        User teacher = new User(1, "Login", "Password",
                "Firstname", "Surname", role);
        Course course = new Course("Course", teacher);
        Mockito.lenient().when(courseReceiver.getById(Mockito.anyInt())).thenReturn(course);
        Mockito.lenient().when(taskReceiver.create(Mockito.any(Task.class))).thenReturn(true);
        command.execute(requestContent);
        assertEquals(requestContent.getRequestParameters().get("name")[0],
                requestContent.getRequestAttributes().get("name").toString());
        assertEquals(requestContent.getRequestParameters().get("course_id")[0],
                requestContent.getRequestAttributes().get("course_id").toString());
        assertEquals(requestContent.getRequestParameters().get("startdate")[0],
                requestContent.getRequestAttributes().get("startdate").toString());
        assertEquals(requestContent.getRequestParameters().get("deadline")[0],
                requestContent.getRequestAttributes().get("deadline").toString());
    }
}