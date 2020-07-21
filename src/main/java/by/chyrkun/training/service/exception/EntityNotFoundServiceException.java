package by.chyrkun.training.service.exception;

/**
 * Thrown to indicate that object of {@link by.chyrkun.training.model.Entity} was not found in database.
 */
public class EntityNotFoundServiceException extends ServiceException {
    /**
     * Constructs a new exception.
     */
    public EntityNotFoundServiceException(){
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the message
     */
    public EntityNotFoundServiceException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public EntityNotFoundServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
