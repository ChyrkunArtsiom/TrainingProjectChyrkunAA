package by.chyrkun.training.service.exception;

public class CommandNotFoundException extends ServiceException{
    public CommandNotFoundException(String message) {
        super(message);
    }

    public CommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
