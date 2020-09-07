package by.chyrkun.training.service.command.course_registration;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.CourseRegistration;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.receiver.CourseRegistrationReceiver;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class RegisterCourseCommandTest {
    private RequestContent requestContent;

    @Mock private CourseRegistrationReceiver courseRegistrationReceiver;
    @Mock private UserReceiver userReceiver;
    @Mock private CourseReceiver courseReceiver;

    @InjectMocks
    private RegisterCourseCommand command;

    @BeforeEach
    void setUp() {
        String[] course_id = {"1"};
        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setSessionAttribute("user_id", 1);
        requestContent.setRequestParameter("course_id", course_id);
    }

    @Test
    void testExecute() throws EntityNotFoundServiceException {
        Role role = new Role(1, "student");
        User student = new User(1, "Login", PasswordHasher.getHash("Password"), "Firstname", "Surname", role);
        Course course = new Course(1, "Course1", student);
        Mockito.lenient().when(userReceiver.getById(Mockito.anyInt())).thenReturn(student);
        Mockito.lenient().when(courseReceiver.getById(Mockito.anyInt())).thenReturn(course);
        CommandResult result = command.execute(requestContent);
        Mockito.verify(courseRegistrationReceiver, Mockito.times(1)).create(Mockito.any(CourseRegistration.class));
        assertEquals(PageManager.getPage("shortpath.page.course") + "/" + course.getId(), result.getPage());
        assertEquals(CommandResult.ResponseType.REDIRECT, result.getResponseType());
    }
}