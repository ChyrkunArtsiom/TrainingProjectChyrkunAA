package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.task.GetTasksByCourseCommand;
import by.chyrkun.training.service.receiver.CourseReceiver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class GetCoursesCommandTest {
    private RequestContent requestContent;

    @Mock
    private CourseReceiver courseReceiver;

    @InjectMocks
    private GetCoursesCommand command;

    @BeforeEach
    void setUp() {
        requestContent = new RequestContent();
        requestContent.setSessionAttribute("lang", "en_US");
        requestContent.setSessionAttribute("user_id", 1);
        requestContent.setSessionAttribute("role", "teacher");
    }

    @Test
    void testExecute() {
        Role role = new Role(1, "teacher");
        User teacher = new User(1, "Login", "Password", "Firstname", "Surname", role);
        Course course1 = new Course("Course1", teacher);
        Course course2 = new Course("Course2", teacher);
        List<Course> courses = List.of(course1, course2);
        Mockito.lenient().when(courseReceiver.getByTeacher(Mockito.anyInt(), Mockito.anyInt())).thenReturn(courses);
        Mockito.lenient().when(courseReceiver.getPagesForTeacher(Mockito.anyInt())).thenReturn(1);
        command.execute(requestContent);
        assertEquals(courses, requestContent.getRequestAttributes().get("courses"));
    }
}