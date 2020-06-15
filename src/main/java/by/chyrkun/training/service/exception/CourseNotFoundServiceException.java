package by.chyrkun.training.service.exception;

public class CourseNotFoundServiceException extends EntityNotFoundServiceException {
    public CourseNotFoundServiceException(String message){
        super(message);
    }

    public CourseNotFoundServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
