package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.CourseNotFoundDAOException;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import by.chyrkun.training.dao.exception.UserNotFoundDAOException;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.CourseRegistration;
import by.chyrkun.training.model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class for interacting with {@link CourseRegistration} table in database.
 * Implements {@link StatementSetter}, {@link ResultMapper}.
 */
public class CourseRegistrationDAO extends AbstractDAO<CourseRegistration>
        implements StatementSetter<CourseRegistration>, ResultMapper<List<CourseRegistration>>{
    /** SQL query for creating course registration. */
    private final static String SQL_CREATE_COURSE_REGISTRATION =
            "INSERT INTO training_schema.course_registration (student_id, course_id) VALUES (?,?)";

    /** SQL query for updating course registration. */
    private final static String SQL_UPDATE_COURSE_REGISTRATION = "UPDATE training_schema.course_registration SET " +
            "student_id = (?), course_id = (?) WHERE course_registration_id = (?)";

    /** SQL query for deleting course registration. */
    private final static String SQL_DELETE_COURSE_REGISTRATION =
            "DELETE FROM training_schema.course_registration WHERE course_registration_id = (?)";

    /** SQL query for getting course registration. */
    private final static String SQL_GET_COURSE_REGISTRATION =
            "SELECT student_id, course_id, course_registration_id " +
                    "FROM training_schema.course_registration WHERE course_registration_id = (?)";

    /** SQL query for getting course registrations. */
    private final static String SQL_GET_COURSE_REGISTRATIONS =
            "SELECT student_id, course_id, course_registration_id FROM training_schema.course_registration";

    /** SQL query for getting course registrations for the student by the course. */
    private final static String SQL_GET_COURSE_REGISTRATION_BY_COURSE_STUDENT = "SELECT course_registration_id " +
            "FROM training_schema.course_registration WHERE course_id = (?) AND student_id = (?)";

    /** Field for logging. */
    private final static Logger LOGGER = LogManager.getLogger(RoleDAO.class);

    /**
     * Constructor with no parameters.
     */
    public CourseRegistrationDAO(){
        setConnection(ConnectionPoolImpl.getInstance().getConnection());
    }

    /**
     * Constructor with connection.
     *
     * @param connection the connection
     */
    public CourseRegistrationDAO(Connection$Proxy connection){
        setConnection(connection);
    }

    @Override
    public boolean create(CourseRegistration courseRegistration)
            throws CourseNotFoundDAOException, UserNotFoundDAOException {
        LOGGER.log(Level.INFO, "Creating course_registration column...");
        AbstractDAO userDAO = new UserDAO(connection);
        AbstractDAO courseDAO = new CourseDAO(connection);
        if (userDAO.getEntityById(courseRegistration.getStudent().getId()).isEmpty()) {
            throw new UserNotFoundDAOException("Cannot create course_registration. User not found");
        }
        if (courseDAO.getEntityById(courseRegistration.getCourse().getId()).isEmpty()) {
            throw new CourseNotFoundDAOException("Cannot create course_registration. Course not found");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_COURSE_REGISTRATION)) {
            preparedStatement.setInt(1, courseRegistration.getStudent().getId());
            preparedStatement.setInt(2, courseRegistration.getCourse().getId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during course_registration creating");
            throw new UncheckedDAOException("Exception during course_registration creating", ex);
        }
        return false;
    }

    @Override
    public boolean delete(CourseRegistration courseRegistration) {
        LOGGER.log(Level.INFO, "Deleting course_registration column...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE_REGISTRATION)) {
            preparedStatement.setInt(1, courseRegistration.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during course_registration deleting");
            throw new UncheckedDAOException("Exception during course_registration deleting", ex);
        }
        return false;
    }

    @Override
    public Optional<CourseRegistration> update(CourseRegistration courseRegistration) {
        LOGGER.log(Level.INFO, "Updating course_registration column...");
        Optional<CourseRegistration> optionalCourseRegistration = getEntityById(courseRegistration.getId());
        if (optionalCourseRegistration.isPresent()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE_REGISTRATION)) {
                setValuesToStatement(preparedStatement, courseRegistration);
                if (preparedStatement.executeUpdate() > 0) {
                    return optionalCourseRegistration;
                }
            }catch (SQLException ex) {
                LOGGER.log(Level.FATAL, "Exception during course_registration updating");
                throw new UncheckedDAOException("Exception during course_registration updating", ex);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CourseRegistration> getEntityById(int id) {
        LOGGER.log(Level.INFO, "Selecting course_registration column by id...");
        int course_registration_id = 0;
        int course_id = 0;
        int teacher_id = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_COURSE_REGISTRATION)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            }
            while (resultSet.next()) {
                course_registration_id = resultSet.getInt("course_registration_id");
                course_id = resultSet.getInt("course_id");
                teacher_id = resultSet.getInt("teacher_id");
            }
            AbstractDAO userDAO = new UserDAO(connection);
            User teacher = (User) userDAO.getEntityById(teacher_id).get();
            AbstractDAO courseDAO = new CourseDAO(connection);
            Course course = (Course) courseDAO.getEntityById(course_id).get();
            return Optional.of(new CourseRegistration(course_registration_id, course, teacher));
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during course_registration reading");
            throw new UncheckedDAOException("Exception during course_registration reading", ex);
        }
    }

    /**
     * Checks if student is registered for this course.
     * Returns {@code true} if student is registered.
     *
     * @param course_id the course id
     * @param student_id the student id
     * @return {@code true} if student is registered to the course
     * @throws UncheckedDAOException if SQLException was thrown
     */
    public boolean isCourseRegistered(int course_id, int student_id){
        LOGGER.log(Level.INFO, "Checking course_registration column by course, student...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_COURSE_REGISTRATION_BY_COURSE_STUDENT)) {
            preparedStatement.setInt(1, course_id);
            preparedStatement.setInt(2, student_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.isBeforeFirst();
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during course_registration checking by course, student");
            throw new UncheckedDAOException("Exception during course_registration checking by course, student", ex);
        }
    }

    @Override
    public List<CourseRegistration> getAll() {
        LOGGER.log(Level.INFO, "Selecting all course registrations...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_COURSE_REGISTRATIONS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }else {
                List<CourseRegistration> courseRegistrations;
                courseRegistrations = getFromResult(resultSet);
                return courseRegistrations;
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during getting course registrations");
            throw new UncheckedDAOException("Exception during getting course registrations", ex);
        }
    }

    @Override
    public void setValuesToStatement(PreparedStatement preparedStatement, CourseRegistration courseRegistration) throws SQLException {
        preparedStatement.setInt(1, courseRegistration.getStudent().getId());
        preparedStatement.setInt(2, courseRegistration.getCourse().getId());
        if (courseRegistration.getId() != 0){
            preparedStatement.setInt(3, courseRegistration.getId());
        }
    }

    @Override
    public List<CourseRegistration> getFromResult(ResultSet resultSet) throws SQLException {
        List<CourseRegistration> courseRegistrations = new ArrayList<>();
        int student_id, course_id, course_registration_id;
        while (resultSet.next()) {
            student_id = resultSet.getInt("student_id");
            course_id = resultSet.getInt("course_id");
            course_registration_id = resultSet.getInt("course_registration_id");
            CourseDAO courseDAO = new CourseDAO(connection);
            UserDAO userDAO = new UserDAO(connection);
            User student = userDAO.getEntityById(student_id).get();
            Course course = courseDAO.getEntityById(course_id).get();
            CourseRegistration courseRegistration = new CourseRegistration(course_registration_id, course, student);
            courseRegistrations.add(courseRegistration);
        }
        return courseRegistrations;
    }
}