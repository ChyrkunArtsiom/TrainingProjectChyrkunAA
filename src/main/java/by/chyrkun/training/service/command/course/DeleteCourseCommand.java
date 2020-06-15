package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.resource.MessageManager;

public class DeleteCourseCommand implements Command {
    private static final String PARAM_COURSE_ID = "course_id";
    private static final String MESSAGE = "message";
    private CourseReceiver receiver = new CourseReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.EN;
        CommandResult result = new CommandResult();
        String course_id = requestContent.getRequestParameters().get(PARAM_COURSE_ID)[0];
        if (receiver.delete(Integer.parseInt(course_id))) {
            requestContent.setSessionAttribute(MESSAGE, messages.getMessage("courseWasDeleted"));
        }
        else {
            requestContent.setSessionAttribute(MESSAGE, messages.getMessage("courseNotFound"));
        }
        result.setPage(PageManager.getProperty("shortpath.page.teacher.courses"));
        result.setResponseType(CommandResult.ResponseType.REDIRECT);
        requestContent.setRequestAttribute(PARAM_COURSE_ID, course_id);
        return result;
    }
}
