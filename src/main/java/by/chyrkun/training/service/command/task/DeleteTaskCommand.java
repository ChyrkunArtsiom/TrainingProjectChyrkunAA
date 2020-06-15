package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.validator.ParamValidator;

public class DeleteTaskCommand implements Command {
    private static final String PARAM_TASK_ID = "task_id";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE = "message";
    private TaskReceiver receiver = new TaskReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.EN;
        CommandResult result = new CommandResult();
        String task_id = requestContent.getRequestParameters().get(PARAM_TASK_ID)[0];
        if (!ParamValidator.isPresent(task_id)) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
            result.setPage(PageManager.getProperty("fullpath.page.deletetask"));
        }
        else {
            if (receiver.delete(Integer.parseInt(task_id))) {
                requestContent.setSessionAttribute(MESSAGE, messages.getMessage("taskWasDeleted"));
                result.setPage(PageManager.getProperty("shortpath.page.deletetask"));
                result.setResponseType(CommandResult.ResponseType.REDIRECT);
            }
            else {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("taskNotFound"));
                result.setPage(PageManager.getProperty("fullpath.page.deletetask"));
            }
        }
        requestContent.setRequestAttribute(PARAM_TASK_ID, task_id);
        return result;
    }
}
