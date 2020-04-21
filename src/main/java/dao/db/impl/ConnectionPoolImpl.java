package dao.db.impl;

import dao.db.ConnectionPoolDAO;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static dao.db.config.AppProperties.INSTANCE;

public enum ConnectionPoolImpl implements ConnectionPoolDAO {
    CONNECTION_POOL_INSTANCE;

    private List<Connection$Proxy> connectionPool = new ArrayList<>();
    private List<Connection$Proxy> usedConnections = new ArrayList<>();

    public static ConnectionPoolImpl getInstance() {

        return CONNECTION_POOL_INSTANCE;
    }

    ConnectionPoolImpl() {
        initConnectionPool();
    }

    private void initConnectionPool() {
        createConnections(INSTANCE.getInitialPoolSize());
    }

    private void createConnections(int poolSize) {
        String url = INSTANCE.getUrl();
        String user = INSTANCE.getUser();
        String password = INSTANCE.getPassword();

        try{
            DriverManager.registerDriver(new org.postgresql.Driver());
            for (int i = 0; i < poolSize; i++){
                Connection$Proxy connection$Proxy = new Connection$Proxy(DriverManager.getConnection(url, user, password));
                connectionPool.add(connection$Proxy);
            }
        }catch (SQLException e){
            System.out.println(e);      //need to handle
        }
    }

    @Override
    public Connection$Proxy getConnection(){
        Connection$Proxy connection$Proxy = connectionPool.remove(connectionPool.size()-1);
        usedConnections.add(connection$Proxy);
        return connection$Proxy;
    }

    @Override
    public boolean releaseConnection(Connection$Proxy connection) throws SQLException {
        if (connection != null) {
            connection.setAutoCommit(true);
        }
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public int getSize(){
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public void destroyConnections() {

    }
}
