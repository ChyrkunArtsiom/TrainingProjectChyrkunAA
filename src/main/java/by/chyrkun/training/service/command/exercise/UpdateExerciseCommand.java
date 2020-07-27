package by.chyrkun.training.service.command.exercise;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Exercise;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.ExerciseReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.util.InputSanitizer;
import by.chyrkun.training.service.validator.ParamValidator;

/**
 * The class-command for updating an exercise. Implements {@link Command}.
 */
public class UpdateExerciseCommand implements Command {
    private ExerciseReceiver receiver = new ExerciseReceiver();
    private static final String MESSAGE = "message";

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        CommandResult result = new CommandResult();
        String exercise_id = requestContent.getRequestParameters().get("exercise_id")[0];
        String grade = requestContent.getRequestParameters().get("grade")[0];
        String review = requestContent.getRequestParameters().get("review")[0];
        if (!ParamValidator.isPresent(grade, review)) {
            requestContent.setSessionAttribute(MESSAGE, messages.getMessage("lineIsEmpty"));
            result.setPage(PageManager.getPage("shortpath.page.exercise") + "/" + exercise_id);
        }else {
            review = InputSanitizer.sanitize(review);
            Exercise exercise = receiver.getById(Integer.parseInt(exercise_id));
            exercise.setGrade(Integer.parseInt(grade));
            exercise.setReview(review);
            if (receiver.update(exercise) == null) {
                requestContent.setSessionAttribute(MESSAGE, messages.getMessage("exerciseWasNotUpdated"));
                result.setPage(PageManager.getPage("shortpath.page.teacher.courses"));
            }else {
                result.setPage(PageManager.getPage("shortpath.page.exercise") + "/" + exercise_id);
            }
        }
        result.setResponseType(CommandResult.ResponseType.REDIRECT);
        return result;
    }
}
