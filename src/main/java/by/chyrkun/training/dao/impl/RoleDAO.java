package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.Connection$Proxy;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import by.chyrkun.training.model.Role;
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
 * Class for interacting with {@link Role} table in database.
 * Implements {@link ResultMapper}.
 */
public class RoleDAO extends AbstractDAO<Role> implements ResultMapper<List<Role>> {
    /** SQL query for creating role using id and name. */
    private final static String SQL_CREATE_ROLE_FULL = "INSERT INTO training_schema.roles (role_id, name) VALUES (?,?)";

    /** SQL query for creating role using name. */
    private final static String SQL_CREATE_ROLE = "INSERT INTO training_schema.roles (name) VALUES (?)";

    /** SQL query for updating role. */
    private final static String SQL_UPDATE_ROLE = "UPDATE training_schema.roles SET name = (?) WHERE role_id = (?)";

    /** SQL query for deleting role. */
    private final static String SQL_DELETE_ROLE = "DELETE FROM training_schema.roles WHERE role_id = (?)";

    /** SQL query for getting role by id. */
    private final static String SQL_GET_ROLE_BY_ID = "SELECT role_id, name FROM training_schema.roles WHERE role_id = (?)";

    /** SQL query for getting role by name. */
    private final static String SQL_GET_ROLE_BY_NAME = "SELECT role_id, name FROM training_schema.roles WHERE name = (?)";

    /** SQL query for getting roles. */
    private final static String SQL_GET_ROLES = "SELECT role_id, name FROM training_schema.roles";

    /** Field for logging. */
    private final static Logger LOGGER = LogManager.getLogger(RoleDAO.class);

    /**
     * Constructor with no parameters.
     */
    public RoleDAO(){
        setConnection(ConnectionPoolImpl.getInstance().getConnection());
    }

    /**
     * Constructor with connection.
     *
     * @param connection the connection
     */
    public RoleDAO(Connection$Proxy connection){
        setConnection(connection);
    }

    @Override
    public boolean create(Role role) {
        LOGGER.log(Level.INFO, "Creating role column...");
        String query;
        if (role.getId() != 0) {
            query = SQL_CREATE_ROLE_FULL;
        }else {
            query = SQL_CREATE_ROLE;
        }
        if (getRoleByName(role.getName()).isPresent()){
            return false;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (role.getId() != 0) {
                preparedStatement.setInt(1, role.getId());
                preparedStatement.setString(2, role.getName());
            }else {
                preparedStatement.setString(1, role.getName());
            }
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during role creating");
            throw new UncheckedDAOException("Exception during role creating", ex);
        }
        return false;
    }

    @Override
    public boolean delete(Role role){
        LOGGER.log(Level.INFO, "Deleting role column...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ROLE)) {
            preparedStatement.setInt(1, role.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during role deleting");
            throw new UncheckedDAOException("Exception during role deleting", ex);
        }
        return false;
    }

    @Override
    public Optional<Role> update(Role role) {
        LOGGER.log(Level.INFO, "Updating role column...");
        Optional<Role> optional = getEntityById(role.getId());
        if (optional.isPresent()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ROLE)) {
                preparedStatement.setString(1, role.getName());
                preparedStatement.setInt(2, role.getId());
                if (preparedStatement.executeUpdate() > 0) {
                    return optional;
                }
            }catch (SQLException ex) {
                LOGGER.log(Level.FATAL, "Exception during role updating");
                throw new UncheckedDAOException("Exception during role updating", ex);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Role> getEntityById(int id) {
        LOGGER.log(Level.INFO, "Selecting role column by id...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ROLE_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet  resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            } else {
                List<Role> roles = getFromResult(resultSet);
                return Optional.of(roles.get(0));
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during role reading");
            throw new UncheckedDAOException("Exception during role reading", ex);
        }
    }

    /**
     * Gets role by name. Returns Optional of {@link Role}.
     *
     * @param name the name of role
     * @return Optional of {@link Role}
     * @throws UncheckedDAOException if SQLException was thrown
     */
    public Optional<Role> getRoleByName(String name) {
        LOGGER.log(Level.INFO, "Selecting role column by fields...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ROLE_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet  resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            }else {
                List<Role> roles = getFromResult(resultSet);
                return Optional.of(roles.get(0));
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during role reading");
            throw new UncheckedDAOException("Exception during role reading", ex);
        }
    }

    @Override
    public List<Role> getAll() {
        LOGGER.log(Level.INFO, "Selecting all roles...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ROLES)) {
            ResultSet  resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }else {
                return getFromResult(resultSet);
            }
        }catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "Exception during getting roles");
            throw new UncheckedDAOException("Exception during getting roles", ex);
        }
    }

    @Override
    public List<Role> getFromResult(ResultSet resultSet) throws SQLException {
        List<Role> roles = new ArrayList<>();
        String name;
        int role_id;
        Role role;
        while (resultSet.next()) {
            role_id = resultSet.getInt("role_id");
            name = resultSet.getString("name");
            role = new Role(role_id, name);
            roles.add(role);
        }
        return roles;
    }
}
