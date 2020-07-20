package by.chyrkun.training.dao.exception;

/**
 * Thrown to indicate that object of {@link by.chyrkun.training.model.Entity} was not found in database.
 */
public class EntityNotFoundDAOException extends DAOException {
    /**
     * Constructs a new exception.
     */
    public EntityNotFoundDAOException(){
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the message
     */
    public EntityNotFoundDAOException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public EntityNotFoundDAOException(String message, Throwable cause){
        super(message, cause);
    }
}
