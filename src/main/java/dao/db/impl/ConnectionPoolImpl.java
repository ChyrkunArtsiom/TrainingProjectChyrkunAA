package dao.db.impl;

import dao.db.ConnectionPoolDAO;
import dao.exception.UncheckedDAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.db.config.AppProperties.DB_PROPERTIES;

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

    private void initConnectionPool() {
        createConnections(DB_PROPERTIES.getInitialPoolSize());
    }

    private void createConnections(int poolSize) {
        String url = DB_PROPERTIES.getUrl();
        String user = DB_PROPERTIES.getUser();
        String password = DB_PROPERTIES.getPassword();

        try{
            DriverManager.registerDriver(new org.postgresql.Driver());
        }catch (SQLException e){
            LOGGER.log(Level.FATAL, "Driver cannot be registered");
            throw new UncheckedDAOException("Driver cannot be registered", e);
        }

        try{
            for (int i = 0; i < poolSize; i++){
                Connection$Proxy connection$Proxy = new Connection$Proxy(DriverManager.getConnection(url, user, password));
                connectionPool.add(connection$Proxy);
            }
        }catch (SQLException e){
            LOGGER.log(Level.FATAL, "Cannot connect to database with given properties");
            throw new UncheckedDAOException("Cannot connect to database with given properties", e);
        }
    }

    @Override
    public Connection$Proxy getConnection(){
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
        }catch (SQLException e){
            LOGGER.log(Level.FATAL, "Connection cannot be released");
            throw new UncheckedDAOException("Connection cannot be released", e);
        }

        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    @Override
    public void shutdown() {
        LOGGER.log(Level.INFO, "Shutting down connections...");
        usedConnections.forEach(this::releaseConnection);
        for (Connection$Proxy c : connectionPool){
            c.connectionClose();
        }
        connectionPool.clear();
    }
}
