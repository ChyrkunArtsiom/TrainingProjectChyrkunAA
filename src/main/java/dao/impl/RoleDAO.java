package dao.impl;

import dao.AbstractDAO;
import dao.db.ConnectionPoolDAO;
import dao.db.impl.Connection$Proxy;
import dao.db.impl.ConnectionPoolImpl;
import model.Role;

import java.sql.*;
import java.util.Enumeration;

public class RoleDAO extends AbstractDAO<Role> {
    private ConnectionPoolDAO pool;
    private Connection$Proxy connection;
    private final static String SQL_COUNT = "SELECT COUNT(role_id) FROM training_schema.roles";
    private final static String SQL_CREATE_ROLE = "INSERT INTO training_schema.roles (name) VALUES (?)";

    public RoleDAO() {
        pool = ConnectionPoolImpl.getInstance();
        connection = pool.getConnection();
    }

    @Override
    public boolean create(Role entity) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_ROLE)){
            preparedStatement.setString(1, entity.getName());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException ex){
            System.out.println(ex);     //need to handle
        }finally {
            try{
                close();
                Enumeration<Driver> drivers = DriverManager.getDrivers();
                while (drivers.hasMoreElements()){
                    Driver driver = drivers.nextElement();
                    DriverManager.deregisterDriver(driver);
                }
            }catch (SQLException e){
                System.out.println(e);  //need to handle
            }
        }
        return false;
    }

    @Override
    public boolean close() throws SQLException{
        return pool.releaseConnection(connection);
    }
}
