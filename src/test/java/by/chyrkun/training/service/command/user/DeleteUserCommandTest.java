package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
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
class DeleteUserCommandTest {
    private RequestContent requestContent;

    @Mock
    private UserReceiver userReceiver;

    @InjectMocks
    private DeleteUserCommand command;

    @BeforeEach
    void setUp() {
        String[] username = {"Login"};

        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setRequestParameter("login", username);
    }

    @Test
    void testExecute() {
        Mockito.lenient().when(userReceiver.delete(Mockito.anyString())).thenReturn(true);
        CommandResult result = command.execute(requestContent);
        assertEquals(PageManager.getProperty("shortpath.page.admin.deleteuser"), result.getPage());
        assertEquals(CommandResult.ResponseType.REDIRECT, result.getResponseType());
    }
}