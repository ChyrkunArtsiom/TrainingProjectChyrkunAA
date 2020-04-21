package dao;

import model.Entity;

import java.sql.SQLException;

public abstract class AbstractDAO<T extends Entity> {
//    boolean delete(T entity);
    public abstract boolean create(T entity);
//    T update(T entity);
    protected abstract boolean close() throws SQLException;
}
