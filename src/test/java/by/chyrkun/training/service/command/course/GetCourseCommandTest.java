package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.task.GetTasksByCourseCommand;
import by.chyrkun.training.service.receiver.CourseReceiver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class GetCourseCommandTest {
    private RequestContent requestContent;

    @Mock
    private CourseReceiver courseReceiver;

    @Mock
    GetTasksByCourseCommand nextCommand;

    @InjectMocks
    private GetCourseCommand command;

    @BeforeEach
    void setUp() {
        requestContent = new RequestContent();
        requestContent.setRequestAttribute("id", 1);
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setSessionAttribute("role", "teacher");
        requestContent.setSessionAttribute("user_id", "1");
    }

    @Test
    void testExecute() {
        Role role = new Role(1, "teacher");
        User teacher = new User(1, "Login", "Password", "Firstname", "Surname", role);
        Course course = new Course("Course", teacher);
        Mockito.lenient().when(courseReceiver.getById(Mockito.anyInt())).thenReturn(course);
        command.execute(requestContent);
        Mockito.verify(nextCommand, Mockito.times(1)).execute(Mockito.any(RequestContent.class));
        assertEquals(course, requestContent.getRequestAttributes().get("course"));
    }
}