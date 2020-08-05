package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import by.chyrkun.training.dao.exception.UserNotFoundDAOException;
import by.chyrkun.training.model.Course;
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
 * Class for interacting with {@link Course} table in database. Implements {@link ResultMapper}.
 */
public class CourseDAO extends AbstractDAO<Course> implements ResultMapper<List<Course>> {
    /** SQL query for creating course. */
    private final static String SQL_CREATE_COURSE =
            "INSERT INTO training_schema.courses (name, teacher_id) VALUES (?,?)";

    /** SQL query for updating course. */
    private final static String SQL_UPDATE_COURSE = "UPDATE training_schema.courses SET " +
            "name = (?), teacher_id = (?) WHERE course_id = (?)";

    /** SQL query for deleting course. */
    private final static String SQL_DELETE_COURSE = "DELETE FROM training_schema.courses WHERE course_id = (?)";

    /** SQL query for getting course. */
    private final static String SQL_GET_COURSE = "SELECT course_id, name, teacher_id " +
            "FROM training_schema.courses WHERE course_id = (?)";

    /** SQL query for getting courses. */
    private final static String SQL_GET_COURSES = "SELECT course_id, name, teacher_id " +
            "FROM training_schema.courses";

    /** SQL query for getting courses on one page. */
    private final static String SQL_GET_COURSES_PAGE = "SELECT course_id, name, teacher_id " +
            "FROM training_schema.courses ORDER BY course_id OFFSET (?) LIMIT (?)";

    /** SQL query for getting courses for teacher on one page. */
    private final static String SQL_GET_COURSES_BY_TEACHER_PAGE = "SELECT course_id, name, teacher_id " +
            "FROM training_schema.courses WHERE teacher_id = (?) ORDER BY course_id OFFSET (?) LIMIT (?)";

    /** SQL query for getting courses for the teacher. */
    private final static String SQL_GET_COURSES_BY_TEACHER = "SELECT course_id, name, teacher_id " +
            "FROM training_schema.courses WHERE teacher_id = (?) ORDER BY course_id";

    /** SQL query for getting count of courses. */
    private final static String SQL_GET_COUNT = "SELECT COUNT(*) FROM training_schema.courses ";

    /** SQL query for getting count of courses for the teacher. */
    private final static String SQL_GET_COUNT_BY_TEACHER = "SELECT COUNT(*) FROM training_schema.courses " +
            "WHERE teacher_id = (?)";

    /** SQL query for getting not chosen by the student courses. */
    private final static String SQL_GET_UNCHOSEN_COURSES = "SELECT course_id, name, teacher_id " +
            "FROM training_schema.courses " +
            "WHERE course_id NOT IN (SELECT course_registration.course_id " +
            "FROM training_schema.courses INNER JOIN training_schema.course_registration " +
            "ON courses.course_id = course_registration.course_id WHERE student_id = (?)) " +
            "ORDER BY course_id OFFSET (?) LIMIT (?)";

    /** SQL query for getting chosen by the student courses. */
    private final static String SQL_GET_CHOSEN_COURSES = "SELECT courses.course_id, name, teacher_id " +
            "FROM training_schema.courses LEFT JOIN training_schema.course_registration " +
            "ON courses.course_id = course_registration.course_id WHERE student_id = (?)" +
            "ORDER BY course_id OFFSET (?) LIMIT (?)";

    /** SQL query for counting not chosen by the student courses. */
    private final static String SQL_GET_COUNT_UNCHOSEN_COURSES = "SELECT COUNT(*) " +
            "FROM training_schema.courses " +
            "WHERE course_id NOT IN (SELECT course_registration.course_id " +
            "FROM training_schema.courses INNER JOIN training_schema.course_registration " +
            "ON courses.course_id = course_registration.course_id WHERE student_id = (?))";

    /** SQL query for counting chosen by the student courses. */
    private final static String SQL_GET_COUNT_CHOSEN_COURSES = "SELECT COUNT(*) " +
            "FROM training_schema.courses LEFT JOIN training_schema.course_registration " +
            "ON courses.course_id = course_registration.course_id WHERE student_id = (?)";

    /** Field for logging. */
    private final static Logger LOGGER = LogManager.getLogger(RoleDAO.class);

    /**
     * Rows to show in table on one page.
     */
    public final static int ROWS_PER_PAGE = 3;

    /**
     * Constructor with no parameters.
     */
    public CourseDAO(){
        setConnection(ConnectionPoolImpl.getInstance().getConnection());
    }

