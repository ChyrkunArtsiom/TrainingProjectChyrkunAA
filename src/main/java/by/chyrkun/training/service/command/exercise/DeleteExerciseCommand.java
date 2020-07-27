package by.chyrkun.training.service.command.exercise;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.ExerciseReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

/**
 * The class-command for exercise deletion. Implements {@link Command}.
 */
public class DeleteExerciseCommand implements Command {
    private static final String MESSAGE = "message";
    private ExerciseReceiver receiver;
    private CommandResult result;

    /**
     * Instantiates a new Delete exercise command.
     */
    public DeleteExerciseCommand() {
        receiver = new ExerciseReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        String exercise_id = requestContent.getRequestParameters().get("exercise_id")[0];
        String task_id = requestContent.getRequestParameters().get("task_id")[0];
        if (!receiver.delete(Integer.parseInt(exercise_id))) {
            requestContent.setSessionAttribute(MESSAGE, messages.getMessage("taskIsNotRegistered"));
        }
        result.setPage(PageManager.getPage("shortpath.page.task") + "/" + task_id);
        result.setResponseType(CommandResult.ResponseType.REDIRECT);
        return result;
    }
}
