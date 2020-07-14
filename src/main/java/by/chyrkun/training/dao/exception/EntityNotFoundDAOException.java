package by.chyrkun.training.dao.exception;

public class EntityNotFoundDAOException extends DAOException {
    public EntityNotFoundDAOException(){
        super();
    }

    public EntityNotFoundDAOException(String message){
        super(message);
    }

    public EntityNotFoundDAOException(String message, Throwable cause){
        super(message, cause);
    }
}
