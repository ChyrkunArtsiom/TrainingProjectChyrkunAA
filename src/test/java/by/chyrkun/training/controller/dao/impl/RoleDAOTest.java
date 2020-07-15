package by.chyrkun.training.controller.dao.impl;

import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.model.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RoleDAOTest {
    private static ConnectionPoolImpl connectionPool;
    private RoleDAO roleDAO;
    private Role role;

    @BeforeAll
    static void setUp() throws SQLException {
        Logger logger = LogManager.getLogger(CourseDAOTest.class);
        logger.log(Level.INFO, "Starting test for roleDAO");
        DriverManager.registerDriver(new org.h2.Driver());
        connectionPool = ConnectionPoolImpl.getInstance();
    }

    @BeforeEach
    void setVariables() {
        roleDAO = new RoleDAO();
        role = new Role(5, "test_role");
    }

    @AfterEach
    void close() {
        roleDAO.close();
    }

    @AfterAll
    static void tearDown() {
        Logger logger = LogManager.getLogger(CourseDAOTest.class);
        logger.log(Level.INFO, "Finishing test for roleDAO");
        connectionPool.shutdown();
    }

    @Test
    void testRoleDAO() {
        assertTrue(roleDAO.create(role));
        assertTrue(roleDAO.delete(role));
    }
}
