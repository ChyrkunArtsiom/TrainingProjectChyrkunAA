package by.chyrkun.training.dao.impl;

import by.chyrkun.training.model.Course;
import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.DAOException;
import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import by.chyrkun.training.model.Task;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDAO extends AbstractDAO<Task> implements StatementSetter<Task>, ResultMapper<Task>{
    private final static String SQL_CREATE_TASK =
            "INSERT INTO training_schema.tasks (name, course_id, startdate, deadline) VALUES (?,?,?,?)";
    private final static String SQL_UPDATE_TASK = "UPDATE training_schema.tasks SET " +
            "name = (?), course_id = (?), startdate = (?), deadline = (?) WHERE task_id = (?)";
    private final static String SQL_DELETE_TASK = "DELETE FROM training_schema.tasks WHERE task_id = (?)";
    private final static String SQL_GET_TASK = "SELECT task_id, name, course_id, startdate, deadline " +
            "FROM training_schema.tasks WHERE task_id = (?)";
    private final static String SQL_GET_ALL_TASKS = "SELECT task_id, name, course_id, startdate, deadline " +
            "FROM training_schema.tasks";
    private final static String SQL_GET_TASKS_BY_COURSE = "SELECT task_id, name, course_id, startdate, deadline " +
            "FROM training_schema.tasks WHERE course_id = (?)";
    private final static Logger LOGGER = LogManager.getLogger(RoleDAO.class);

    public TaskDAO(){
        setConnection(ConnectionPoolImpl.getInstance().getConnection());
    }

    public TaskDAO(Connection$Proxy connection){
        setConnection(connection);
    }

    @Override
    public boolean create(Task task) throws EntityNotFoundDAOException {
        LOGGER.log(Level.INFO, "Creating task column...");
        AbstractDAO courseDAO = new CourseDAO(this.connection);
        if (courseDAO.getEntityById(task.getCourse().getId()).isEmpty()){
            throw new EntityNotFoundDAOException("Cannot create task. Course not found");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_TASK)) {
            set(preparedStatement, task);
            if (preparedStatement.executeUpdate() > 0){
                return true;
            }
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
            if (preparedStatement.executeUpdate() > 0){
                return true;
            }
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
                set(preparedStatement, task);
                if (preparedStatement.executeUpdate() > 0){
                    return optional;
                }
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
        Task task = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TASK)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                return Optional.empty();
            }
            while (resultSet.next()){
                task = convert(resultSet);
            }
            return Optional.of(task);
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during task reading");
            throw new UncheckedDAOException("Exception during task reading", ex);
        }
    }

    /*
    public List<Task> getAll(){
        LOGGER.log(Level.INFO, "Selecting all tasks...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_TASKS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                return null;
            }
            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                tasks.add(convert(resultSet));
            }
            return tasks;
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during getting tasks");
            throw new UncheckedDAOException("Exception during getting tasks", ex);
        }
    }*/

    public List<Task> getByCourse(int teacher_id) {
        LOGGER.log(Level.INFO, "Selecting all tasks by course...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TASKS_BY_COURSE)) {
            preparedStatement.setInt(1, teacher_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                tasks.add(convert(resultSet));
            }
            return tasks;
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during getting tasks by course");
            throw new UncheckedDAOException("Exception during getting tasks by course", ex);
        }
    }

    @Override
    public void set(PreparedStatement preparedStatement, Task task) throws SQLException{
        preparedStatement.setString(1, task.getName());
        preparedStatement.setInt(2, task.getCourse().getId());
        preparedStatement.setDate(3, Date.valueOf(task.getStartdate()));
        if (task.getDeadline() != null){
            preparedStatement.setDate(4, Date.valueOf(task.getDeadline()));
        }
        else{
            preparedStatement.setNull(4, Types.DATE);
        }
        if (task.getId() != 0){
            preparedStatement.setInt(5, task.getId());
        }
    }

    @Override
    public Task convert(ResultSet resultSet) throws SQLException {
        String name;
        int task_id, course_id;
        Task task;
        LocalDate startdate;
        LocalDate deadline = null;
        task_id = resultSet.getInt("task_id");
        name = resultSet.getString("name");
        course_id = resultSet.getInt("course_id");
        startdate = resultSet.getDate("startdate").toLocalDate();
        if (resultSet.getDate("deadline") != null){
            deadline = resultSet.getDate("deadline").toLocalDate();
        }
        AbstractDAO courseDAO = new CourseDAO(connection);
        Course course = (Course) courseDAO.getEntityById(course_id).get();
        task = new Task(task_id, name, startdate, deadline, course);
        return task;
    }
}
