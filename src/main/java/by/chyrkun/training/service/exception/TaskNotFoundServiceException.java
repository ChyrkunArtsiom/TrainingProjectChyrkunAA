package by.chyrkun.training.service.exception;

/**
 * Thrown to indicate that object of {@link by.chyrkun.training.model.Task} was not found in database.
 */
public class TaskNotFoundServiceException extends EntityNotFoundServiceException {
    /**
     * Constructs a new exception
     */
    public TaskNotFoundServiceException(){
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the message
     */
    public TaskNotFoundServiceException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TaskNotFoundServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
