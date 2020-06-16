package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

public class GetTaskCommand implements Command {
    private TaskReceiver receiver = new TaskReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.EN;
        CommandResult result = new CommandResult();
        String task_id = requestContent.getRequestParameters().get("task_id")[0];
        Task task = receiver.getById(Integer.parseInt(task_id));
        if (task == null) {
            requestContent.setRequestAttribute("errorMessage", messages.getMessage("taskNotFound"));
        }else {
            requestContent.setRequestAttribute("task", task);
        }
        result.setPage(PageManager.getProperty("fullpath.page.task"));
        return result;
    }
}
