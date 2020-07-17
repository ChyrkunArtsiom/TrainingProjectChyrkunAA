package by.chyrkun.training.service.command.task_registration;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.model.TaskRegistration;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.exception.TaskNotFoundServiceException;
import by.chyrkun.training.service.exception.UserNotFoundServiceException;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.receiver.TaskRegistrationReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

public class RegisterTaskCommand implements Command {
    private static final String MESSAGE = "message";
    private TaskRegistrationReceiver taskRegistrationReceiver;
    private UserReceiver userReceiver;
    private TaskReceiver taskReceiver;
    private CommandResult result;

    public RegisterTaskCommand() {
        taskRegistrationReceiver = new TaskRegistrationReceiver();
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
            TaskRegistration taskRegistration = new TaskRegistration(task, student);
            try {
                taskRegistrationReceiver.create(taskRegistration);
                /*
                if (taskRegistrationReceiver.create(taskRegistration)) {
                    requestContent.setSessionAttribute(MESSAGE, messages.getMessage("taskRegistrationWasCreated"));
                }*/
            }catch (UserNotFoundServiceException ex) {
                requestContent.setSessionAttribute(MESSAGE, messages.getMessage("userNotFound"));
            }catch (TaskNotFoundServiceException ex){
                requestContent.setSessionAttribute(MESSAGE, messages.getMessage("taskNotFound"));
            }
        }
        result.setPage(PageManager.getProperty("shortpath.page.task") + "/" + task_id);
        result.setResponseType(CommandResult.ResponseType.REDIRECT);
        return result;
    }
}
