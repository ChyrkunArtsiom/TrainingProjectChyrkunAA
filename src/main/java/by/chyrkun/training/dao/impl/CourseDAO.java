package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CourseDAO extends AbstractDAO<Course> {
    private final static String SQL_CREATE_COURSE =
            "INSERT INTO training_schema.courses (name, teacher_id) VALUES (?,?)";
    private final static String SQL_UPDATE_COURSE = "UPDATE training_schema.courses SET " +
            "name = (?), teacher_id = (?) WHERE course_id = (?)";
    private final static String SQL_DELETE_COURSE = "DELETE FROM training_schema.courses WHERE course_id = (?)";
    private final static String SQL_GET_COURSE = "SELECT * FROM training_schema.courses WHERE course_id = (?)";
    private final static Logger LOGGER = LogManager.getLogger(RoleDAO.class);

    public CourseDAO(){
        setConnection(ConnectionPoolImpl.getInstance().getConnection());
    }

    public CourseDAO(Connection$Proxy connection){
        setConnection(connection);
    }

    @Override
    public boolean create(Course course) {
        LOGGER.log(Level.INFO, "Creating course column...");
        AbstractDAO userDAO = new UserDAO(this.connection);
        if (userDAO.getEntityById(course.getTeacher().getId()).isEmpty())
            return false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_COURSE)) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setInt(2, course.getTeacher().getId());
            if (preparedStatement.executeUpdate() > 0)
                return true;
        } catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during course creating");
            throw new UncheckedDAOException("Exception during course creating", ex);
        }
        return false;
    }

    @Override
    public boolean delete(Course course){
        LOGGER.log(Level.INFO, "Deleting course column...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE)) {
            preparedStatement.setInt(1, course.getId());
            if (preparedStatement.executeUpdate() > 0)
                return true;
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during course deleting");
            throw new UncheckedDAOException("Exception during course deleting", ex);
        }
        return false;
    }

    @Override
    public Optional<Course> update(Course course){
        LOGGER.log(Level.INFO, "Updating course column...");
        Optional<Course> optionalCourse = getEntityById(course.getId());
        if (optionalCourse.isPresent()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE)) {
                preparedStatement.setString(1, course.getName());
                preparedStatement.setInt(2, course.getTeacher().getId());
                preparedStatement.setInt(3, course.getId());
                if (preparedStatement.executeUpdate() > 0)
                    return optionalCourse;
            }catch (SQLException ex){
                LOGGER.log(Level.FATAL, "Exception during course updating");
                throw new UncheckedDAOException("Exception during course updating", ex);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Course> getEntityById(int id){
        LOGGER.log(Level.INFO, "Selecting course column by id...");
        String name = null;
        int course_id = -1;
        int teacher_id = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_COURSE)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                course_id = resultSet.getInt("course_id");
                name = resultSet.getString("name");
                teacher_id = resultSet.getInt("teacher_id");
            }
            AbstractDAO userDAO = new UserDAO();
            User user = (User) userDAO.getEntityById(teacher_id).get();
            return Optional.of(new Course(course_id, name, user));
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during course reading");
            throw new UncheckedDAOException("Exception during course reading", ex);
        }
    }
}
