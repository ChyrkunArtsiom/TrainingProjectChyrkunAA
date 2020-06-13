package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.resource.ConfigurationManager;

import java.util.List;

public class GetTasksByCourseCommand implements Command {
    private static final String TASKS = "tasks";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult result = new CommandResult();
        return result;
    }
}
