package dao;

import dao.db.impl.Connection$Proxy;
import dao.db.impl.ConnectionPoolImpl;
import model.Entity;

import java.sql.SQLException;

public abstract class AbstractDAO<T extends Entity> {
    protected Connection$Proxy connection;
    protected void setConnection(Connection$Proxy connection){
        this.connection = connection;
    }

//    boolean delete(T entity);
    public abstract boolean create(T entity);
//    T update(T entity);
    protected boolean close() throws SQLException{
        return ConnectionPoolImpl.getInstance().releaseConnection(connection);
    }
}
