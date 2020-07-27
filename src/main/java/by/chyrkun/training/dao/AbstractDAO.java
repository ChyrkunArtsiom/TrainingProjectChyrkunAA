package by.chyrkun.training.dao;

import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import by.chyrkun.training.model.Entity;

import java.util.List;
import java.util.Optional;

/**
 * Abstract class for interacting with database.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractDAO<T extends Entity> {
    /**
     * The connection.
     */
    protected Connection$Proxy connection;

    protected void setConnection(Connection$Proxy connection){
        this.connection = connection;
    }

    /**
     * Closes used connection. Returns {@code true} if connection was released.
     *
     * @return {@code true} if connection was released
     * @see ConnectionPoolImpl#releaseConnection(Connection$Proxy)
     */
    public boolean close() {
        return ConnectionPoolImpl.getInstance().releaseConnection(connection);
    }

    /**
     * Creates entity. Returns {@code true} if entity in database was created.
     *
     * @param entity the object of {@link Entity}
     * @return {@code true} if entity in database was created
     * @throws EntityNotFoundDAOException if necessary entity was not found
     */
    public abstract boolean create(T entity) throws EntityNotFoundDAOException;

    /**
     * Deletes entity. Returns {@code true} if entity was deleted.
     *
     * @param entity the object of {@link Entity}
     * @return {@code true} if entity was deleted
     * @throws UncheckedDAOException if SQLException is thrown in it
     */
    public abstract boolean delete(T entity);

    /**
     * Updates entity. Returns the Optional of entity.
     *
     * @param entity the object of {@link Entity}
     * @return the Optional of {@link Entity} object
     * @throws UncheckedDAOException if SQLException is thrown in it
     */
    public abstract Optional<T> update(T entity);

    /**
     * Gets entity by id.
     *
     * @param id the id
     * @return the Optional of {@link Entity} object
     * @throws UncheckedDAOException if SQLException is thrown in it
     */
    public abstract Optional<T> getEntityById(int id);


    /**
     * Gets list of {@link Entity} objects.
     *
     * @return list of {@link Entity} objects
     */
    public abstract List<T> getAll();
}
