package by.chyrkun.training.service.command.exercise;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.receiver.TaskRegistrationReceiver;
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
class DeleteExerciseCommandTest {
    private RequestContent requestContent;

    @Mock
    private TaskRegistrationReceiver receiver;

    @InjectMocks
    private DeleteTaskRegistrationCommand command;

    @BeforeEach
    void setUp() {
        String[] exercise_id = {"1"};
        String[] task_id = {"1"};

        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setRequestParameter("exercise_id", exercise_id);
        requestContent.setRequestParameter("task_id", task_id);
    }

    @Test
    void testExecute() {
        Mockito.lenient().when(receiver.delete(Mockito.anyInt())).thenReturn(true);
        CommandResult result = command.execute(requestContent);
        assertEquals(PageManager.getPage("shortpath.page.task") + "/" + "1", result.getPage());
        assertEquals(CommandResult.ResponseType.REDIRECT, result.getResponseType());
    }
}