    /**
     * Constructor with connection.
     *
     * @param connection the connection
     */
    public CourseDAO(Connection$Proxy connection){
        setConnection(connection);
    }

    @Override
    public boolean create(Course course) throws UserNotFoundDAOException {
        LOGGER.log(Level.INFO, "Creating course column...");
        AbstractDAO userDAO = new UserDAO(connection);
        if (userDAO.getEntityById(course.getTeacher().getId()).isEmpty()) {
            throw new UserNotFoundDAOException("Cannot create course. User not found");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_COURSE)) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setInt(2, course.getTeacher().getId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during course creating");
            throw new UncheckedDAOException("Exception during course creating", ex);
        }
        return false;
    }

    @Override
    public boolean delete(Course course) {
        LOGGER.log(Level.INFO, "Deleting course column...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE)) {
            preparedStatement.setInt(1, course.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during course deleting");
            throw new UncheckedDAOException("Exception during course deleting", ex);
        }
        return false;
    }

    @Override
    public Optional<Course> update(Course course) {
        LOGGER.log(Level.INFO, "Updating course column...");
        Optional<Course> optionalCourse = getEntityById(course.getId());
        if (optionalCourse.isPresent()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE)) {
                preparedStatement.setString(1, course.getName());
                preparedStatement.setInt(2, course.getTeacher().getId());
                preparedStatement.setInt(3, course.getId());
                if (preparedStatement.executeUpdate() > 0) {
                    return optionalCourse;
                }
            }catch (SQLException ex) {
                LOGGER.log(Level.FATAL, "Exception during course updating");
                throw new UncheckedDAOException("Exception during course updating", ex);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Course> getEntityById(int id) {
        LOGGER.log(Level.INFO, "Selecting course column by id...");
        String name = null;
        int course_id = 0;
        int teacher_id = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_COURSE)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            }
            while (resultSet.next()) {
                course_id = resultSet.getInt("course_id");
                name = resultSet.getString("name");
                teacher_id = resultSet.getInt("teacher_id");
            }
            AbstractDAO userDAO = new UserDAO(connection);
            User teacher = (User) userDAO.getEntityById(teacher_id).get();
            return Optional.of(new Course(course_id, name, teacher));
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during course reading");
            throw new UncheckedDAOException("Exception during course reading", ex);
        }
    }

    /**
     * Gets list of courses for teacher depending on page number. Returns ArrayList of courses.
     *
     * @param teacher_id the teacher id
     * @param page       the page number
     * @return ArrayList of courses for teacher
     * @throws UncheckedDAOException if SQLException was thrown
     */
    public List<Course> getByTeacher(int teacher_id, int page) {
        LOGGER.log(Level.INFO, "Selecting courses by teacher on page...");
        List<Course> courses = new ArrayList<>();
        String name;
        int course_id;
        String query;
        if (page == 0) {
            query = SQL_GET_COURSES_BY_TEACHER;
        } else {
            query = SQL_GET_COURSES_BY_TEACHER_PAGE;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setValues(preparedStatement, teacher_id, page);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            AbstractDAO userDAO = new UserDAO(connection);
            User teacher = (User) userDAO.getEntityById(teacher_id).get();
            while (resultSet.next()) {
                course_id = resultSet.getInt("course_id");
                name = resultSet.getString("name");
                courses.add(new Course(course_id, name, teacher));
            }
            return courses;
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during course reading by teacher on page");
            throw new UncheckedDAOException("Exception during course reading by teacher on page", ex);
        }
    }

    /**
     * Gets count of courses.
     *
     * @return the count of courses
     * @throws UncheckedDAOException if SQLException was thrown
     */
    public int getCount() {
        LOGGER.log(Level.INFO, "Counting courses...");
        try {
            return getCountUsingQuery(0, SQL_GET_COUNT );
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during course counting");
            throw new UncheckedDAOException("Exception during course counting", ex);
        }
    }

    /**
     * Gets count of courses for teacher.
     *
     * @param teacher_id the teacher id
     * @return the count of courses for teacher
     * @throws UncheckedDAOException if SQLException was thrown
     */
    public int getCountByTeacher(int teacher_id) {
        LOGGER.log(Level.INFO, "Counting courses by teacher...");
        try {
            return getCountUsingQuery(teacher_id, SQL_GET_COUNT_BY_TEACHER );
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during course counting by teacher");
            throw new UncheckedDAOException("Exception during course counting by teacher", ex);
        }
    }

    /**
     * Gets count of courses for student.
     *
     * @param student_id the student id
     * @param chosen     {@code true} if chosen by student, {@code false} if not chosen by student
     * @return the count of courses for student
     * @throws UncheckedDAOException if SQLException was thrown
     */
    public int getCountByStudent(int student_id, boolean chosen) {
        LOGGER.log(Level.INFO, "Counting chosen courses by student...");
        String query;
        if (chosen) {
            query = SQL_GET_COUNT_CHOSEN_COURSES;
        }else {
            query = SQL_GET_COUNT_UNCHOSEN_COURSES;
        }
        try {
            return getCountUsingQuery(student_id, query);
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during chosen course counting");
            throw new UncheckedDAOException("Exception during chosen course counting", ex);
        }
    }

    /**
     * Gets list of courses for student depending on page number. Returns ArrayList of courses.
     *
     * @param student_id the student id
     * @param chosen     {@code true} if chosen by student, {@code false} if not chosen by student
     * @param page       the page number
     * @return ArrayList of courses for student
     * @throws UncheckedDAOException if SQLException was thrown
     */
    public List<Course> getCoursesByStudent(int student_id, boolean chosen, int page) {
        LOGGER.log(Level.INFO, "Selecting courses for student...");
        List<Course> courses = new ArrayList<>();
        String name, query;
        int course_id, teacher_id;
        if (chosen) {
            query = SQL_GET_CHOSEN_COURSES;
        }else {
            query = SQL_GET_UNCHOSEN_COURSES;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setValues(preparedStatement, student_id, page);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            AbstractDAO userDAO = new UserDAO(connection);
            while (resultSet.next()) {
                course_id = resultSet.getInt("course_id");
                name = resultSet.getString("name");
                teacher_id = resultSet.getInt("teacher_id");
                Optional<User> optionalUser = userDAO.getEntityById(teacher_id);
                User teacher;
                teacher = optionalUser.orElse(null);
                courses.add(new Course(course_id, name, teacher));
            }
            return courses;
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during reading courses for student");
            throw new UncheckedDAOException("Exception during reading courses for student", ex);
        }
    }

    @Override
    public List<Course> getAll() {
        LOGGER.log(Level.INFO, "Selecting all courses...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_COURSES)) {
            ResultSet  resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            return getFromResult(resultSet);
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during getting courses");
            throw new UncheckedDAOException("Exception during getting courses", ex);
        }
    }

    /**
     * Gets all courses depending on page number. Returns ArrayList of courses.
     *
     * @param page the page number
     * @return ArrayList of courses
     */
    public List<Course> getAll(int page) {
        LOGGER.log(Level.INFO, "Selecting courses on page...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_COURSES_PAGE)) {
            preparedStatement.setInt(1, (page - 1) * ROWS_PER_PAGE);
            preparedStatement.setInt(2, ROWS_PER_PAGE);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            return getFromResult(resultSet);
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during course reading by teacher on page");
            throw new UncheckedDAOException("Exception during course reading by teacher on page", ex);
        }
    }

    /**
     * Gets count of courses for user depending on type of query.
     *
     * @param id the user id
     * @param query type of query
     * @return count of courses
     * @throws SQLException if SQLException is thrown in method
     */
    private int getCountUsingQuery(int id, String query) throws SQLException{
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (id != 0) {
                preparedStatement.setInt(1, id);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return 0;
            }else {
                resultSet.next();
                return resultSet.getInt("count");
            }
        }
    }

    @Override
    public List<Course> getFromResult(ResultSet resultSet) throws SQLException {
        UserDAO userDAO = new UserDAO(connection);
        List<Course> courses = new ArrayList<>();
        int course_id, teacher_id;
        String name;
        Course course;
        User user;
        while (resultSet.next()) {
            course_id = resultSet.getInt("course_id");
            name = resultSet.getString("name");
            teacher_id = resultSet.getInt("teacher_id");
            Optional<User> optionalUser =  userDAO.getEntityById(teacher_id);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            } else break;
            course = new Course(course_id, name, user);
            courses.add(course);
        }
        return courses;
    }

    /**
     * Sets values in {@link PreparedStatement} object
     *
     * @param preparedStatement the {@link PreparedStatement} object
     * @param id the user id
     * @param page the page number
     * @throws SQLException if SQLException is thrown in method
     */
    private void setValues(PreparedStatement preparedStatement, int id, int page) throws SQLException {
        preparedStatement.setInt(1, id);
        if (page != 0) {
            preparedStatement.setInt(2, (page - 1) * ROWS_PER_PAGE);
            preparedStatement.setInt(3, ROWS_PER_PAGE);
        }
    }
}
