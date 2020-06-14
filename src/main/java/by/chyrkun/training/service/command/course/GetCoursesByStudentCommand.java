package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.resource.ConfigurationManager;

import java.util.List;

public class GetCoursesByStudentCommand implements Command {
    private static final String COURSES = "courses";
    private static final String CHOSEN = "chosen";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult result = new CommandResult();
        List<Course> courses;
        boolean chosen = Boolean.parseBoolean(requestContent.getRequestAttributes().get("chosen").toString());
        int student_id = (Integer)requestContent.getSessionAttributes().get("user_id");
        CourseReceiver courseReceiver = new CourseReceiver();
        courses = courseReceiver.getByStudent(student_id, chosen);
        if (courses == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, "Courses not found");
        }else {
            requestContent.setRequestAttribute(COURSES, courses);
        }
        if (chosen) {
            result.setPage(ConfigurationManager.getProperty("fullpath.page.courses"));
        }

        return result;
    }
}
