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

        ConnectionPoolImpl.getInstance().shutdown();
    }
}
