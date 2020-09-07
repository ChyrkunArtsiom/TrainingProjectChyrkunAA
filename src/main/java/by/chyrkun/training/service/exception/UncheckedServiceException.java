package by.chyrkun.training.service.exception;

/**
 * Thrown to indicate that unchecked exception was thrown in method.
 */
public class UncheckedServiceException extends RuntimeException {
    /**
     * Constructs a new exception
     */
    public UncheckedServiceException(){
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the message
     */
    public UncheckedServiceException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UncheckedServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
