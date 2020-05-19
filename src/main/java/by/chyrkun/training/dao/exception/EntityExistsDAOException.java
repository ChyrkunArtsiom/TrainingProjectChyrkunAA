package by.chyrkun.training.dao.exception;

public class EntityExistsDAOException extends DAOException {
    public EntityExistsDAOException(String message){
        super(message);
    }

    public EntityExistsDAOException(String message, Throwable cause){
        super(message, cause);
    }
}
