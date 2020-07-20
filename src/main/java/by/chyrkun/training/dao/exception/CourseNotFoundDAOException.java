package by.chyrkun.training.dao.exception;

/**
 * Thrown to indicate that object of {@link by.chyrkun.training.model.Course} was not found in database.
 */
public class CourseNotFoundDAOException extends EntityNotFoundDAOException {
    /**
     * Constructs a new exception.
     */
    public CourseNotFoundDAOException(){
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the message
     */
    public CourseNotFoundDAOException(String message){
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public CourseNotFoundDAOException(String message, Throwable cause){
        super(message, cause);
    }
}
