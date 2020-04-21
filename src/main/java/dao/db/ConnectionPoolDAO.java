package dao.db;

import dao.db.impl.Connection$Proxy;
import java.sql.SQLException;

public interface ConnectionPoolDAO {
    Connection$Proxy getConnection();
    boolean releaseConnection(Connection$Proxy connection) throws SQLException;
    void destroyConnections();
}
