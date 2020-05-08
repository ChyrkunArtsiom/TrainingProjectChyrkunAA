package by.chyrkun.training.dao.impl;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.exception.UncheckedDAOException;
import by.chyrkun.training.model.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class RoleDAO extends AbstractDAO<Role> {
    private final static String SQL_CREATE_ROLE = "INSERT INTO training_schema.roles (name) VALUES (?)";
    private final static String SQL_UPDATE_ROLE = "UPDATE training_schema.roles SET name = (?) WHERE role_id = (?)";
    private final static String SQL_DELETE_ROLE = "DELETE FROM training_schema.roles WHERE role_id = (?)";
    private final static String SQL_GET_ROLE = "SELECT * FROM training_schema.roles WHERE role_id = (?)";
    private final static Logger LOGGER = LogManager.getLogger(RoleDAO.class);

    public RoleDAO(){
        setConnection(ConnectionPoolImpl.getInstance().getConnection());
    }

    @Override
    public boolean create(Role role) {
        LOGGER.log(Level.INFO, "Creating role column...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_ROLE)) {
            preparedStatement.setString(1, role.getName());
            if (preparedStatement.executeUpdate() > 0)
                return true;
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
            if (preparedStatement.executeUpdate() > 0)
                return true;
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during role deleting");
            throw new UncheckedDAOException("Exception during role deleting", ex);
        }
        return false;
    }

    @Override
    public Optional<Role> update(Role role){
        LOGGER.log(Level.INFO, "Updating role column...");
        Optional<Role> optional = getEntityById(role.getId());
        if (optional.isPresent()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ROLE)) {
                preparedStatement.setString(1, role.getName());
                preparedStatement.setInt(2, role.getId());
                if (preparedStatement.executeUpdate() > 0)
                    return optional;
            }catch (SQLException ex){
                LOGGER.log(Level.FATAL, "Exception during role updating");
                throw new UncheckedDAOException("Exception during role updating", ex);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Role> getEntityById(int id){
        LOGGER.log(Level.INFO, "Selecting role column by id...");
        String name = null;
        int role_id = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ROLE)) {
            preparedStatement.setInt(1, id);
            ResultSet  resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                role_id = resultSet.getInt("role_id");
                name = resultSet.getString("name");
            }
            return Optional.of(new Role(role_id, name));
        }catch (SQLException ex){
            LOGGER.log(Level.FATAL, "Exception during role reading");
            throw new UncheckedDAOException("Exception during role reading", ex);
        }
    }
}
