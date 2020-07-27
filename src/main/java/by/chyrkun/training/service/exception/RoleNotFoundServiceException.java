package by.chyrkun.training.service.exception;

/**
 * Thrown to indicate that object of {@link by.chyrkun.training.model.Role} was not found in database.
 */
public class RoleNotFoundServiceException extends EntityNotFoundServiceException {
    /**
     * Constructs a new exception
     */
    public RoleNotFoundServiceException(){
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the message
     */
    public RoleNotFoundServiceException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public RoleNotFoundServiceException(String message, Throwable cause){
        super(message, cause);
    }
}