package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.dao.impl.TaskDAO;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;

import java.util.List;
import java.util.Optional;

public class TaskReceiver {
    private TaskDAO taskDAO = new TaskDAO();

    public boolean create(Task task) throws EntityNotFoundServiceException {
        taskDAO = new TaskDAO();
        try {
            return taskDAO.create(task);
        }catch (EntityNotFoundDAOException ex) {
            throw new EntityNotFoundServiceException(ex.getMessage(), ex);
        }finally {
            taskDAO.close();
        }
    }

    public boolean delete(int id) {
        taskDAO = new TaskDAO();
        try {
            Optional<Task> task = taskDAO.getEntityById(id);
            return task.map(taskDAO::delete).orElse(false);
        }finally {
            taskDAO.close();
        }
    }

    public List<Task> getByCourse(int course_id) {
        List<Task> tasks;
        taskDAO = new TaskDAO();
        try {
            tasks = taskDAO.getByCourse(course_id);
            return tasks;
        }finally {
            taskDAO.close();
        }
    }

    public Task getById(int task_id) {
        Optional<Task> task;
        taskDAO = new TaskDAO();
        try {
            task = taskDAO.getEntityById(task_id);
            return task.orElse(null);
        }finally {
            taskDAO.close();
        }
    }
}
