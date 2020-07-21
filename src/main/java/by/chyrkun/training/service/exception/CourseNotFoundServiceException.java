package by.chyrkun.training.service.exception;

/**
 * Thrown to indicate that object of {@link by.chyrkun.training.model.Course} was not found in database.
 */
public class CourseNotFoundServiceException extends EntityNotFoundServiceException {
    /**
     * Instantiates a new Course not found service exception.
     */
    public CourseNotFoundServiceException(){
        super();
    }

    /**
     * Instantiates a new Course not found service exception.
     *
     * @param message the message
     */
    public CourseNotFoundServiceException(String message){
        super(message);
    }

    /**
     * Instantiates a new Course not found service exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public CourseNotFoundServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
