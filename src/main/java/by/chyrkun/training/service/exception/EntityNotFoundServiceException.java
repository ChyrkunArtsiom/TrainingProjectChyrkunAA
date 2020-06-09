package by.chyrkun.training.service.exception;

public class EntityNotFoundServiceException extends ServiceException {
    public EntityNotFoundServiceException(String message){
        super(message);
    }

    public EntityNotFoundServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
