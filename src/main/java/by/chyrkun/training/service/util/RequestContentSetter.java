package by.chyrkun.training.service.util;

import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.receiver.RoleReceiver;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;

import java.util.List;

public class RequestContentSetter {
    private static final String PARAM_NAME_ROLES = "roles";
    private static final String TASKS = "tasks";
    private static final String TEACHERS = "teachers";
    private static final String ERROR_MESSAGE = "errorMessage";

    public static void showRoles(RequestContent requestContent) {
        List<Role> roles;
        RoleReceiver roleReceiver = new RoleReceiver();
        roles = roleReceiver.getAll();
        if (roles == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, "Roles not found");
        }else {
            requestContent.setRequestAttribute(PARAM_NAME_ROLES, roles);
        }
    }

    public static void showTeachers(RequestContent requestContent) {
        List<User> teachers;
        UserReceiver userReceiver = new UserReceiver();
        teachers = userReceiver.getTeachers();
        if (teachers == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, "Teachers not found");
        }else {
            requestContent.setRequestAttribute(TEACHERS, teachers);
        }
    }

    public static void showTasks(RequestContent requestContent) {
        List<Task> tasks;
        int course_id = Integer.parseInt(requestContent.getRequestParameters().get("course_id")[0]);
        TaskReceiver receiver = new TaskReceiver();
        tasks = receiver.getByCourse(course_id);
        if (tasks == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, "Tasks not found");
        }else {
            requestContent.setRequestAttribute(TASKS, tasks);
        }
    }
}
