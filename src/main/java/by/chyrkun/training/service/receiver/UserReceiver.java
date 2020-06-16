package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.model.User;
import by.chyrkun.training.dao.impl.UserDAO;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserReceiver {

    public boolean create(User user) throws EntityNotFoundServiceException {
        UserDAO userDAO = new UserDAO();
        try {
            return userDAO.create(user);
        }catch (EntityNotFoundDAOException ex) {
            throw new EntityNotFoundServiceException(ex.getMessage(), ex);
        }
        finally {
            userDAO.close();
        }
    }

    public User update(User user) {
        Optional<User> optional;
        UserDAO userDAO = new UserDAO();
        try {
            optional = userDAO.update(user);
            return optional.orElse(null);
        }finally {
            userDAO.close();
        }
    }

    public boolean delete(String login) {
        UserDAO userDAO = new UserDAO();
        try {
            Optional<User> user = userDAO.getEntityByLogin(login);
            return user.map(userDAO::delete).orElse(false);
        }finally {
            userDAO.close();
        }
    }

    public User getById(int id){
        Optional<User> user;
        UserDAO userDAO = new UserDAO();
        try {
            user = userDAO.getEntityById(id);
            return user.orElse(null);
        }finally {
            userDAO.close();
        }
    }

    public User getByLogin(String login) {
        Optional<User> user;
        UserDAO userDAO = new UserDAO();
        try {
            user = userDAO.getEntityByLogin(login);
            return user.orElse(null);
        }finally {
            userDAO.close();
        }
    }

    public List<User> getTeachers() {
        List<User> teachers;
        UserDAO userDAO = new UserDAO();
        try {
            teachers = userDAO.getTeachers();
            return teachers;
        }finally {
            userDAO.close();
        }
    }
}
