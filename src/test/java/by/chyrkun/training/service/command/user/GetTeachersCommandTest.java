package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
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
class GetTeachersCommandTest {
    private RequestContent requestContent;

    @Mock
    private UserReceiver userReceiver;

    @InjectMocks
    private GetTeachersCommand command;

    @BeforeEach
    void setUp() {
        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
    }

    @Test
    void testExecute() {
        MessageManager messages = MessageManager.en_US;
        Mockito.lenient().when(userReceiver.getTeachers()).thenReturn(null);
        CommandResult result = command.execute(requestContent);
        assertEquals(messages.getMessage("teachersNotFound"), requestContent.getRequestAttributes().get("errorMessage"));
        assertEquals(PageManager.getProperty("fullpath.page.createcourse"), result.getPage());
    }
}