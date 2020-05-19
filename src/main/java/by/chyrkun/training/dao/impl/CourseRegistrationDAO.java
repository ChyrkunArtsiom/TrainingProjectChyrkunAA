package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.DAOException;
import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.CourseRegistration;
import by.chyrkun.training.model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CourseRegistrationDAO extends AbstractDAO<CourseRegistration>
        implements StatementSetter<CourseRegistration>{
    private final static String SQL_CREATE_COURSE_REGISTRATION =
            "INSERT INTO training_schema.course_registration (student_id, course_id) VALUES (?,?)";
    private final static String SQL_UPDATE_COURSE_REGISTRATION = "UPDATE training_schema.course_registration SET " +
            "student_id = (?), course_id = (?) WHERE course_registration_id = (?)";
    private final static String SQL_DELETE_COURSE_REGISTRATION =
            "DELETE FROM training_schema.course_registration WHERE course_registration_id = (?)";
    private final static String SQL_GET_COURSE_REGISTRATION =
            "SELECT * FROM training_schema.course_registration WHERE course_registration_id = (?)";
    private final static Logger LOGGER = LogManager.getLogger(RoleDAO.class);

    public CourseRegistrationDAO(){
        setConnection(ConnectionPoolImpl.getInstance().getConnection());
    }

    public CourseRegistrationDAO(Connection$Proxy connection){
        setConnection(connection);
    }

    @Override
    public boolean create(CourseRegistration courseRegistration) throws DAOException {
        LOGGER.log(Level.INFO, "Creating course_registration column...");
        AbstractDAO userDAO = new UserDAO(connection);
        AbstractDAO courseDAO = new CourseDAO(connection);
        if (userDAO.getEntityById(courseRegistration.getStudent().getId()).isEmpty())
            throw new EntityNotFoundDAOException("Cannot create course_registration. User not found");
        if (courseDAO.getEntityById(courseRegistration.getCourse().getId()).isEmpty())
            throw new EntityNotFoundDAOException("Cannot create course_registration. Course not found");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_COURSE_REGISTRATION)) {
            set(preparedStatement, courseRegistration);
            if (preparedStatement.executeUpdate() > 0)
                return true;
        } catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during course_registration creating");
            throw new UncheckedDAOException("Exception during course_registration creating", ex);
        }
        return false;
    }

    @Override
    public boolean delete(CourseRegistration courseRegistration){
        LOGGER.log(Level.INFO, "Deleting course_registration column...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE_REGISTRATION)) {
            preparedStatement.setInt(1, courseRegistration.getId());
            if (preparedStatement.executeUpdate() > 0)
                return true;
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during course_registration deleting");
            throw new UncheckedDAOException("Exception during course_registration deleting", ex);
        }
        return false;
    }

    @Override
    public Optional<CourseRegistration> update(CourseRegistration courseRegistration){
        LOGGER.log(Level.INFO, "Updating course_registration column...");
        Optional<CourseRegistration> optionalCourseRegistration = getEntityById(courseRegistration.getId());
        if (optionalCourseRegistration.isPresent()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE_REGISTRATION)) {
                set(preparedStatement, courseRegistration);
                if (preparedStatement.executeUpdate() > 0)
                    return optionalCourseRegistration;
            }catch (SQLException ex){
                LOGGER.log(Level.FATAL, "Exception during course_registration updating");
                throw new UncheckedDAOException("Exception during course_registration updating", ex);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CourseRegistration> getEntityById(int id){
        LOGGER.log(Level.INFO, "Selecting course_registration column by id...");
        int course_registration_id = 0;
        int course_id = 0;
        int teacher_id = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_COURSE_REGISTRATION)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst())
                return Optional.empty();
            while (resultSet.next()){
                course_registration_id = resultSet.getInt("course_registration_id");
                course_id = resultSet.getInt("course_id");
                teacher_id = resultSet.getInt("teacher_id");
            }
            AbstractDAO userDAO = new UserDAO(connection);
            User teacher = (User) userDAO.getEntityById(teacher_id).get();
            AbstractDAO courseDAO = new CourseDAO(connection);
            Course course = (Course) courseDAO.getEntityById(course_id).get();
            return Optional.of(new CourseRegistration(course_registration_id, course, teacher));
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during course_registration reading");
            throw new UncheckedDAOException("Exception during course_registration reading", ex);
        }
    }

    @Override
    public void set(PreparedStatement preparedStatement, CourseRegistration courseRegistration) throws SQLException {
        preparedStatement.setInt(1, courseRegistration.getStudent().getId());
        preparedStatement.setInt(2, courseRegistration.getCourse().getId());
        if (courseRegistration.getId() != 0)
            preparedStatement.setInt(3, courseRegistration.getId());
    }
}
