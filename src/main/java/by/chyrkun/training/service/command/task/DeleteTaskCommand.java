package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.resource.MessageManager;

public class DeleteTaskCommand implements Command {
    private static final String MESSAGE = "message";
    private TaskReceiver receiver = new TaskReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.valueOf(requestContent.getSessionAttributes().get("lang").toString());
        CommandResult result = new CommandResult();
        String task_id = requestContent.getRequestParameters().get("task_id")[0];
        Task task = receiver.getById(Integer.parseInt(task_id));
        if (task == null && !receiver.delete(Integer.parseInt(task_id))) {
            requestContent.setRequestAttribute("errorMessage", messages.getMessage("taskNotFound"));
        }
        result.setPage(PageManager.getProperty("shortpath.page.course") + "/" + task.getCourse().getId());
        result.setResponseType(CommandResult.ResponseType.REDIRECT);
        return result;
    }
}
