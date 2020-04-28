package dao.db;

import dao.db.impl.Connection$Proxy;
import dao.exception.DAOException;

public interface ConnectionPoolDAO {
    Connection$Proxy getConnection();
    boolean releaseConnection(Connection$Proxy connection) throws DAOException;
    void shutdown();
}
