package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

import java.util.List;

public class GetCoursesCommand extends BaseCommand implements Command {
    private static final String COURSES = "courses";
    private static final String ERROR_MESSAGE = "errorMessage";
    private CourseReceiver receiver = new CourseReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.valueOf(requestContent.getSessionAttributes().get("lang").toString());
        CommandResult result = new CommandResult();
        List<Course> courses = null;
        int id = (Integer)requestContent.getSessionAttributes().get("user_id");
        Object select = requestContent.getRequestAttributes().get("select");
        String role = requestContent.getSessionAttributes().get("role").toString();
        switch (role) {
            case "teacher": {
                courses = receiver.getByTeacher(id);
                break;
            }
            case "student": {
                boolean chosen = Boolean.parseBoolean(requestContent.getRequestAttributes().get("chosen").toString());
                courses = receiver.getByStudent(id, chosen);
                break;
            }
        }
        if (courses == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("coursesNotFound"));
        }else {
            requestContent.setRequestAttribute(COURSES, courses);
        }
        if (select != null && select.equals("for_task")) {
            result.setPage(PageManager.getProperty("fullpath.page.createtask"));
        } else {
            result.setPage(PageManager.getProperty("fullpath.page.courses"));
        }
        return result;
    }
}
