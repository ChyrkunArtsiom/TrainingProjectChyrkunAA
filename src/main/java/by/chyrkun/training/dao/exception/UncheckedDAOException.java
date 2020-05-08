package by.chyrkun.training.dao.exception;

public class UncheckedDAOException extends RuntimeException {
    public UncheckedDAOException(String message){
        super(message);
    }

    public UncheckedDAOException(String message, Throwable cause){
        super(message, cause);
    }
}
