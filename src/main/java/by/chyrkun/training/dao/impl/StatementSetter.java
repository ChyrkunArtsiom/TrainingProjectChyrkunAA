package by.chyrkun.training.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementSetter<T> {
    void set(PreparedStatement preparedStatement, T entity) throws SQLException;
}
