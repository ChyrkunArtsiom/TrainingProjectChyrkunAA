package by.javatr.training;

import by.javatr.training.dao.impl.ConnectionPoolImplDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try{
            ConnectionPoolImplDAO cpool = ConnectionPoolImplDAO.create();
            final Connection connection = cpool.getConnection();
            System.out.println("test");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM training_db.training_schema.roles");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                System.out.println("id " + resultSet.getInt(1));
                System.out.println("name " + resultSet.getString(2));
            }
            preparedStatement.close();
            cpool.releaseConnection(connection);
        }catch (SQLException ex){
            System.out.println(ex.toString());
        }
    }
}
