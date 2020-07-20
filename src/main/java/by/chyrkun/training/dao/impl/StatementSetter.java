package by.chyrkun.training.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The interface for working with {@link PreparedStatement} objects.
 *
 * @param <T> the type parameter
 */
public interface StatementSetter<T> {
    /**
     * Sets values from {@link by.chyrkun.training.model.Entity} object in {@link PreparedStatement} object.
     *
     * @param preparedStatement the prepared statement object
     * @param entity            the entity object
     * @throws SQLException if SQLException is thrown in method
     */
    void setValuesToStatement(PreparedStatement preparedStatement, T entity) throws SQLException;
}
