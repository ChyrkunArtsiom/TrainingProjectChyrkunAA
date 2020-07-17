package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.model.TaskRegistration;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.CourseRegistrationReceiver;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.receiver.TaskRegistrationReceiver;
import by.chyrkun.training.service.resource.PageManager;

import java.util.List;

public class GetTaskCommand implements Command {
    private TaskReceiver taskReceiver;
    private TaskRegistrationReceiver taskRegistrationReceiver;
    private CommandResult result;

    public GetTaskCommand() {
        taskReceiver = new TaskReceiver();
        taskRegistrationReceiver = new TaskRegistrationReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        int task_id = Integer.valueOf(requestContent.getRequestAttributes().get("id").toString());
        int user_id = Integer.parseInt(requestContent.getSessionAttributes().get("user_id").toString());
        String role = requestContent.getSessionAttributes().get("role").toString();
        Task task = taskReceiver.getById(task_id);
        if (task != null) {
            if (role.equals("teacher") && task.getCourse().getTeacher().getId() == user_id) {
                List<TaskRegistration> registrations;
                registrations = taskRegistrationReceiver.getAllByTask(task_id);
                requestContent.setRequestAttribute("registrations", registrations);
                requestContent.setRequestAttribute("task", task);
            }else if (role.equals("student")) {
                CourseRegistrationReceiver courseRegistrationReceiver = new CourseRegistrationReceiver();
                boolean registered = courseRegistrationReceiver.isCourseRegistered(task.getCourse().getId(), user_id);
                if (registered) {
                    boolean performed = taskRegistrationReceiver.isPerformed(task_id, user_id);
                    if (performed) {
                        TaskRegistration exercise = taskRegistrationReceiver.getByTaskStudent(task_id, user_id);
                        requestContent.setSessionAttribute("exercise", exercise);
                        if (exercise != null && exercise.getGrade() != 0) {
                            requestContent.setRequestAttribute("reviewed", "true");
                        }
                        requestContent.setRequestAttribute("performed", "true");
                    }
                    requestContent.setRequestAttribute("task", task);
                }
            }
        }
        result.setPage(PageManager.getProperty("fullpath.page.task"));
        return result;
    }
}
