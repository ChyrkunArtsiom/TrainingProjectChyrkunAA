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
        MessageManager messages = setLang(requestContent);
        CommandResult result = new CommandResult();
        String exercise_id = requestContent.getRequestParameters().get("exercise_id")[0];
        String task_id = requestContent.getRequestParameters().get("task_id")[0];
        if (!receiver.delete(Integer.parseInt(exercise_id))) {
            requestContent.setSessionAttribute(MESSAGE, messages.getMessage("taskIsNotRegistered"));
        }
        result.setPage(PageManager.getProperty("shortpath.page.task") + "/" + task_id);
        result.setResponseType(CommandResult.ResponseType.REDIRECT);
        return result;
    }
}
