package dao;

import dao.db.impl.Connection$Proxy;
import dao.db.impl.ConnectionPoolImpl;
import model.Entity;

import java.util.Optional;

public abstract class AbstractDAO<T extends Entity> {
    protected Connection$Proxy connection;
    protected void setConnection(Connection$Proxy connection){
        this.connection = connection;
    }

    public abstract boolean create(T entity);
    public abstract boolean delete(T entity);
    public abstract Optional<T> update(T entity);
    public abstract Optional<T> getEntityById(int id);

    protected boolean close() {
        return ConnectionPoolImpl.getInstance().releaseConnection(connection);
    }
}
