package by.chyrkun.training;

import by.chyrkun.training.dao.AbstractDAO;
import by.chyrkun.training.dao.db.impl.ConnectionPoolImpl;
import by.chyrkun.training.dao.impl.RoleDAO;
import by.chyrkun.training.dao.impl.UserDAO;
import by.chyrkun.training.model.Entity;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;

public class Main {

    public static void main(String[] args) {
        AbstractDAO roleDAO = new RoleDAO();
        AbstractDAO userDAO = new UserDAO();
        Role role = (Role) roleDAO.getEntityById(1).get();
        Entity user = new User("test_user", "pass1234", "artsiom", "chyrkun", role);
        User user_E = (User) userDAO.getEntityById(1).get();
        System.out.println(user_E.toString());

        ConnectionPoolImpl.getInstance().shutdown();
    }
}
