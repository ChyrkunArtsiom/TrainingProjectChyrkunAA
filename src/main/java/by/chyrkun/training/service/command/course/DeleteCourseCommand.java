package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

public class DeleteCourseCommand implements Command {
    private static final String PARAM_COURSE_ID = "course_id";
    private static final String MESSAGE = "message";
    private CourseReceiver receiver;
    private CommandResult result;

    public DeleteCourseCommand() {
        receiver = new CourseReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        String course_id = requestContent.getRequestParameters().get(PARAM_COURSE_ID)[0];
        if (!receiver.delete(Integer.parseInt(course_id))) {
            requestContent.setSessionAttribute(MESSAGE, messages.getMessage("courseNotFound"));
        }
        result.setPage(PageManager.getPage("shortpath.page.teacher.courses"));
        result.setResponseType(CommandResult.ResponseType.REDIRECT);
        requestContent.setRequestAttribute(PARAM_COURSE_ID, course_id);
        return result;
    }
}
