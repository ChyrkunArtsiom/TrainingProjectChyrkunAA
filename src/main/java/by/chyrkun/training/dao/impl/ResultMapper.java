package by.chyrkun.training.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The interface for working with {@link ResultSet} objects.
 *
 * @param <T> the type parameter
 */
public interface ResultMapper<T> {
    /**
     * Gets data from {@link ResultSet} object and converts it to {@link by.chyrkun.training.model.Entity} object.
     *
     * @param resultSet the {@link ResultSet} object of executed SQL query
     * @return the {@link by.chyrkun.training.model.Entity} object
     * @throws SQLException if SQLException was thrown in method
     */
    T getFromResult(ResultSet resultSet) throws SQLException;
}
