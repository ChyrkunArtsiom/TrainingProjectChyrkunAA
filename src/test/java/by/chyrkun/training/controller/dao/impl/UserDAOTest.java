package by.chyrkun.training.controller.dao.impl;

import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.dao.impl.UserDAO;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import org.junit.jupiter.api.*;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDAOTest {
    private static ConnectionPoolImpl connectionPool;
    private RoleDAO roleDAO;
    private UserDAO userDAO;
    private Role role;

    @BeforeAll
    static void setUp() throws SQLException {
        DriverManager.registerDriver(new org.h2.Driver());
        connectionPool = ConnectionPoolImpl.getInstance();
    }

    @BeforeEach
    void setVariables() {
        roleDAO = new RoleDAO();
        userDAO = new UserDAO();
    }

    @AfterEach
    void close() {
        roleDAO.close();
        userDAO.close();
    }

    @AfterAll
    static void tearDown() {
        connectionPool.shutdown();
    }

    @Test
    void testUserDAO() throws EntityNotFoundDAOException {
        role = new Role("test_role");
        assertTrue(roleDAO.create(role));
        role = roleDAO.getRoleByName(role.getName()).orElseThrow(EntityNotFoundDAOException::new);

        User user = new User("logintest", "passwordtest", "firstname", "secondname", role);
        assertTrue(userDAO.create(user));
        assertNotNull(user = userDAO.getUserByLogin(user.getLogin()).orElse(null));

        assertTrue(userDAO.delete(user));
        assertTrue(roleDAO.delete(role));
    }
}
