package by.chyrkun.training.dao.db.impl;

import by.chyrkun.training.dao.db.ConnectionPoolDAO;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static by.chyrkun.training.dao.db.config.AppProperties.DB_PROPERTIES;

/**
 * Enumeration for database connection pool.
 */
public enum ConnectionPoolImpl implements ConnectionPoolDAO {
    /**
     * Connection pool instance.
     */
    CONNECTION_POOL_INSTANCE;

    /** Field for logging. */
    private final Logger LOGGER = LogManager.getLogger(ConnectionPoolImpl.class);

    /** List of unused connections. */
    private List<Connection$Proxy> connectionPool = new ArrayList<>();

    /** List of used connections. */
    private List<Connection$Proxy> usedConnections = new ArrayList<>();

    public static ConnectionPoolImpl getInstance() {

        return CONNECTION_POOL_INSTANCE;
    }

    /** Constructor for enumeration. Initiates pools. */
    ConnectionPoolImpl() {
        LOGGER.log(Level.INFO, "Initializing connection pool...");
        initConnectionPool();
    }

    /**
     * Calls {@link #createConnections(int)}.
     */
    public void initConnectionPool() {
        createConnections(DB_PROPERTIES.getInitialPoolSize());
    }

    /**
     * Gets properties and creates connection pool based on them.
     *
     * @param poolSize size of connection pools
     * @throws UncheckedDAOException if SQLException is thrown during driver registration or connection creating
     */
    private void createConnections(int poolSize) {
        String url = DB_PROPERTIES.getUrl();
        String user = DB_PROPERTIES.getUser();
        String password = DB_PROPERTIES.getPassword();

        try{
            DriverManager.registerDriver(new org.postgresql.Driver());
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Driver cannot be registered");
            throw new UncheckedDAOException("Driver cannot be registered", ex);
        }

        try{
            for (int i = 0; i < poolSize; i++) {
                Connection$Proxy connection$Proxy = new Connection$Proxy(DriverManager.getConnection(url, user, password));
                connectionPool.add(connection$Proxy);
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Cannot connect to database with given properties");
            throw new UncheckedDAOException("Cannot connect to database with given properties", ex);
        }
    }

    /**
     * Gets connection from pool of unused connections and moves it to pool of used connections.
     * Initiates new pools if there are no pools.
     *
     * @return the connection
     * @throws UncheckedDAOException if there are no connections in pool of unused connections
     */
    @Override
    public Connection$Proxy getConnection() {
        if (connectionPool.size() < 1 && usedConnections.size() == 0) {
            initConnectionPool();
        }
        if (connectionPool.size() < 1) {
            LOGGER.log(Level.FATAL, "Connection pool is empty");
            throw new UncheckedDAOException("Connection pool is empty");
        }
        Connection$Proxy connection$Proxy = connectionPool.remove(connectionPool.size()-1);
        usedConnections.add(connection$Proxy);
        return connection$Proxy;
    }

    /**
     * Releases connection by moving it from pool of used connections to unused.
     * Returns {@code true} if connection was moved to unused connections pool.
     *
     * @param connection the connection
     * @return {@code true} if connection was moved to unused connections pool
     * @throws UncheckedDAOException if SQLException is thrown in it
     */
    @Override
    public boolean releaseConnection(Connection$Proxy connection) {
        try{
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Connection cannot be released");
            throw new UncheckedDAOException("Connection cannot be released", ex);
        }

        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    /**
     * Closes all connections, deregisters drivers.
     *
     * @throws UncheckedDAOException if SQLException is thrown in it
     */
    @Override
    public void shutdown() {
        LOGGER.log(Level.INFO, "Shutting down connections...");
        connectionPool.addAll(usedConnections);
        usedConnections.clear();
        for (Connection$Proxy c : connectionPool) {
            c.connectionClose();
        }
        connectionPool.clear();
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        try {
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Connection cannot be released");
            throw new UncheckedDAOException("Connection cannot be released", ex);
        }
    }
}
