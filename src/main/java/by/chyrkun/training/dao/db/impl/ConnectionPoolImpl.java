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

public enum ConnectionPoolImpl implements ConnectionPoolDAO {
    CONNECTION_POOL_INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(ConnectionPoolImpl.class);
    private List<Connection$Proxy> connectionPool = new ArrayList<>();
    private List<Connection$Proxy> usedConnections = new ArrayList<>();

    public static ConnectionPoolImpl getInstance() {

        return CONNECTION_POOL_INSTANCE;
    }

    ConnectionPoolImpl() {
        LOGGER.log(Level.INFO, "Initializing connection pool...");
        initConnectionPool();
    }

    public void initConnectionPool() {
        createConnections(DB_PROPERTIES.getInitialPoolSize());
    }

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

    @Override
    public Connection$Proxy getConnection() {
        if (connectionPool.size() < 1 && usedConnections.size() == 0) {
            initConnectionPool();
        }
        if (connectionPool.size() < 1) {
            LOGGER.log(Level.FATAL, "Connection pool is emopty");
            throw new UncheckedDAOException("Connection pool is emopty");
        }
        Connection$Proxy connection$Proxy = connectionPool.remove(connectionPool.size()-1);
        usedConnections.add(connection$Proxy);
        return connection$Proxy;
    }

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
