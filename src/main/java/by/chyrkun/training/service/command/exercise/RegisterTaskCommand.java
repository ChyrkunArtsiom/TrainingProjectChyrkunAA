package by.chyrkun.training.service.command.exercise;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Exercise;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.exception.TaskNotFoundServiceException;
import by.chyrkun.training.service.exception.UserNotFoundServiceException;
import by.chyrkun.training.service.receiver.ExerciseReceiver;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

/**
 * The class-command for exercise creation. Implements {@link Command}.
 */
public class RegisterTaskCommand implements Command {
    private static final String MESSAGE = "message";
    private ExerciseReceiver exerciseReceiver;
    private UserReceiver userReceiver;
    private TaskReceiver taskReceiver;
    private CommandResult result;

    /**
     * Instantiates a new Register task command.
     */
    public RegisterTaskCommand() {
        exerciseReceiver = new ExerciseReceiver();
        userReceiver = new UserReceiver();
        taskReceiver = new TaskReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        String student_id = requestContent.getSessionAttributes().get("user_id").toString();
        String task_id = requestContent.getRequestParameters().get("task_id")[0];
        User student = userReceiver.getById(Integer.parseInt(student_id));
        Task task = taskReceiver.getById(Integer.parseInt(task_id));
        if (student == null) {
            requestContent.setSessionAttribute(MESSAGE, messages.getMessage("userIsNotStudent"));
        }
        else if (task == null) {
            requestContent.setSessionAttribute(MESSAGE, messages.getMessage("taskNotFound"));
        }
        else {
            Exercise exercise = new Exercise(task, student);
            try {
                exerciseReceiver.create(exercise);
                /*
                if (exerciseReceiver.create(exercise)) {
                    requestContent.setSessionAttribute(MESSAGE, messages.getMessage("exerciseWasCreated"));
                }*/
            }catch (UserNotFoundServiceException ex) {
                requestContent.setSessionAttribute(MESSAGE, messages.getMessage("userNotFound"));
            }catch (TaskNotFoundServiceException ex){
                requestContent.setSessionAttribute(MESSAGE, messages.getMessage("taskNotFound"));
            }
        }
        result.setPage(PageManager.getPage("shortpath.page.task") + "/" + task_id);
        result.setResponseType(CommandResult.ResponseType.REDIRECT);
        return result;
    }
}
