package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.User;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class GetUserCommandTest {
    private RequestContent requestContent;

    @Mock
    private UserReceiver userReceiver;

    @InjectMocks
    private GetUserCommand command;

    @BeforeEach
    void setUp() {
        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setRequestAttribute("id", 1);
    }

    @Test
    void testExecute() {
        User user = new User("Login", "Password", "Firstname", "Surname");
        Mockito.lenient().when(userReceiver.getById(Mockito.anyInt())).
                thenReturn(user);
        CommandResult result = command.execute(requestContent);
        assertEquals(user, requestContent.getRequestAttributes().get("user"));
        assertEquals(PageManager.getProperty("fullpath.page.profile"), result.getPage());
    }
}