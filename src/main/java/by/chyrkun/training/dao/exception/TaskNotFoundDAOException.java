package by.chyrkun.training.dao.exception;

public class TaskNotFoundDAOException extends EntityNotFoundDAOException {
    public TaskNotFoundDAOException(String message){
        super(message);
    }

    public TaskNotFoundDAOException(String message, Throwable cause){
        super(message, cause);
    }
}
