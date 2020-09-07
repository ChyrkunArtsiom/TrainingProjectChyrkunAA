package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.RoleNotFoundDAOException;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import by.chyrkun.training.model.Role;
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
 * Class for interacting with {@link User} table in database.
 * Implements {@link ResultMapper}.
 */
public class UserDAO extends AbstractDAO<User> implements ResultMapper<List<User>> {
    /** SQL query for creating user using all columns. */
    private final static String SQL_CREATE_USER_FULL =
            "INSERT INTO training_schema.users (user_id, login, password, firstname, secondname, role_id) VALUES (?,?,?,?,?,?)";

    /** SQL query for creating user. */
    private final static String SQL_CREATE_USER =
            "INSERT INTO training_schema.users (login, password, firstname, secondname, role_id) VALUES (?,?,?,?,?)";

    /** SQL query for updating user. */
    private final static String SQL_UPDATE_USER = "UPDATE training_schema.users SET " +
            "login = (?), password = (?), firstname = (?), secondname = (?), role_id = (?) WHERE user_id = (?)";

    /** SQL query for updating user without password. */
    private final static String SQL_UPDATE_USER_WITHOUT_PASSWORD = "UPDATE training_schema.users SET " +
            "login = (?), firstname = (?), secondname = (?), role_id = (?)  WHERE user_id = (?)";

    /** SQL query for deleting user. */
    private final static String SQL_DELETE_USER = "DELETE FROM training_schema.users WHERE user_id = (?)";

    /** SQL query for getting user by id. */
    private final static String SQL_GET_USER_BY_ID = "SELECT user_id, login, password, firstname, secondname, role_id " +
            "FROM training_schema.users WHERE user_id = (?)";

    /** SQL query for getting user by login. */
    private final static String SQL_GET_USER_BY_LOGIN = "SELECT user_id, login, password, password, firstname, secondname, role_id " +
            "FROM training_schema.users WHERE login = (?)";

    /** SQL query for getting teachers. */
    private final static String SQL_GET_ALL_TEACHERS = "SELECT user_id, firstname, secondname" +
            " FROM training_schema.users " +
            "INNER JOIN training_schema.roles ON users.role_id = roles.role_id WHERE name = 'teacher'";

    /** SQL query for getting users. */
    private final static String SQL_GET_USERS = "SELECT user_id, firstname, secondname" +
            " FROM training_schema.users ";

    /** Field for logging. */
    private final static Logger LOGGER = LogManager.getLogger(RoleDAO.class);

    /**
     * Constructor with no parameters.
     */
    public UserDAO() {
        setConnection(ConnectionPoolImpl.getInstance().getConnection());
    }

    /**
     * Constructor with connection.
     *
     * @param connection the connection
     */
    public UserDAO(Connection$Proxy connection){
        setConnection(connection);
    }

