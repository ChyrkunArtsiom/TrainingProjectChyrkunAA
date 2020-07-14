package by.chyrkun.training.controller.dao.impl;

import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.dao.impl.UserDAO;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDAOTest {
    private static ConnectionPoolImpl connectionPool;
    private RoleDAO roleDAO;
    private UserDAO userDAO;
    private Role role;

    @BeforeAll
    static void setUp() throws SQLException {
        Logger logger = LogManager.getLogger(CourseDAOTest.class);
        logger.log(Level.INFO, "Starting test for userDAO");
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
        Logger logger = LogManager.getLogger(CourseDAOTest.class);
        logger.log(Level.INFO, "Finishing test for userDAO");
        connectionPool.shutdown();
    }

    @Test
    void testConnection() throws EntityNotFoundDAOException {
        role = new Role("test_role");
        assertTrue(roleDAO.create(role));
        role = roleDAO.getEntityByName(role.getName()).orElseThrow(EntityNotFoundDAOException::new);

        User user = new User("logintest", "passwordtest", "firstname", "secondname", role);
        assertTrue(userDAO.create(user));
        assertNotNull(user = userDAO.getEntityByLogin(user.getLogin()).orElse(null));

        assertTrue(userDAO.delete(user));
        assertTrue(roleDAO.delete(role));
    }
}
