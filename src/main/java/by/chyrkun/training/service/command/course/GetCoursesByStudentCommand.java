package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

import java.util.List;

public class GetCoursesByStudentCommand implements Command {
    private static final String COURSES = "courses";
    private static final String CHOSEN = "chosen";
    private static final String ERROR_MESSAGE = "errorMessage";
    private CourseReceiver receiver = new CourseReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.valueOf(requestContent.getSessionAttributes().get("lang").toString());
        CommandResult result = new CommandResult();
        List<Course> courses;
        boolean chosen = Boolean.parseBoolean(requestContent.getRequestAttributes().get("chosen").toString());
        int student_id = (Integer)requestContent.getSessionAttributes().get("user_id");
        courses = receiver.getByStudent(student_id, chosen);
        if (courses == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("coursesNotFound"));
        }else {
            requestContent.setRequestAttribute(COURSES, courses);
        }
        if (chosen) {
            result.setPage(PageManager.getProperty("fullpath.page.courses"));
        }

        return result;
    }
}
