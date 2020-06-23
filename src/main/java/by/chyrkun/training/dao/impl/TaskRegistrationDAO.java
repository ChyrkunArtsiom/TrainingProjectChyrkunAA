package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.*;
import by.chyrkun.training.model.TaskRegistration;
import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRegistrationDAO extends AbstractDAO<TaskRegistration> implements StatementSetter<TaskRegistration>{
    private final static String SQL_CREATE_TASK_REGISTRATION =
            "INSERT INTO training_schema.task_registration (task_id, student_id, grade, review) VALUES (?,?,?,?)";
    private final static String SQL_UPDATE_TASK_REGISTRATION = "UPDATE training_schema.task_registration SET " +
            "task_id = (?), student_id = (?), grade = (?), review = (?) WHERE task_registration_id = (?)";
    private final static String SQL_DELETE_TASK_REGISTRATION =
            "DELETE FROM training_schema.task_registration WHERE task_registration_id = (?)";
    private final static String SQL_GET_TASK_REGISTRATION =
            "SELECT task_id, student_id, grade, review, task_registration_id " +
                    "FROM training_schema.task_registration WHERE task_registration_id = (?)";
    private final static String SQL_GET_TASK_REGISTRATION_BY_TASK_STUDENT =
            "SELECT task_registration_id, task_id, student_id, grade, review " +
            "FROM training_schema.task_registration WHERE task_id = (?) AND student_id = (?)";
    private final static String SQL_GET_TASK_REGISTRATIONS_BY_TASK =
            "SELECT task_registration_id, task_id, student_id, grade, review " +
                    "FROM training_schema.task_registration WHERE task_id = (?)";
    private final static Logger LOGGER = LogManager.getLogger(RoleDAO.class);

    public TaskRegistrationDAO(){
        setConnection(ConnectionPoolImpl.getInstance().getConnection());
    }

    public TaskRegistrationDAO(Connection$Proxy connection){
        setConnection(connection);
    }

    @Override
    public boolean create(TaskRegistration taskRegistration) throws TaskNotFoundDAOException, UserNotFoundDAOException {
        LOGGER.log(Level.INFO, "Creating task_registration column...");
        AbstractDAO userDAO = new UserDAO(connection);
        AbstractDAO taskDAO = new TaskDAO(connection);
        if (userDAO.getEntityById(taskRegistration.getStudent().getId()).isEmpty()) {
            throw new UserNotFoundDAOException("Cannot create task_registration. User not found");
        }
        if (taskDAO.getEntityById(taskRegistration.getTask().getId()).isEmpty()) {
            throw new TaskNotFoundDAOException("Cannot create task_registration. Task not found");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_TASK_REGISTRATION)) {
            set(preparedStatement, taskRegistration);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during task_registration creating");
            throw new UncheckedDAOException("Exception during task_registration creating", ex);
        }
        return false;
    }

    @Override
    public boolean delete(TaskRegistration taskRegistration) {
        LOGGER.log(Level.INFO, "Deleting task_registration column...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_TASK_REGISTRATION)) {
            preparedStatement.setInt(1, taskRegistration.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during task_registration deleting");
            throw new UncheckedDAOException("Exception during task_registration deleting", ex);
        }
        return false;
    }

    @Override
    public Optional<TaskRegistration> update(TaskRegistration taskRegistration) {
        LOGGER.log(Level.INFO, "Updating task_registration column...");
        Optional<TaskRegistration> optionalTaskRegistration = getEntityById(taskRegistration.getId());
        if (optionalTaskRegistration.isPresent()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_TASK_REGISTRATION)) {
                set(preparedStatement, taskRegistration);
                if (preparedStatement.executeUpdate() > 0) {
                    return optionalTaskRegistration;
                }
            }catch (SQLException ex) {
                LOGGER.log(Level.FATAL, "Exception during task_registration updating");
                throw new UncheckedDAOException("Exception during task_registration updating", ex);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<TaskRegistration> getEntityById(int id) {
        LOGGER.log(Level.INFO, "Selecting task_registration column by id...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TASK_REGISTRATION)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            }
            else {
                resultSet.next();
                TaskRegistration taskRegistration = insertValuesFromResult(resultSet);
                return Optional.of(taskRegistration);
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during task_registration reading");
            throw new UncheckedDAOException("Exception during task_registration reading", ex);
        }
    }

    public boolean isPerformed(int task_id, int student_id) {
        LOGGER.log(Level.INFO, "Selecting task_registration column by task, student...");
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_GET_TASK_REGISTRATION_BY_TASK_STUDENT)) {
            preparedStatement.setInt(1, task_id);
            preparedStatement.setInt(2, student_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.isBeforeFirst();
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during task_registration reading by task, student");
            throw new UncheckedDAOException("Exception during task_registration reading by task, student", ex);
        }
    }

    public Optional<TaskRegistration> getTaskRegistrationByTaskStudent(int task_id, int student_id) {
        LOGGER.log(Level.INFO, "Selecting task_registration column by task, student...");
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_GET_TASK_REGISTRATION_BY_TASK_STUDENT)) {
            preparedStatement.setInt(1, task_id);
            preparedStatement.setInt(2, student_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            }
            else {
                resultSet.next();
                TaskRegistration taskRegistration = insertValuesFromResult(resultSet);
                return Optional.of(taskRegistration);
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during task_registration reading by task, student");
            throw new UncheckedDAOException("Exception during task_registration reading by task, student", ex);
        }
    }

    public List<TaskRegistration> getAllByTask(int task_id) {
        LOGGER.log(Level.INFO, "Selecting task_registrations task...");
        List<TaskRegistration> registrations = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TASK_REGISTRATIONS_BY_TASK)) {
            preparedStatement.setInt(1, task_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            else {
                while (resultSet.next()) {
                    TaskRegistration taskRegistration = insertValuesFromResult(resultSet);
                    registrations.add(taskRegistration);
                }
                return registrations;
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during task_registrations reading by task");
            throw new UncheckedDAOException("Exception during task_registrations reading by task", ex);
        }
    }

    @Override
    public void set(PreparedStatement preparedStatement, TaskRegistration taskRegistration) throws SQLException {
        preparedStatement.setInt(1, taskRegistration.getTask().getId());
        preparedStatement.setInt(2, taskRegistration.getStudent().getId());
        if (taskRegistration.getGrade() != 0) {
            preparedStatement.setInt(3, taskRegistration.getGrade());
        }
        else {
            preparedStatement.setNull(3, Types.INTEGER);
        }
        if (taskRegistration.getReview() != null) {
            preparedStatement.setString(4, taskRegistration.getReview());
        }
        else {
            preparedStatement.setNull(4, Types.VARCHAR);
        }
        if (taskRegistration.getId() != 0) {
            preparedStatement.setInt(5, taskRegistration.getId());
        }
    }

    private TaskRegistration insertValuesFromResult(ResultSet result) throws SQLException{
        int task_registration_id = result.getInt("task_registration_id");
        int task_id = result.getInt("task_id");
        int student_id = result.getInt("student_id");
        int grade = result.getInt("grade");
        String review = result.getString("review");
        AbstractDAO userDAO = new UserDAO(connection);
        User student = (User) userDAO.getEntityById(student_id).get();
        AbstractDAO taskDAO = new TaskDAO(connection);
        Task task = (Task) taskDAO.getEntityById(task_id).get();
        return new TaskRegistration(task_registration_id, task, student, grade, review);
    }
}
