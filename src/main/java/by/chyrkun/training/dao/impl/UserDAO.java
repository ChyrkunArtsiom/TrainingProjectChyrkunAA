package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDAO extends AbstractDAO<User> {
    private final static String SQL_CREATE_USER =
            "INSERT INTO training_schema.users (login, password, firstname, secondname, role_id) VALUES (?,?,?,?,?)";
    private final static String SQL_UPDATE_USER = "UPDATE training_schema.users SET " +
            "login = (?), password = (?), firstname = (?), secondname = (?), role_id = (?)  WHERE user_id = (?)";
    private final static String SQL_DELETE_USER = "DELETE FROM training_schema.users WHERE user_id = (?)";
    private final static String SQL_GET_USER = "SELECT * FROM training_schema.users WHERE user_id = (?)";
    private final static Logger LOGGER = LogManager.getLogger(RoleDAO.class);

    public UserDAO() {
        setConnection(ConnectionPoolImpl.getInstance().getConnection());
    }

    public UserDAO(Connection$Proxy connection){
        setConnection(connection);
    }

    @Override
    public boolean create(User user) {
        LOGGER.log(Level.INFO, "Creating user column...");
        AbstractDAO roleDAO = new RoleDAO(connection);
        if (!roleDAO.getEntityById(user.getRole().getId()).isPresent())
            return false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getSecondname());
            preparedStatement.setInt(5, user.getRole().getId());
            if (preparedStatement.executeUpdate() > 0)
                return true;
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
            if (preparedStatement.executeUpdate() > 0)
                return true;
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during user deleting");
            throw new UncheckedDAOException("Exception during user deleting", ex);
        }
        return false;
    }

    @Override
    public Optional<User> update(User user){
        LOGGER.log(Level.INFO, "Updating user column...");
        Optional<User> optionalUser = getEntityById(user.getId());
        if (optionalUser.isPresent()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getFirstname());
                preparedStatement.setString(4, user.getSecondname());
                preparedStatement.setInt(5, user.getRole().getId());
                preparedStatement.setInt(6, user.getId());
                if (preparedStatement.executeUpdate() > 0)
                    return optionalUser;
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
        String login = null;
        String password = null;
        String firstname = null;
        String secondname = null;
        int user_id = 0;
        int role_id = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USER)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user_id = resultSet.getInt("user_id");
                login = resultSet.getString("login");
                password = resultSet.getString("password");
                firstname = resultSet.getString("firstname");
                secondname = resultSet.getString("secondname");
                role_id = resultSet.getInt("role_id");
            }
            AbstractDAO roleDAO = new RoleDAO(connection);
            Role role = (Role) roleDAO.getEntityById(role_id).get();
            return Optional.of(new User(user_id, login, password, firstname, secondname, role));
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during user reading");
            throw new UncheckedDAOException("Exception during user reading", ex);
        }
    }
}
