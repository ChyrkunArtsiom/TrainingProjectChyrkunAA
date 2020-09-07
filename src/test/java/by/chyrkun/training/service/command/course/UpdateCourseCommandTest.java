package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.PageManager;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UpdateCourseCommandTest {
    private RequestContent requestContent;

    @Mock
    CourseReceiver courseReceiver;

    @Mock
    UserReceiver userReceiver;

    @InjectMocks
    UpdateCourseCommand command;

    @BeforeEach
    void setUp() {
        String[] course_id = {"1"};
        String[] course_name = {"Course after updating"};
        String[] teacher_id = {"1"};

        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setRequestParameter("course_id", course_id);
        requestContent.setRequestParameter("course_name", course_name);
        requestContent.setRequestParameter("teacher_id", teacher_id);
    }

    @Test
    void testExecute() {
        Role role = new Role(1, "teacher");
        User teacher = new User(1, "TestTeacher", PasswordHasher.getHash("Password"), "FN", "SN", role);
        Course course = new Course(1, "Course before updating", teacher);
        Mockito.lenient().when(courseReceiver.getById(Mockito.anyInt())).thenReturn(course);
        Mockito.lenient().when(userReceiver.getById(Mockito.anyInt())).thenReturn(teacher);
        Mockito.lenient().when(courseReceiver.update(Mockito.any(Course.class))).thenReturn(course);
        CommandResult result = command.execute(requestContent);
        assertEquals(CommandResult.ResponseType.REDIRECT, result.getResponseType());
        assertEquals(PageManager.getPage("shortpath.page.course") + "/" + "1", result.getPage());
    }
}