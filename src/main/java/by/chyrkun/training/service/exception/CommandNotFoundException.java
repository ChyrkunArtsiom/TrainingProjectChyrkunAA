package by.chyrkun.training.service.exception;

/**
 * Thrown to indicate that object of {@link by.chyrkun.training.service.command.CommandType} was not found.
 */
public class CommandNotFoundException extends ServiceException {
    /**
     * Constructs a new exception.
     */
    public CommandNotFoundException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the message
     */
    public CommandNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public CommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
