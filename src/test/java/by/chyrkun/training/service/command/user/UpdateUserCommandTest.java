package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.role.GetRolesCommand;
import by.chyrkun.training.service.receiver.RoleReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.PageManager;
import org.junit.jupiter.api.AfterEach;
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
class UpdateUserCommandTest {
    private RequestContent requestContent;

    @Mock
    private RoleReceiver roleReceiver;
    @Mock private UserReceiver userReceiver;

    @InjectMocks
    private UpdateUserCommand command;

    @BeforeEach
    void setUp() {
        String[] user_id = {"1"};
        String[] login = {"NewTest"};
        String[] old_login = {"Test"};
        String[] firstname = {"Firstname"};
        String[] secondname = {"Surname"};
        String[] role_name = {"teacher"};

        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setRequestParameter("login", login);
        requestContent.setRequestParameter("old_username", old_login);
        requestContent.setRequestParameter("firstname", firstname);
        requestContent.setRequestParameter("secondname", secondname);
        requestContent.setRequestParameter("user_id", user_id);
        requestContent.setSessionAttribute("user_id", 1);
        requestContent.setSessionAttribute("role", "admin");
        requestContent.setRequestParameter("role_name", role_name);

    }


    @Test
    void execute() {
        Role role = new Role(2, "teacher");
        User user = new User(1, "Test", "Firstname", "Surname", new Role(1, "admin"));
        Mockito.lenient().when(roleReceiver.getByName(Mockito.anyString())).thenReturn(role);
        Mockito.lenient().when(userReceiver.update(Mockito.any(User.class))).
                thenReturn(user);
        CommandResult result = command.execute(requestContent);
        assertEquals("teacher", requestContent.getSessionAttributes().get("role"));
        assertEquals(PageManager.getPage("shortpath.page.profile") + "/" + 1, result.getPage());
    }
}