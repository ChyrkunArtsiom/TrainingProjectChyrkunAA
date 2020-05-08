package by.chyrkun.training.dao.db;

import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.exception.DAOException;

public interface ConnectionPoolDAO {
    Connection$Proxy getConnection();
    boolean releaseConnection(Connection$Proxy connection) throws DAOException;
    void shutdown();
}
