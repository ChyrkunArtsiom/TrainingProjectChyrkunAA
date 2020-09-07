package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.receiver.UserReceiver;
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
class LogInCommandTest {
    private RequestContent requestContent;

    @Mock
    private UserReceiver userReceiver;

    @InjectMocks
    private LogInCommand command;

    @BeforeEach
    void setUp() {
        String[] login = {"Login"};
        String[] password = {"Password"};

        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setRequestParameter("login", login);
        requestContent.setRequestParameter("password", password);
    }

    @Test
    void testExecute() {
        Role role = new Role(1, "teacher");
        User user = new User(1, "Login", PasswordHasher.getHash("Password"), "Firstname", "Surname", role);
        Mockito.lenient().when(userReceiver.getByLogin(Mockito.anyString())).thenReturn(user);
        CommandResult result = command.execute(requestContent);
        assertEquals(1, requestContent.getSessionAttributes().get("user_id"));
        assertEquals("Login", requestContent.getSessionAttributes().get("userName"));
        assertEquals("teacher", requestContent.getSessionAttributes().get("role"));
    }
}