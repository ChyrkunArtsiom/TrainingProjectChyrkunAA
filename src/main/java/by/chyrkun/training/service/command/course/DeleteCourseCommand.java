package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.resource.ConfigurationManager;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.validator.ParamValidator;

public class DeleteCourseCommand implements Command {
    private static final String PARAM_COURSE_ID = "course_id";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE = "message";
    private CourseReceiver receiver = new CourseReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.EN;
        CommandResult result = new CommandResult();
        String course_id = requestContent.getRequestParameters().get(PARAM_COURSE_ID)[0];
        if (!ParamValidator.isPresent(course_id)) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
            result.setPage(ConfigurationManager.getProperty("fullpath.page.deletecourse"));
        }
        else{
            if (receiver.delete(Integer.parseInt(course_id))) {
                requestContent.setSessionAttribute(MESSAGE, messages.getMessage("courseWasDeleted"));
                result.setPage(ConfigurationManager.getProperty("shortpath.page.deletecourse"));
                result.setResponseType(CommandResult.ResponseType.REDIRECT);
            }
            else {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("courseNotFound"));
                result.setPage(ConfigurationManager.getProperty("fullpath.page.deletecourse"));
            }
        }
        requestContent.setRequestAttribute(PARAM_COURSE_ID, course_id);
        return result;
    }
}
