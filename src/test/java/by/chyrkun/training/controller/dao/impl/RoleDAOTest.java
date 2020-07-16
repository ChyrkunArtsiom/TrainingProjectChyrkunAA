package by.chyrkun.training.controller.dao.impl;

import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.model.Role;
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
        connectionPool.shutdown();
    }

    @Test
    void testRoleDAO() {
        assertTrue(roleDAO.create(role));
        assertTrue(roleDAO.delete(role));
    }
}
