package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class CreateCourseCommandTest {
    private RequestContent requestContent;

    @Mock private CourseReceiver courseReceiver;
    @Mock private UserReceiver userReceiver;

    @InjectMocks private CreateCourseCommand command;

    @BeforeEach
    void setUp() {
        String[] name = {"Course"};
        String[] teacher_id = {"1"};

        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setRequestParameter("name", name);
        requestContent.setRequestParameter("teacher_id", teacher_id);
    }

    @Test
    void testExecute() throws EntityNotFoundServiceException {
        Role role = new Role(1, "teacher");
        User user = new User(1, "Login", PasswordHasher.getHash("Password"), "Firstname", "Surname", role);
        Mockito.lenient().when(userReceiver.getById(Mockito.anyInt())).thenReturn(user);
        Mockito.lenient().when(courseReceiver.create(Mockito.any(Course.class))).thenReturn(true);
        CommandResult result = command.execute(requestContent);
        assertEquals(PageManager.getPage("shortpath.page.createcourse"), result.getPage());
        assertEquals(CommandResult.ResponseType.REDIRECT, result.getResponseType());
    }
}