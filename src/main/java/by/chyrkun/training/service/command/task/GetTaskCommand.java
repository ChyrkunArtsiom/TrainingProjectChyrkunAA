package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.CourseRegistrationReceiver;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.receiver.TaskRegistrationReceiver;
import by.chyrkun.training.service.resource.PageManager;

public class GetTaskCommand implements Command {
    private TaskReceiver receiver = new TaskReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult result = new CommandResult();
        int task_id = Integer.parseInt(requestContent.getRequestParameters().get("task_id")[0]);
        int user_id = Integer.parseInt(requestContent.getSessionAttributes().get("user_id").toString());
        String role = requestContent.getSessionAttributes().get("role").toString();
        Task task = receiver.getById(task_id);
        if (task != null) {
            if (role.equals("teacher") && task.getCourse().getTeacher().getId() == user_id) {
                requestContent.setRequestAttribute("task", task);
            }else if (role.equals("student")) {
                CourseRegistrationReceiver courseRegistrationReceiver = new CourseRegistrationReceiver();
                boolean registered = courseRegistrationReceiver.isCourseRegistered(task.getCourse().getId(), user_id);
                if (registered) {
                    TaskRegistrationReceiver taskRegistrationReceiver = new TaskRegistrationReceiver();
                    boolean performed = taskRegistrationReceiver.isPerformed(task_id, user_id);
                    if (performed) {
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
