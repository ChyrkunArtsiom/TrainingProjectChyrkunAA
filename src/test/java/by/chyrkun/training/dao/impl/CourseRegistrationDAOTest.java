package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.DAOException;
import by.chyrkun.training.dao.impl.CourseDAO;
import by.chyrkun.training.dao.impl.CourseRegistrationDAO;
import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.dao.impl.UserDAO;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.CourseRegistration;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.util.PasswordHasher;
import org.junit.jupiter.api.*;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseRegistrationDAOTest {
    private static ConnectionPoolImpl connectionPool;
    private RoleDAO roleDAO;
    private UserDAO userDAO;
    private CourseDAO courseDAO;
    private CourseRegistrationDAO courseRegistrationDAO;

    @BeforeAll
    static void setUp() throws SQLException {
        DriverManager.registerDriver(new org.h2.Driver());
        connectionPool = ConnectionPoolImpl.getInstance();
    }

    @BeforeEach
    void setVariables() {
        roleDAO = new RoleDAO();
        userDAO = new UserDAO();
        courseDAO = new CourseDAO();
        courseRegistrationDAO = new CourseRegistrationDAO();
    }

    @AfterEach
    void close() {
        courseRegistrationDAO.close();
        courseDAO.close();
        userDAO.close();
        roleDAO.close();
    }

    @AfterAll
    static void tearDown() {
        connectionPool.shutdown();
    }

    @Test
    void testCourseRegistrationDAO() throws DAOException {
        Role role = new Role(1, "teacher");
        assertTrue(roleDAO.create(role));

        User user = new User("Teacher", PasswordHasher.getHash("Password"),"First name", "Second name", role);
        assertTrue(userDAO.create(user));
        assertNotNull(user = userDAO.getUserByLogin(user.getLogin()).orElse(null));

        Course course = new Course("Course name", user);
        assertTrue(courseDAO.create(course));
        assertNotNull(course = courseDAO.getAll().get(0));

        CourseRegistration courseRegistration = new CourseRegistration(course, user);
        assertTrue(courseRegistrationDAO.create(courseRegistration));
        assertNotNull(courseRegistration = courseRegistrationDAO.getAll().get(0));

        assertTrue(courseRegistrationDAO.delete(courseRegistration));
        assertTrue(courseDAO.delete(course));
        assertTrue(userDAO.delete(user));
        assertTrue(roleDAO.delete(role));
    }
}
