package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.receiver.CourseReceiver;
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
class DeleteCourseCommandTest {
    private RequestContent requestContent;

    @Mock
    private CourseReceiver courseReceiver;

    @InjectMocks
    private DeleteCourseCommand command;

    @BeforeEach
    void setUp() {
        String[] course_id = {"1"};

        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setRequestParameter("course_id", course_id);
    }

    @Test
    void testExecute() {
        Mockito.lenient().when(courseReceiver.delete(Mockito.anyInt())).thenReturn(true);
        CommandResult result = command.execute(requestContent);
        assertEquals(PageManager.getPage("shortpath.page.teacher.courses"), result.getPage());
        assertEquals(CommandResult.ResponseType.REDIRECT, result.getResponseType());
    }
}