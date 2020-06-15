package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.command.task.GetTasksByCourseCommand;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.receiver.CourseRegistrationReceiver;
import by.chyrkun.training.service.resource.PageManager;

public class GetCourseCommand extends BaseCommand implements Command {
    private static final String PARAM_COURSE_ID = "course_id";
    private static final String ERROR_MESSAGE = "errorMessage";
    private CourseReceiver receiver = new CourseReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        int course_id = Integer.parseInt(requestContent.getRequestParameters().get(PARAM_COURSE_ID)[0]);
        int student_id = Integer.parseInt(requestContent.getSessionAttributes().get("user_id").toString());
        CommandResult result = new CommandResult();
        Course course = receiver.getById(course_id);
        if (course == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, "Course not found");
        }else {
            CourseRegistrationReceiver courseRegistrationReceiver = new CourseRegistrationReceiver();
            boolean registered = courseRegistrationReceiver.isCourseRegistered(course_id, student_id);
            requestContent.setRequestAttribute("registered", registered);
            requestContent.setRequestAttribute("course", course);
            setNext(new GetTasksByCourseCommand());
            next.execute(requestContent);
        }
        result.setPage(PageManager.getProperty("fullpath.page.course"));
        return result;
    }
}
