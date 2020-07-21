package by.chyrkun.training.service.exception;

/**
 * Thrown to indicate that there is a condition that was catched on service layer.
 */
public class ServiceException extends Exception {
    /**
     * Constructs a new exception.
     */
    public ServiceException(){
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the message
     */
    public ServiceException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
