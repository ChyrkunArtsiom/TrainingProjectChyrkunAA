package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.exception.TaskNotFoundDAOException;
import by.chyrkun.training.dao.exception.UserNotFoundDAOException;
import by.chyrkun.training.dao.impl.TaskRegistrationDAO;
import by.chyrkun.training.model.TaskRegistration;
import by.chyrkun.training.service.exception.TaskNotFoundServiceException;
import by.chyrkun.training.service.exception.UserNotFoundServiceException;

import java.util.List;
import java.util.Optional;

public class TaskRegistrationReceiver {
    public boolean isPerformed(int task_id, int student_id) {
        TaskRegistrationDAO taskRegistrationDAO = new TaskRegistrationDAO();
        try {
            return taskRegistrationDAO.isPerformed(task_id, student_id);
        }finally {
            taskRegistrationDAO.close();
        }
    }

    public boolean create(TaskRegistration taskRegistration) throws UserNotFoundServiceException, TaskNotFoundServiceException {
        TaskRegistrationDAO taskRegistrationDAO = new TaskRegistrationDAO();
        try {
            return taskRegistrationDAO.create(taskRegistration);
        }catch (TaskNotFoundDAOException ex){
            throw new TaskNotFoundServiceException(ex.getMessage(), ex);
        }catch (UserNotFoundDAOException ex) {
            throw new UserNotFoundServiceException(ex.getMessage(), ex);
        }finally {
            taskRegistrationDAO.close();
        }
    }

    public TaskRegistration getById(int taskRegistration_id) {
        Optional<TaskRegistration> exercise;
        TaskRegistrationDAO taskRegistrationDAO = new TaskRegistrationDAO();
        try {
            exercise = taskRegistrationDAO.getEntityById(taskRegistration_id);
            return exercise.orElse(null);
        }finally {
            taskRegistrationDAO.close();
        }
    }

    public TaskRegistration update(TaskRegistration taskRegistration) {
        Optional<TaskRegistration> optional;
        TaskRegistrationDAO taskRegistrationDAO = new TaskRegistrationDAO();
        try {
            optional = taskRegistrationDAO.update(taskRegistration);
            return optional.orElse(null);
        }finally {
            taskRegistrationDAO.close();
        }
    }

    public boolean delete(int task_id, int student_id) {
        TaskRegistrationDAO taskRegistrationDAO = new TaskRegistrationDAO();
        try {
            Optional<TaskRegistration> task = taskRegistrationDAO.getTaskRegistrationByTaskStudent(task_id, student_id);
            return task.map(taskRegistrationDAO::delete).orElse(false);
        }finally {
            taskRegistrationDAO.close();
        }
    }

    public TaskRegistration getByTaskStudent(int task_id, int student_id) {
        Optional<TaskRegistration> exercise;
        TaskRegistrationDAO taskRegistrationDAO = new TaskRegistrationDAO();
        try {
            exercise = taskRegistrationDAO.getTaskRegistrationByTaskStudent(task_id, student_id);
            return exercise.orElse(null);
        }finally {
            taskRegistrationDAO.close();
        }
    }

    public List<TaskRegistration> getAllByTask(int task_id) {
        List<TaskRegistration> registrations;
        TaskRegistrationDAO taskRegistrationDAO = new TaskRegistrationDAO();
        try {
            registrations = taskRegistrationDAO.getAllByTask(task_id);
            return registrations;
        }finally {
            taskRegistrationDAO.close();
        }
    }
}
