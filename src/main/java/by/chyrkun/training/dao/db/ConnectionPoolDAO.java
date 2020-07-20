package by.chyrkun.training.dao.db;

import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.exception.DAOException;
import by.chyrkun.training.dao.exception.UncheckedDAOException;

/**
 * The interface Connection pool dao.
 */
public interface ConnectionPoolDAO {
    /**
     * Gets connection from pool of unused connections and moves it to pool of used connections.
     * Initiates new pools if there are no pools.
     *
     * @return the connection
     * @throws UncheckedDAOException if there are no connections in pool of unused connections
     */
    Connection$Proxy getConnection();

    /**
     * Releases connection by moving it from pool of used connections to unused.
     * Returns {@code true} if connection was moved to unused connections pool.
     *
     * @param connection the connection
     * @return {@code true} if connection was moved to unused connections pool
     * @throws UncheckedDAOException if SQLException is thrown in it
     */
    boolean releaseConnection(Connection$Proxy connection);

    /**
     * Closes all connections, deregisters drivers
     *
     * @throws UncheckedDAOException if SQLException is thrown in it
     */
    void shutdown();
}
