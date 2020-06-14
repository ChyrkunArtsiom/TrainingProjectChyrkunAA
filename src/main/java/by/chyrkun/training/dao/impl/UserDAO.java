package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractDAO<User> {
    private final static String SQL_CREATE_USER =
            "INSERT INTO training_schema.users (login, password, firstname, secondname, role_id) VALUES (?,?,?,?,?)";
    private final static String SQL_UPDATE_USER = "UPDATE training_schema.users SET " +
            "password = (?), firstname = (?), secondname = (?), role_id = (?)  WHERE login = (?)";
    private final static String SQL_DELETE_USER = "DELETE FROM training_schema.users WHERE user_id = (?)";
    private final static String SQL_GET_USER_BY_ID = "SELECT user_id, login, firstname, secondname, role_id " +
            "FROM training_schema.users WHERE user_id = (?)";
    private final static String SQL_GET_USER_BY_LOGIN = "SELECT user_id, login, password, firstname, secondname, role_id " +
            "FROM training_schema.users WHERE login = (?)";
    private final static String SQL_GET_ALL_TEACHERS = "SELECT user_id, firstname, secondname" +
            " FROM training_schema.users " +
            "INNER JOIN training_schema.roles ON users.role_id = roles.role_id WHERE name = 'teacher'";
    private final static Logger LOGGER = LogManager.getLogger(RoleDAO.class);

    public UserDAO() {
        setConnection(ConnectionPoolImpl.getInstance().getConnection());
    }

    public UserDAO(Connection$Proxy connection){
        setConnection(connection);
    }

    @Override
    public boolean create(User user) throws EntityNotFoundDAOException {
        LOGGER.log(Level.INFO, "Creating user column...");
        if (getEntityByLogin(user.getLogin()).isPresent()){
            return false;
        }
        AbstractDAO roleDAO = new RoleDAO(connection);
        if (roleDAO.getEntityById(user.getRole().getId()).isEmpty()){
            throw new EntityNotFoundDAOException("Cannot create user. Role not found");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getSecondname());
            preparedStatement.setInt(5, user.getRole().getId());
            if (preparedStatement.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during user creating");
            throw new UncheckedDAOException("Exception during user creating", ex);
        }
        return false;
    }

    @Override
    public boolean delete(User user){
        LOGGER.log(Level.INFO, "Deleting user column...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            preparedStatement.setInt(1, user.getId());
            if (preparedStatement.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during user deleting");
            throw new UncheckedDAOException("Exception during user deleting", ex);
        }
        return false;
    }

    @Override
    public Optional<User> update(User user){
        LOGGER.log(Level.INFO, "Updating user column...");
        Optional<User> optionalUser = getEntityByLogin(user.getLogin());
        if (optionalUser.isPresent()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
                preparedStatement.setString(1, user.getPassword());
                preparedStatement.setString(2, user.getFirstname());
                preparedStatement.setString(3, user.getSecondname());
                preparedStatement.setInt(4, user.getRole().getId());
                preparedStatement.setString(5, user.getLogin());
                if (preparedStatement.executeUpdate() > 0){
                    return optionalUser;
                }
            }catch (SQLException ex){
                LOGGER.log(Level.FATAL, "Exception during user updating");
                throw new UncheckedDAOException("Exception during user updating", ex);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getEntityById(int id){
        LOGGER.log(Level.INFO, "Selecting user column by id...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                return Optional.empty();
            }
            List<User> users = getList(resultSet, false);
            return Optional.of(users.get(0));
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during user reading");
            throw new UncheckedDAOException("Exception during user reading", ex);
        }
    }

    public Optional<User> getEntityByLogin(String login){
        LOGGER.log(Level.INFO, "Selecting user column by name...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                return Optional.empty();
            }
            List<User> users = getList(resultSet, true);
            return Optional.of(users.get(0));
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during user reading");
            throw new UncheckedDAOException("Exception during user reading", ex);
        }
    }

    public List<User> getTeachers(){
        LOGGER.log(Level.INFO, "Selecting all teachers...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_TEACHERS)) {
            ResultSet  resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                return null;
            }
            String firstname;
            String secondname;
            int user_id;
            List<User> teachers = new ArrayList<>();
            while (resultSet.next()){
                user_id = resultSet.getInt("user_id");
                firstname = resultSet.getString("firstname");
                secondname = resultSet.getString("secondname");
                teachers.add(new User(user_id, firstname, secondname));
            }
            return teachers;
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during getting teachers");
            throw new UncheckedDAOException("Exception during getting teachers", ex);
        }
    }

    private List<User> getList(ResultSet resultSet, boolean hasPassword) throws SQLException{
        List<User> users = new ArrayList<>();
        String login;
        String password = null;
        String firstname;
        String secondname;
        int user_id;
        int role_id;
        User user;
        while (resultSet.next()){
            user_id = resultSet.getInt("user_id");
            login = resultSet.getString("login");
            if (hasPassword) {
                password = resultSet.getString("password");
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
}
