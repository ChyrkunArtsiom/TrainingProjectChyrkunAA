package by.javatr.training.dao;

import java.sql.Connection;

public interface ConnectionPoolDAO {
    Connection getConnection();
    boolean releaseConnection(Connection connection);
}
