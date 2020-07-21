package by.chyrkun.training.service.exception;

/**
 * Thrown to indicate that object of {@link by.chyrkun.training.model.User} was not found in database.
 */
public class UserNotFoundServiceException extends EntityNotFoundServiceException {
    /**
     * Constructs a new exception.
     */
    public UserNotFoundServiceException(){
        super();
    }

    /**
     * Constructs a new exception with the specified detail message
     *
     * @param message the message
     */
    public UserNotFoundServiceException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UserNotFoundServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
