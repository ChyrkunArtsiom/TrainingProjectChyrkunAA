package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.TaskNotFoundDAOException;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import by.chyrkun.training.dao.exception.UserNotFoundDAOException;
import by.chyrkun.training.model.Exercise;
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

/**
 * Class for interacting with {@link Exercise} table in database.
 * Implements {@link StatementSetter}, {@link ResultMapper}.
 */
public class ExerciseDAO extends AbstractDAO<Exercise>
        implements StatementSetter<Exercise>, ResultMapper<Exercise>{
    /** SQL query for creating exercise. */
    private final static String SQL_CREATE_EXERCISE =
            "INSERT INTO training_schema.exercise (task_id, student_id, grade, review) VALUES (?,?,?,?)";

    /** SQL query for updating exercise. */
    private final static String SQL_UPDATE_EXERCISE = "UPDATE training_schema.exercise SET " +
            "task_id = (?), student_id = (?), grade = (?), review = (?) WHERE exercise_id = (?)";

    /** SQL query for deleting exercise. */
    private final static String SQL_DELETE_EXERCISE =
            "DELETE FROM training_schema.exercise WHERE exercise_id = (?)";

    /** SQL query for getting exercise. */
    private final static String SQL_GET_EXERCISE =
            "SELECT task_id, student_id, grade, review, exercise_id " +
                    "FROM training_schema.exercise WHERE exercise_id = (?)";

    /** SQL query for getting exercises. */
    private final static String SQL_GET_EXERCISES =
            "SELECT task_id, student_id, grade, review, exercise_id FROM training_schema.exercise";

    /** SQL query for getting exercise for the student by the task. */
    private final static String SQL_GET_EXERCISE_BY_TASK_STUDENT =
            "SELECT exercise_id, task_id, student_id, grade, review " +
            "FROM training_schema.exercise WHERE task_id = (?) AND student_id = (?)";

    /** SQL query for getting exercises by the task. */
    private final static String SQL_GET_EXERCISES_BY_TASK =
            "SELECT exercise_id, task_id, student_id, grade, review " +
                    "FROM training_schema.exercise WHERE task_id = (?)";

    /** Field for logging. */
    private final static Logger LOGGER = LogManager.getLogger(RoleDAO.class);

    /**
     * Constructor with no parameters.
     */
    public ExerciseDAO(){
        setConnection(ConnectionPoolImpl.getInstance().getConnection());
    }

    /**
     * Constructor with connection.
     *
     * @param connection the connection
     */
    public ExerciseDAO(Connection$Proxy connection){
        setConnection(connection);
    }

    @Override
    public boolean create(Exercise exercise) throws TaskNotFoundDAOException, UserNotFoundDAOException {
        LOGGER.log(Level.INFO, "Creating exercise column...");
        AbstractDAO userDAO = new UserDAO(connection);
        AbstractDAO taskDAO = new TaskDAO(connection);
        if (userDAO.getEntityById(exercise.getStudent().getId()).isEmpty()) {
            throw new UserNotFoundDAOException("Cannot create exercise. User not found");
        }
        if (taskDAO.getEntityById(exercise.getTask().getId()).isEmpty()) {
            throw new TaskNotFoundDAOException("Cannot create exercise. Task not found");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_EXERCISE)) {
            setValuesToStatement(preparedStatement, exercise);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during exercise creating");
            throw new UncheckedDAOException("Exception during exercise creating", ex);
        }
        return false;
    }

    @Override
    public boolean delete(Exercise exercise) {
        LOGGER.log(Level.INFO, "Deleting exercise column...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_EXERCISE)) {
            preparedStatement.setInt(1, exercise.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during exercise deleting");
            throw new UncheckedDAOException("Exception during exercise deleting", ex);
        }
        return false;
    }

    @Override
    public Optional<Exercise> update(Exercise exercise) {
        LOGGER.log(Level.INFO, "Updating exercise column...");
        Optional<Exercise> optionalExercise = getEntityById(exercise.getId());
        if (optionalExercise.isPresent()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_EXERCISE)) {
                setValuesToStatement(preparedStatement, exercise);
                if (preparedStatement.executeUpdate() > 0) {
                    return optionalExercise;
                }
            }catch (SQLException ex) {
                LOGGER.log(Level.FATAL, "Exception during exercise updating");
                throw new UncheckedDAOException("Exception during exercise updating", ex);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Exercise> getEntityById(int id) {
        LOGGER.log(Level.INFO, "Selecting exercise column by id...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_EXERCISE)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            }
            else {
                resultSet.next();
                Exercise exercise = getFromResult(resultSet);
                return Optional.of(exercise);
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during exercise reading");
            throw new UncheckedDAOException("Exception during exercise reading", ex);
        }
    }

    /**
     * Checks if exercise is performed by the student.
     * Returns {@code true} if exercise is performed.
     *
     * @param task_id the task id
     * @param student_id the student id
     * @return {@code true} if exercise is performed, {@code false} if exercise is not performed
     * @throws UncheckedDAOException if SQLException was thrown
     */
    public boolean isPerformed(int task_id, int student_id) {
        LOGGER.log(Level.INFO, "Selecting exercise column by task, student...");
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_GET_EXERCISE_BY_TASK_STUDENT)) {
            preparedStatement.setInt(1, task_id);
            preparedStatement.setInt(2, student_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.isBeforeFirst();
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during exercise reading by task, student");
            throw new UncheckedDAOException("Exception during exercise reading by task, student", ex);
        }
    }

    /**
     * Gets exercise for the student by the task. Returns Optional of {@link Exercise}.
     *
     * @param task_id the task id
     * @param student_id the student id
     * @return Optional of {@link Exercise}.
     * @throws UncheckedDAOException if SQLException was thrown
     */
    public Optional<Exercise> getExerciseByTaskStudent(int task_id, int student_id) {
        LOGGER.log(Level.INFO, "Selecting exercise column by task, student...");
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_GET_EXERCISE_BY_TASK_STUDENT)) {
            preparedStatement.setInt(1, task_id);
            preparedStatement.setInt(2, student_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            }
            else {
                resultSet.next();
                Exercise exercise = getFromResult(resultSet);
                return Optional.of(exercise);
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during exercises reading by task, student");
            throw new UncheckedDAOException("Exception during exercises reading by task, student", ex);
        }
    }

    /**
     * Gets all exercises by the task. Returns list of exercises.
     *
     * @param task_id the task id
     * @return list of exercises
     * @throws UncheckedDAOException if SQLException was thrown
     */
    public List<Exercise> getAllByTask(int task_id) {
        LOGGER.log(Level.INFO, "Selecting exercises by task...");
        List<Exercise> registrations = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_EXERCISES_BY_TASK)) {
            preparedStatement.setInt(1, task_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            else {
                while (resultSet.next()) {
                    Exercise exercise = getFromResult(resultSet);
                    registrations.add(exercise);
                }
                return registrations;
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during exercises reading by task");
            throw new UncheckedDAOException("Exception during exercises reading by task", ex);
        }
    }

    @Override
    public List<Exercise> getAll() {
        LOGGER.log(Level.INFO, "Selecting all exercises...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_EXERCISES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }else {
                List<Exercise> exercises;
                exercises = convertToList(resultSet);
                return exercises;
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during getting exercises");
            throw new UncheckedDAOException("Exception during getting exercises", ex);
        }
    }

    /**
     * Gets list of exercises from {@link ResultSet} object. Returns list of exercises.
     *
     * @param resultSet {@link ResultSet} object
     * @return list of exercises
     * @throws SQLException if SQLException is thrown in method
     */
    private List<Exercise> convertToList(ResultSet resultSet) throws SQLException{
        List<Exercise> exercises = new ArrayList<>();
        Exercise exercise;
        while (resultSet.next()) {
            exercise = getFromResult(resultSet);
            exercises.add(exercise);
        }
        return exercises;
    }

    @Override
    public void setValuesToStatement(PreparedStatement preparedStatement, Exercise exercise) throws SQLException {
        preparedStatement.setInt(1, exercise.getTask().getId());
        preparedStatement.setInt(2, exercise.getStudent().getId());
        if (exercise.getGrade() != 0) {
            preparedStatement.setInt(3, exercise.getGrade());
        }
        else {
            preparedStatement.setNull(3, Types.INTEGER);
        }
        if (exercise.getReview() != null) {
            preparedStatement.setString(4, exercise.getReview());
        }
        else {
            preparedStatement.setNull(4, Types.VARCHAR);
        }
        if (exercise.getId() != 0) {
            preparedStatement.setInt(5, exercise.getId());
        }
    }

    @Override
    public Exercise getFromResult(ResultSet resultSet) throws SQLException {
        int exercise_id = resultSet.getInt("exercise_id");
        int task_id = resultSet.getInt("task_id");
        int student_id = resultSet.getInt("student_id");
        int grade = resultSet.getInt("grade");
        String review = resultSet.getString("review");
        AbstractDAO userDAO = new UserDAO(connection);
        User student = (User) userDAO.getEntityById(student_id).get();
        AbstractDAO taskDAO = new TaskDAO(connection);
        Task task = (Task) taskDAO.getEntityById(task_id).get();
        return new Exercise(exercise_id, task, student, grade, review);
    }
}
