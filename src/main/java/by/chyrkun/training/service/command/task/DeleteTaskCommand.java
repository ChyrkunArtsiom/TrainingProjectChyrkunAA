package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.validator.ParamValidator;

public class DeleteTaskCommand implements Command {
    private static final String MESSAGE = "message";
    private TaskReceiver receiver = new TaskReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.EN;
        CommandResult result = new CommandResult();
        String task_id = requestContent.getRequestParameters().get("task_id")[0];
        Task task = receiver.getById(Integer.parseInt(task_id));
        if (task != null && receiver.delete(Integer.parseInt(task_id))) {
            requestContent.setSessionAttribute(MESSAGE, messages.getMessage("taskWasDeleted"));
        }
        else {
            requestContent.setRequestAttribute("errorMessage", messages.getMessage("taskNotFound"));
        }
        result.setPage(PageManager.getProperty("shortpath.page.teacher.courses") + "?command=course&course_id=" + task.getCourse().getId());
        result.setResponseType(CommandResult.ResponseType.REDIRECT);
        return result;
    }
}
