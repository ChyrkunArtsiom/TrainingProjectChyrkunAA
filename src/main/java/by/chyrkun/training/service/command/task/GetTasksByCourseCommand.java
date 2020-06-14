package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.TaskReceiver;

import java.util.List;

public class GetTasksByCourseCommand extends BaseCommand implements Command {
    private static final String TASKS = "tasks";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult result = new CommandResult();
        List<Task> tasks;
        int course_id = Integer.parseInt(requestContent.getRequestParameters().get("course_id")[0]);
        TaskReceiver receiver = new TaskReceiver();
        tasks = receiver.getByCourse(course_id);
        if (tasks == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, "Tasks not found");
        }else {
            requestContent.setRequestAttribute(TASKS, tasks);
        }
        return result;
    }
}
