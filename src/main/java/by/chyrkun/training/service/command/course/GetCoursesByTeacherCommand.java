package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.resource.PageManager;

import java.util.List;

public class GetCoursesByTeacherCommand implements Command {
    private static final String COURSES = "courses";
    private static final String ERROR_MESSAGE = "errorMessage";
    private CourseReceiver receiver = new CourseReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult result = new CommandResult();
        List<Course> courses;
        int teacher_id = (Integer)requestContent.getSessionAttributes().get("user_id");
        courses = receiver.getByTeacher(teacher_id);
        if (courses == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, "Courses not found");
        }else {
            requestContent.setRequestAttribute(COURSES, courses);
        }
        result.setPage(PageManager.getProperty("fullpath.page.courses"));
        return result;
    }
}