    @Override
    public boolean create(User user) throws RoleNotFoundDAOException {
        LOGGER.log(Level.INFO, "Creating user column...");
        if (getUserByLogin(user.getLogin()).isPresent()) {
            return false;
        }
        AbstractDAO roleDAO = new RoleDAO(connection);
        if (roleDAO.getEntityById(user.getRole().getId()).isEmpty()) {
            throw new RoleNotFoundDAOException("Cannot create user. Role not found");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setBytes(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getSecondname());
            preparedStatement.setInt(5, user.getRole().getId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during user creating");
            throw new UncheckedDAOException("Exception during user creating", ex);
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        LOGGER.log(Level.INFO, "Deleting user column...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            preparedStatement.setInt(1, user.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during user deleting");
            throw new UncheckedDAOException("Exception during user deleting", ex);
        }
        return false;
    }

    @Override
    public Optional<User> update(User user) {
        LOGGER.log(Level.INFO, "Updating user column...");
        Optional<User> optionalUser = getEntityById(user.getId());
        String query;
        if (user.getPassword() == null) {
            query = SQL_UPDATE_USER_WITHOUT_PASSWORD;
        }else {
            query = SQL_UPDATE_USER;
        }
        if (optionalUser.isPresent()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                if (user.getPassword() == null) {
                    preparedStatement.setString(1, user.getLogin());
                    preparedStatement.setString(2, user.getFirstname());
                    preparedStatement.setString(3, user.getSecondname());
                    preparedStatement.setInt(4, user.getRole().getId());
                    preparedStatement.setInt(5, user.getId());
                }else {
                    preparedStatement.setString(1, user.getLogin());
                    preparedStatement.setBytes(2, user.getPassword());
                    preparedStatement.setString(3, user.getFirstname());
                    preparedStatement.setString(4, user.getSecondname());
                    preparedStatement.setInt(5, user.getRole().getId());
                    preparedStatement.setInt(6, user.getId());
                }

                if (preparedStatement.executeUpdate() > 0) {
                    return optionalUser;
                }
            }catch (SQLException ex) {
                LOGGER.log(Level.FATAL, "Exception during user updating");
                throw new UncheckedDAOException("Exception during user updating", ex);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getEntityById(int id) {
        LOGGER.log(Level.INFO, "Selecting user column by id...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            }
            List<User> users = getList(resultSet, false);
            return Optional.of(users.get(0));
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during user reading");
            throw new UncheckedDAOException("Exception during user reading", ex);
        }
    }

    @Override
    public List<User> getAll() {
        LOGGER.log(Level.INFO, "Selecting all users...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USERS)) {
            ResultSet  resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            return getFromResult(resultSet);
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during getting users");
            throw new UncheckedDAOException("Exception during getting users", ex);
        }
    }

    /**
     * Gets user by login. Returns Optional of {@link User}.
     *
     * @param login the login of user
     * @return Optional of {@link User}
     * @throws UncheckedDAOException if SQLException was thrown
     */
    public Optional<User> getUserByLogin(String login) {
        LOGGER.log(Level.INFO, "Selecting user column by name...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            }
            List<User> users = getList(resultSet, true);
            return Optional.of(users.get(0));
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during user reading");
            throw new UncheckedDAOException("Exception during user reading", ex);
        }
    }

    /**
     * Gets teachers. Returns list of teachers.
     *
     * @return list of teachers
     * @throws UncheckedDAOException if SQLException was thrown
     */
    public List<User> getTeachers() {
        LOGGER.log(Level.INFO, "Selecting all teachers...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_TEACHERS)) {
            ResultSet  resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            String firstname;
            String secondname;
            int user_id;
            List<User> teachers = new ArrayList<>();
            while (resultSet.next()) {
                user_id = resultSet.getInt("user_id");
                firstname = resultSet.getString("firstname");
                secondname = resultSet.getString("secondname");
                teachers.add(new User(user_id, firstname, secondname));
            }
            return teachers;
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during getting teachers");
            throw new UncheckedDAOException("Exception during getting teachers", ex);
        }
    }

    /**
     * Gets list of users from {@link ResultSet} object with or without password, depending on parameter.
     * Returns list of users.
     *
     * @param resultSet the {@link ResultSet} object of executed SQL query
     * @param withPassword if {@code true} user will contain password, if {@code false} user will not contain password
     * @return list of users
     * @throws SQLException if SQLException is thrown in method
     */
    private List<User> getList(ResultSet resultSet, boolean withPassword) throws SQLException {
        List<User> users = new ArrayList<>();
        String login;
        byte[] password = new byte[16];
        String firstname;
        String secondname;
        int user_id;
        int role_id;
        User user;
        while (resultSet.next()) {
            user_id = resultSet.getInt("user_id");
            login = resultSet.getString("login");
            if (withPassword) {
                password = resultSet.getBytes("password");
            }
            firstname = resultSet.getString("firstname");
            secondname = resultSet.getString("secondname");
            role_id = resultSet.getInt("role_id");
            AbstractDAO roleDAO = new RoleDAO(connection);
            Role role = (Role) roleDAO.getEntityById(role_id).get();
            user = new User(user_id, login, password, firstname, secondname, role);
            users.add(user);
        }
        return users;
    }

    @Override
    public List<User> getFromResult(ResultSet resultSet) throws SQLException {
        RoleDAO roleDAO = new RoleDAO(connection);
        List<User> users = new ArrayList<>();
        String login, firstname, secondname;
        byte[] password;
        int role_id, user_id;
        Role role;
        User user;
        while (resultSet.next()) {
            user_id = resultSet.getInt("user_id");
            login = resultSet.getString("login");
            password = resultSet.getBytes("password");
            firstname = resultSet.getString("firstname");
            secondname = resultSet.getString("secondname");
            role_id = resultSet.getInt("role_id");
            role = roleDAO.getEntityById(role_id).get();
            user = new User(user_id, login, password, firstname, secondname, role);
            users.add(user);
        }
        return users;
    }
}
