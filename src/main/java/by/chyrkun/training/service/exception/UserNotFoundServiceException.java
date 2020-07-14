package by.chyrkun.training.service.exception;

public class UserNotFoundServiceException extends EntityNotFoundServiceException {
    public UserNotFoundServiceException(){
        super();
    }

    public UserNotFoundServiceException(String message){
        super(message);
    }

    public UserNotFoundServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
