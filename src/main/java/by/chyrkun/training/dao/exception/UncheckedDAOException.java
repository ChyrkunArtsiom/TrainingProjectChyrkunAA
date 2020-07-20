package by.chyrkun.training.dao.exception;

/**
 * Thrown to indicate that unchecked exception was thrown in method.
 */
public class UncheckedDAOException extends RuntimeException {
    /**
     * Constructs a new exception
     */
    public UncheckedDAOException(){
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the message
     */
    public UncheckedDAOException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UncheckedDAOException(String message, Throwable cause){
        super(message, cause);
    }
}
