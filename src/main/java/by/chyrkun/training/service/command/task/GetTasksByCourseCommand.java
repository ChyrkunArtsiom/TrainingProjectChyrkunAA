package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.TaskReceiver;

import java.util.List;

/**
 * The class-command for getting tasks by the course. Extends {@link BaseCommand}, implements {@link Command}.
 */
public class GetTasksByCourseCommand extends BaseCommand implements Command {
    private static final String TASKS = "tasks";
    private TaskReceiver receiver;
    private CommandResult result;

    /**
     * Instantiates a new Get tasks by course command.
     */
    public GetTasksByCourseCommand() {
        result = new CommandResult();
        receiver = new TaskReceiver();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        List<Task> tasks;
        int course_id = Integer.valueOf(requestContent.getRequestAttributes().get("id").toString());
        tasks = receiver.getByCourse(course_id);
        if (tasks != null) {
            requestContent.setRequestAttribute(TASKS, tasks);
        }
        return result;
    }
}
