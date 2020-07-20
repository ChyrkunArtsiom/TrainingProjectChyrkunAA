package by.chyrkun.training.dao.exception;

/**
 * Thrown to indicate that object of {@link by.chyrkun.training.model.Role} was not found in database.
 */
public class RoleNotFoundDAOException extends EntityNotFoundDAOException {
    /**
     * Constructs a new exception.
     */
    public RoleNotFoundDAOException(){
        super();
    }

    /**
     * Constructs a new exception with the specified detail message
     *
     * @param message the message
     */
    public RoleNotFoundDAOException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public RoleNotFoundDAOException(String message, Throwable cause){
        super(message, cause);
    }
}