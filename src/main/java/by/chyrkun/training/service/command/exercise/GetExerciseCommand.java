package by.chyrkun.training.service.command.exercise;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Exercise;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.ExerciseReceiver;
import by.chyrkun.training.service.resource.PageManager;

/**
 * The class-command for getting an exercise. Implements {@link Command}.
 */
public class GetExerciseCommand implements Command {
    private ExerciseReceiver receiver;
    private CommandResult result;

    /**
     * Instantiates a new Get exercise command.
     */
    public GetExerciseCommand() {
        receiver = new ExerciseReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        int exercise_id = Integer.valueOf(requestContent.getRequestAttributes().get("id").toString());
        int user_id = Integer.parseInt(requestContent.getSessionAttributes().get("user_id").toString());
        String role = requestContent.getSessionAttributes().get("role").toString();
        Exercise exercise = receiver.getById(exercise_id);
        if (exercise != null) {
            if (role.equals("teacher") && exercise.getTask().getCourse().getTeacher().getId() == user_id) {
                requestContent.setRequestAttribute("exercise", exercise);
            }else if (role.equals("student") && exercise.getStudent().getId() == user_id) {
                requestContent.setRequestAttribute("exercise", exercise);
            }
        }
        result.setPage(PageManager.getPage("fullpath.page.exercise"));
        return result;
    }
}
