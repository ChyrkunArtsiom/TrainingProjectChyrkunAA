package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.dao.impl.TaskDAO;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;

import java.util.Optional;

public class TaskReceiver {
    public boolean create(Task task) throws EntityNotFoundServiceException {
        TaskDAO taskDAO = new TaskDAO();
        try{
            return taskDAO.create(task);
        }catch (EntityNotFoundDAOException ex){
            throw new EntityNotFoundServiceException(ex.getMessage(), ex);
        }finally {
            taskDAO.close();
        }
    }

    public boolean delete(int id) {
        TaskDAO taskDAO = new TaskDAO();
        try {
            Optional<Task> task = taskDAO.getEntityById(id);
            if (task.isPresent()) {
                return taskDAO.delete(task.get());
            }
            return false;
        }finally {
            taskDAO.close();
        }
    }
}
