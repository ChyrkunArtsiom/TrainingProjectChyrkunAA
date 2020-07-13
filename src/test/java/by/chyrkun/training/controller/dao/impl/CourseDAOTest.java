package by.chyrkun.training.controller.dao.impl;

import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.DAOException;
import by.chyrkun.training.dao.impl.CourseDAO;
import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.dao.impl.UserDAO;
import by.chyrkun.training.model.Course;
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

class CourseDAOTest {

    private static ConnectionPoolImpl connectionPool;
    private RoleDAO roleDAO;
    private UserDAO userDAO;
    private CourseDAO courseDAO;

    @BeforeAll
    static void setUp() throws SQLException {
        Logger logger = LogManager.getLogger(CourseDAOTest.class);
        logger.log(Level.INFO, "Starting test for courseDAO");
        DriverManager.registerDriver(new org.h2.Driver());
        connectionPool = ConnectionPoolImpl.getInstance();
    }

    @BeforeEach
    void setVariables() {
        roleDAO = new RoleDAO();
        userDAO = new UserDAO();
        courseDAO = new CourseDAO();
    }

    @AfterEach
    void close() {
        courseDAO.close();
        userDAO.close();
        roleDAO.close();
    }

    @AfterAll
    static void tearDown() {
        Logger logger = LogManager.getLogger(CourseDAOTest.class);
        logger.log(Level.INFO, "Finishing test for courseDAO");
        connectionPool.shutdown();
    }

    @Test
    void testConnection() throws DAOException {
        Role role = new Role(1, "teacher");
        User teacher = new User("Teacher", "Password","First name", "Second name", role);
        roleDAO.create(role);
        userDAO.create(teacher);
        assertNotNull(teacher = userDAO.getEntityByLogin(teacher.getLogin()).orElse(null));
        Course course = new Course("Course name", teacher);
        assertTrue(courseDAO.create(course));
    }
}
