package by.chyrkun.training.dao.exception;

/**
 * Thrown to indicate that object of {@link by.chyrkun.training.model.User} was not found in database.
 */
public class UserNotFoundDAOException extends EntityNotFoundDAOException {
    /**
     * Constructs a new exception.
     */
    public UserNotFoundDAOException(){
        super();
    }

    /**
     * Constructs a new exception with the specified detail message
     *
     * @param message the message
     */
    public UserNotFoundDAOException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UserNotFoundDAOException(String message, Throwable cause){
        super(message, cause);
    }
}
