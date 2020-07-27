package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.dao.impl.TaskDAO;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.service.exception.CourseNotFoundServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The class-receiver which calls methods of {@link Task} objects.
 */
public class TaskReceiver {
    /**
     * Creates a task. Returns whether it was successful.
     *
     * @param task the {@link Task} object to create
     * @return whether it was successful
     * @throws CourseNotFoundServiceException if course was not found
     */
    public boolean create(Task task) throws CourseNotFoundServiceException {
        TaskDAO taskDAO = new TaskDAO();
        try {
            return taskDAO.create(task);
        }catch (EntityNotFoundDAOException ex) {
            throw new CourseNotFoundServiceException(ex.getMessage(), ex);
        }finally {
            taskDAO.close();
        }
    }

    /**
     * Deletes a task. Takes task id. Returns whether it was successful.
     *
     * @param id the task id
     * @return whether it was successful
     */
    public boolean delete(int id) {
        TaskDAO taskDAO = new TaskDAO();
        try {
            Optional<Task> task = taskDAO.getEntityById(id);
            return task.map(taskDAO::delete).orElse(false);
        }finally {
            taskDAO.close();
        }
    }

    /**
     * Gets a list of tasks by course id.
     *
     * @param course_id the course id
     * @return the list of tasks
     */
    public List<Task> getByCourse(int course_id) {
        List<Task> tasks;
        TaskDAO taskDAO = new TaskDAO();
        try {
            tasks = taskDAO.getByCourse(course_id);
            return tasks;
        }finally {
            taskDAO.close();
        }
    }

    /**
     * Gets a task by id.
     *
     * @param task_id the task id
     * @return the Optional of {@link Task}
     */
    public Task getById(int task_id) {
        Optional<Task> task;
        TaskDAO taskDAO = new TaskDAO();
        try {
            task = taskDAO.getEntityById(task_id);
            return task.orElse(null);
        }finally {
            taskDAO.close();
        }
    }
}
