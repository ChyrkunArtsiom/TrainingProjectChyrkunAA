package by.chyrkun.training.service.exception;

public class TaskNotFoundServiceException extends EntityNotFoundServiceException {
    public TaskNotFoundServiceException(String message){
        super(message);
    }

    public TaskNotFoundServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
