package by.chyrkun.training.controller.dao.impl;

import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.DAOException;
import by.chyrkun.training.dao.impl.*;
import by.chyrkun.training.model.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskRegistrationDAOTest {
    private static ConnectionPoolImpl connectionPool;
    private RoleDAO roleDAO;
    private UserDAO userDAO;
    private CourseDAO courseDAO;
    private TaskDAO taskDAO;
    private TaskRegistrationDAO taskRegistrationDAO;

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
        taskDAO = new TaskDAO();
        taskRegistrationDAO = new TaskRegistrationDAO();
    }

    @AfterEach
    void close() {
        taskRegistrationDAO.close();
        taskDAO.close();
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
        assertTrue(roleDAO.create(role));

        User user = new User("Teacher", "Password","First name", "Second name", role);
        assertTrue(userDAO.create(user));
        assertNotNull(user = userDAO.getEntityByLogin(user.getLogin()).orElse(null));

        Course course = new Course("Course name", user);
        assertTrue(courseDAO.create(course));
        assertNotNull(course = courseDAO.getAll().get(0));

        Task task = new Task("Test task", LocalDate.now(), LocalDate.now().plusDays(1), course);
        assertTrue(taskDAO.create(task));
        assertNotNull(task = taskDAO.getAll().get(0));

        TaskRegistration taskRegistration = new TaskRegistration(task, user, 7, "Review test");
        assertTrue(taskRegistrationDAO.create(taskRegistration));
        assertNotNull(taskRegistration = taskRegistrationDAO.getAll().get(0));

        assertTrue(taskRegistrationDAO.delete(taskRegistration));
        assertTrue(taskDAO.delete(task));
        assertTrue(courseDAO.delete(course));
        assertTrue(userDAO.delete(user));
        assertTrue(roleDAO.delete(role));
    }
}
