package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.command.task.GetTasksByCourseCommand;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.receiver.CourseRegistrationReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

public class GetCourseCommand extends BaseCommand implements Command {
    private CourseReceiver receiver;
    private CommandResult result;

    public GetCourseCommand() {
        receiver = new CourseReceiver();
        result = new CommandResult();
        next = new GetTasksByCourseCommand();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        int course_id = Integer.valueOf(requestContent.getRequestAttributes().get("id").toString());
        int user_id = Integer.parseInt(requestContent.getSessionAttributes().get("user_id").toString());
        String role = requestContent.getSessionAttributes().get("role").toString();
        Course course = receiver.getById(course_id);
        if (course == null) {
            requestContent.setRequestAttribute("errorMessage", messages.getMessage("courseNotFound"));
        }else if (role.equals("teacher") && course.getTeacher().getId() != user_id) {
            requestContent.setRequestAttribute("errorMessage", messages.getMessage("courseNotFound"));
        }
        else {
            if (role.equals("student")) {
                CourseRegistrationReceiver courseRegistrationReceiver = new CourseRegistrationReceiver();
                boolean registered = courseRegistrationReceiver.isCourseRegistered(course_id, user_id);
                requestContent.setRequestAttribute("registered", registered);
            }
            requestContent.setRequestAttribute("course", course);
            next.execute(requestContent);
        }
        result.setPage(PageManager.getPage("fullpath.page.course"));
        return result;
    }
}
