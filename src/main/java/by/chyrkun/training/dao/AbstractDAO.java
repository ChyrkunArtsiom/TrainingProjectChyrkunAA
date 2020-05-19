package by.chyrkun.training.dao;

import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.DAOException;
import by.chyrkun.training.model.Entity;

import java.util.Optional;

public abstract class AbstractDAO<T extends Entity> {
    protected Connection$Proxy connection;
    protected void setConnection(Connection$Proxy connection){
        this.connection = connection;
    }

    public abstract boolean create(T entity) throws DAOException;
    public abstract boolean delete(T entity);
    public abstract Optional<T> update(T entity);
    public abstract Optional<T> getEntityById(int id);

    public boolean close() {
        return ConnectionPoolImpl.getInstance().releaseConnection(connection);
    }
}
