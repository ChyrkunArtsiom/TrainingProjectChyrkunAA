package by.chyrkun.training.dao.exception;

/**
 * Thrown to indicate that there is a condition that was catched on DAO layer.
 */
public class DAOException extends Exception {
    /**
     * Constructs a new exception.
     */
    public DAOException(){
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the message
     */
    public DAOException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public DAOException(String message, Throwable cause){
        super(message, cause);
    }
}
