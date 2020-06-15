package by.chyrkun.training.dao.exception;

public class UserNotFoundDAOException extends EntityNotFoundDAOException {
    public UserNotFoundDAOException(String message){
        super(message);
    }

    public UserNotFoundDAOException(String message, Throwable cause){
        super(message, cause);
    }
}
