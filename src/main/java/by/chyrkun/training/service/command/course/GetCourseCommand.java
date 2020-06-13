package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.resource.ConfigurationManager;
import by.chyrkun.training.service.util.RequestContentSetter;

public class GetCourseCommand implements Command {
    private static final String PARAM_COURSE_ID = "course_id";
    private static final String ERROR_MESSAGE = "errorMessage";
    CourseReceiver courseReceiver = new CourseReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        int course_id = Integer.parseInt(requestContent.getRequestParameters().get(PARAM_COURSE_ID)[0]);
        CommandResult result = new CommandResult();
        Course course = courseReceiver.getById(course_id);
        if (course == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, "Course not found");
        }else {
            requestContent.setRequestAttribute("course", course);
        }
        RequestContentSetter.showTasks(requestContent);
        result.setPage(ConfigurationManager.getProperty("fullpath.page.course"));
        return result;
    }
}
