package by.chyrkun.training.dao.exception;

public class CourseNotFoundDAOException extends EntityNotFoundDAOException {
    public CourseNotFoundDAOException(){
        super();
    }

    public CourseNotFoundDAOException(String message){
        super(message);
    }

    public CourseNotFoundDAOException(String message, Throwable cause){
        super(message, cause);
    }
}
