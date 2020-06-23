package by.chyrkun.training.service.command.task_registration;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.TaskRegistrationReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

public class DeleteTaskRegistrationCommand implements Command {
    private static final String MESSAGE = "message";
    private TaskRegistrationReceiver receiver = new TaskRegistrationReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.EN;
        CommandResult result = new CommandResult();
        String student_id = requestContent.getSessionAttributes().get("user_id").toString();
        String task_id = requestContent.getRequestParameters().get("task_id")[0];
        if (receiver.delete(Integer.parseInt(task_id), Integer.parseInt(student_id))) {
            requestContent.setSessionAttribute(MESSAGE, messages.getMessage("taskRegistrationWasDeleted"));
        }
        else {
            requestContent.setSessionAttribute(MESSAGE, messages.getMessage("taskIsNotRegistered"));
        }
        result.setPage(PageManager.getProperty("shortpath.page.task") + "?command=task&task_id=" + task_id);
        result.setResponseType(CommandResult.ResponseType.REDIRECT);
        return result;
    }
}
