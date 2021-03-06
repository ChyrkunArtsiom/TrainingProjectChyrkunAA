package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.role.GetRolesCommand;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;
import by.chyrkun.training.service.receiver.RoleReceiver;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class CreateUserCommandTest {
    private RequestContent requestContent;

    @Mock private RoleReceiver roleReceiver;
    @Mock private UserReceiver userReceiver;
    @Mock private GetRolesCommand nextCommand;

    @InjectMocks private CreateUserCommand command;

    @BeforeEach
    void setUp() {
        String[] username = {"Login"};
        String[] password = {"Password"};
        String[] firstname = {"Firstname"};
        String[] secondname = {"Surname"};
        String[] role_id = {"1"};

        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setRequestParameter("login", username);
        requestContent.setRequestParameter("password", password);
        requestContent.setRequestParameter("firstname", firstname);
        requestContent.setRequestParameter("secondname", secondname);
        requestContent.setRequestParameter("role_id", role_id);
    }

    @Test
    void testExecute() throws EntityNotFoundServiceException {
        Mockito.lenient().when(roleReceiver.getById(Mockito.anyInt())).thenReturn(new Role(1, "student"));
        Mockito.lenient().when(userReceiver.create(Mockito.any(User.class))).thenReturn(true);
        CommandResult result = command.execute(requestContent);
        Mockito.verify(nextCommand, Mockito.times(1)).execute(Mockito.any(RequestContent.class));
        assertEquals(requestContent.getRequestParameters().get("login")[0], requestContent.getRequestAttributes().get("login").toString());
        assertEquals(PageManager.getPage("shortpath.page.admin.createuser"), result.getPage());
        assertEquals(CommandResult.ResponseType.REDIRECT, result.getResponseType());


    }
}
