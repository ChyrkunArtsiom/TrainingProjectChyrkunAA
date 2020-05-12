package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.Task;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class TaskDAO extends AbstractDAO<Task> {
    private final static String SQL_CREATE_TASK =
            "INSERT INTO training_schema.tasks (name, course_id, startdate, deadline) VALUES (?,?,?,?)";
    private final static String SQL_UPDATE_TASK = "UPDATE training_schema.tasks SET " +
            "name = (?), course_id = (?), startdate = (?), deadline = (?) WHERE task_id = (?)";
    private final static String SQL_DELETE_TASK = "DELETE FROM training_schema.tasks WHERE task_id = (?)";
    private final static String SQL_GET_TASK = "SELECT * FROM training_schema.tasks WHERE task_id = (?)";
    private final static Logger LOGGER = LogManager.getLogger(RoleDAO.class);

    public TaskDAO(){
        setConnection(ConnectionPoolImpl.getInstance().getConnection());
    }

    public TaskDAO(Connection$Proxy connection){
        setConnection(connection);
    }

    @Override
    public boolean create(Task task) {
        LOGGER.log(Level.INFO, "Creating task column...");
        AbstractDAO courseDAO = new CourseDAO(this.connection);
        if (courseDAO.getEntityById(task.getCourse().getId()).isEmpty())
            return false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_TASK)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setInt(2, task.getCourse().getId());
            preparedStatement.setDate(3, Date.valueOf(task.getStartdate()));
            if (task.getDeadline() != null)
                preparedStatement.setDate(4, Date.valueOf(task.getDeadline()));
            else
                preparedStatement.setNull(4, Types.DATE);
            if (preparedStatement.executeUpdate() > 0)
                return true;
        } catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during task creating");
            throw new UncheckedDAOException("Exception during task creating", ex);
        }
        return false;
    }

    @Override
    public boolean delete(Task task){
        LOGGER.log(Level.INFO, "Deleting task column...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_TASK)) {
            preparedStatement.setInt(1, task.getId());
            if (preparedStatement.executeUpdate() > 0)
                return true;
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during task deleting");
            throw new UncheckedDAOException("Exception during task deleting", ex);
        }
        return false;
    }

    @Override
    public Optional<Task> update(Task task){
        LOGGER.log(Level.INFO, "Updating task column...");
        Optional<Task> optional = getEntityById(task.getId());
        if (optional.isPresent()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_TASK)) {
                preparedStatement.setString(1, task.getName());
                preparedStatement.setInt(2, task.getCourse().getId());
                preparedStatement.setDate(3, Date.valueOf(task.getStartdate()));
                if (task.getDeadline() != null)
                    preparedStatement.setDate(4, Date.valueOf(task.getDeadline()));
                else
                    preparedStatement.setNull(4, Types.DATE);
                preparedStatement.setInt(5, task.getId());
                if (preparedStatement.executeUpdate() > 0)
                    return optional;
            }catch (SQLException ex){
                LOGGER.log(Level.FATAL, "Exception during user updating");
                throw new UncheckedDAOException("Exception during user updating", ex);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Task> getEntityById(int id){
        LOGGER.log(Level.INFO, "Selecting task column by id...");
        String name = null;
        LocalDate startdate = null;
        LocalDate deadline = null;
        int task_id = 0;
        int course_id = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TASK)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                task_id = resultSet.getInt("task_id");
                name = resultSet.getString("name");
                course_id = resultSet.getInt("course_id");
                startdate = resultSet.getDate("startdate").toLocalDate();
                if (resultSet.getDate("deadline") != null)
                    deadline = resultSet.getDate("deadline").toLocalDate();
            }
            AbstractDAO courseDAO = new CourseDAO(connection);
            Course course = (Course) courseDAO.getEntityById(course_id).get();
            return Optional.of(new Task(task_id, name, startdate, deadline, course));
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during task reading");
            throw new UncheckedDAOException("Exception during task reading", ex);
        }
    }

}
