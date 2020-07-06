package by.chyrkun.training.service.command.task_registration;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.TaskRegistration;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.TaskRegistrationReceiver;
import by.chyrkun.training.service.resource.PageManager;

public class GetTaskRegistrationCommand implements Command {
    private TaskRegistrationReceiver receiver = new TaskRegistrationReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult result = new CommandResult();
        int exercise_id = Integer.valueOf(requestContent.getRequestAttributes().get("id").toString());
        int user_id = Integer.parseInt(requestContent.getSessionAttributes().get("user_id").toString());
        String role = requestContent.getSessionAttributes().get("role").toString();
        TaskRegistration exercise = receiver.getById(exercise_id);
        if (exercise != null) {
            if (role.equals("teacher") && exercise.getTask().getCourse().getTeacher().getId() == user_id) {
                requestContent.setRequestAttribute("exercise", exercise);
            }else if (role.equals("student") && exercise.getStudent().getId() == user_id) {
                requestContent.setRequestAttribute("exercise", exercise);
            }
        }
        result.setPage(PageManager.getProperty("fullpath.page.exercise"));
        return result;
    }
}
