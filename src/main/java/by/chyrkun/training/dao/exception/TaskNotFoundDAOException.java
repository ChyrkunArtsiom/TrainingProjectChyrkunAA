package by.chyrkun.training.dao.exception;

/**
 * Thrown to indicate that object of {@link by.chyrkun.training.model.Task} was not found in database.
 */
public class TaskNotFoundDAOException extends EntityNotFoundDAOException {
    /**
     * Constructs a new exception
     */
    public TaskNotFoundDAOException(){
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the message
     */
    public TaskNotFoundDAOException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TaskNotFoundDAOException(String message, Throwable cause){
        super(message, cause);
    }
}
