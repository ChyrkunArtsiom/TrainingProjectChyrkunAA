package by.javatr.training.dao.impl;

import by.javatr.training.dao.ConnectionPoolDAO;
import by.javatr.training.dao.config.AppProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPoolImplDAO implements ConnectionPoolDAO {

    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();

    public static ConnectionPoolImplDAO create() throws SQLException {
        AppProperties instance = AppProperties.getInstance();
        int INITIAL_POOL_SIZE = instance.getInitialPoolSize();
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        String url = instance.getUrl();
        String user = instance.getUser();
        String password = instance.getPassword();

        for (int i = 0; i < INITIAL_POOL_SIZE; i++){
            pool.add(createConnection(url, user, password));
        }
        return new ConnectionPoolImplDAO(pool);
    }

    private ConnectionPoolImplDAO(List<Connection> pool){

        connectionPool = new ArrayList<>(pool);
    }

    @Override
    public Connection getConnection(){
        Connection connection = connectionPool.remove(connectionPool.size()-1);
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection){
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    public int getSize(){
        return connectionPool.size() + usedConnections.size();
    }
}
