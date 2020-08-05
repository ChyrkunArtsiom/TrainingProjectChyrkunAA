package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.DAOException;
import by.chyrkun.training.dao.impl.*;
import by.chyrkun.training.model.*;
import org.junit.jupiter.api.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExerciseDAOTest {
    private static ConnectionPoolImpl connectionPool;
    private RoleDAO roleDAO;
    private UserDAO userDAO;
    private CourseDAO courseDAO;
    private TaskDAO taskDAO;
    private ExerciseDAO exerciseDAO;

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
        exerciseDAO = new ExerciseDAO();
    }

    @AfterEach
    void close() {
        exerciseDAO.close();
        taskDAO.close();
        courseDAO.close();
        userDAO.close();
        roleDAO.close();
    }

    @AfterAll
    static void tearDown() {
        connectionPool.shutdown();
    }

    @Test
    void testExerciseDAO() throws DAOException {
        Role role = new Role(1, "teacher");
        assertTrue(roleDAO.create(role));

        User user = new User("Teacher", "Password","First name", "Second name", role);
        assertTrue(userDAO.create(user));
        assertNotNull(user = userDAO.getUserByLogin(user.getLogin()).orElse(null));

        Course course = new Course("Course name", user);
        assertTrue(courseDAO.create(course));
        assertNotNull(course = courseDAO.getAll().get(0));

        Task task = new Task("Test task", LocalDate.now(), LocalDate.now().plusDays(1), course);
        assertTrue(taskDAO.create(task));
        assertNotNull(task = taskDAO.getAll().get(0));

        Exercise exercise = new Exercise(task, user, 7, "Review test");
        assertTrue(exerciseDAO.create(exercise));
        assertNotNull(exercise = exerciseDAO.getAll().get(0));

        assertTrue(exerciseDAO.delete(exercise));
        assertTrue(taskDAO.delete(task));
        assertTrue(courseDAO.delete(course));
        assertTrue(userDAO.delete(user));
        assertTrue(roleDAO.delete(role));
    }
}
