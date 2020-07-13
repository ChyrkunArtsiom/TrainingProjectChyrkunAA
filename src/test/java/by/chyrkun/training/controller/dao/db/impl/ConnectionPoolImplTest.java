package by.chyrkun.training.controller.dao.db.impl;

import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionPoolImplTest {

    private static ConnectionPoolImpl connectionPool;

    @BeforeAll
    static void setUp() throws SQLException{
        Logger logger = LogManager.getLogger(ConnectionPoolImplTest.class);
        logger.log(Level.INFO, "Starting test for connection pooling");
        DriverManager.registerDriver(new org.h2.Driver());
        connectionPool = ConnectionPoolImpl.getInstance();
    }

    @AfterAll
    static void tearDown() {
        Logger logger = LogManager.getLogger(ConnectionPoolImplTest.class);
        logger.log(Level.INFO, "Finishing test for connection pooling");
        connectionPool.shutdown();
    }

    @Test
    void testConnection() throws SQLException{
        Connection$Proxy connection = connectionPool.getConnection();
        assertTrue(connection.isValid(1));
        assertTrue(connectionPool.releaseConnection(connection));
    }
}