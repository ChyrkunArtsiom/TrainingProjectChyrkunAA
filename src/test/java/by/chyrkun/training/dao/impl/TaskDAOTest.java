package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.DAOException;
import by.chyrkun.training.dao.impl.CourseDAO;
import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.dao.impl.TaskDAO;
import by.chyrkun.training.dao.impl.UserDAO;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.util.PasswordHasher;
import org.junit.jupiter.api.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskDAOTest {
    private static ConnectionPoolImpl connectionPool;
    private RoleDAO roleDAO;
    private UserDAO userDAO;
    private CourseDAO courseDAO;
    private TaskDAO taskDAO;

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
        taskDAO = new TaskDAO();
    }

    @AfterEach
    void close() {
        roleDAO.close();
        userDAO.close();
        courseDAO.close();
        taskDAO.close();
    }

    @AfterAll
    static void tearDown() {
        connectionPool.shutdown();
    }

    @Test
    void testTaskDAO() throws DAOException {
        Role role = new Role(1, "teacher");
        assertTrue(roleDAO.create(role));

        User user = new User("Teacher", PasswordHasher.getHash("Password"),"First name", "Second name", role);
        assertTrue(userDAO.create(user));
        assertNotNull(user = userDAO.getUserByLogin(user.getLogin()).orElse(null));

        Course course = new Course("Course_test", user);
        assertTrue(courseDAO.create(course));
        assertNotNull(course = courseDAO.getAll().get(0));

        Task task = new Task("task_test", LocalDate.now(), LocalDate.now().plusDays(1), course);
        assertTrue(taskDAO.create(task));
        assertNotNull(task = taskDAO.getAll().get(0));

        assertTrue(taskDAO.delete(task));
        assertTrue(courseDAO.delete(course));
        assertTrue(userDAO.delete(user));
        assertTrue(roleDAO.delete(role));
    }
}
