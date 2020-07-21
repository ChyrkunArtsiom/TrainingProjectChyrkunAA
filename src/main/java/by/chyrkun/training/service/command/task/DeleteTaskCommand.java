package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

public class DeleteTaskCommand implements Command {
    private TaskReceiver receiver;
    private CommandResult result;

    public DeleteTaskCommand() {
        receiver = new TaskReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        String task_id = requestContent.getRequestParameters().get("task_id")[0];
        String course_id = requestContent.getRequestParameters().get("course_id")[0];
        Task task = receiver.getById(Integer.parseInt(task_id));
        if (task == null || !receiver.delete(Integer.parseInt(task_id))) {
            requestContent.setRequestAttribute("errorMessage", messages.getMessage("taskNotFound"));
        }
        result.setPage(PageManager.getPage("shortpath.page.course") + "/" + course_id);
        result.setResponseType(CommandResult.ResponseType.REDIRECT);
        return result;
    }
}
