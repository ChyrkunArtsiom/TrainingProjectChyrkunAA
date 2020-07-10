package by.chyrkun.training.service.command.task_registration;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.TaskRegistration;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.TaskRegistrationReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.util.InputSanitizer;
import by.chyrkun.training.service.validator.ParamValidator;

public class UpdateTaskRegistrationCommand implements Command {
    private TaskRegistrationReceiver receiver = new TaskRegistrationReceiver();
    private static final String MESSAGE = "message";

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        CommandResult result = new CommandResult();
        String exercise_id = requestContent.getRequestParameters().get("exercise_id")[0];
        String grade = requestContent.getRequestParameters().get("grade")[0];
        String review = requestContent.getRequestParameters().get("review")[0];
        if (!ParamValidator.isPresent(grade, review)) {
            requestContent.setRequestAttribute(MESSAGE, messages.getMessage("lineIsEmpty"));
            result.setPage(PageManager.getProperty("fullpath.page.createcourse"));
        }else {
            review = InputSanitizer.sanitize(review);
            TaskRegistration taskRegistration = receiver.getById(Integer.parseInt(exercise_id));
            taskRegistration.setGrade(Integer.parseInt(grade));
            taskRegistration.setReview(review);
            if (receiver.update(taskRegistration) == null) {
                requestContent.setSessionAttribute(MESSAGE, messages.getMessage("taskRegistrationWasNotUpdated"));
                result.setPage(PageManager.getProperty("shortpath.page.teacher.courses"));
            }else {
                result.setPage(PageManager.getProperty("shortpath.page.exercise") + "/" + exercise_id);
            }
            result.setResponseType(CommandResult.ResponseType.REDIRECT);
        }
        return result;
    }
}
