package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.model.User;
import by.chyrkun.training.dao.impl.UserDAO;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;

import java.util.Optional;

public class UserReceiver {

    public boolean create(User user) throws EntityNotFoundServiceException {
        UserDAO userDAO = new UserDAO();
        try{
            return userDAO.create(user);
        }catch (EntityNotFoundDAOException ex){
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
            if (optional.isPresent()) {
                return optional.get();
            }
            else {
                return null;
            }
        }finally {
            userDAO.close();
        }
    }

    public boolean delete(int id) {
        UserDAO userDAO = new UserDAO();
        try {
            Optional<User> user = userDAO.getEntityById(id);
            if (user.isPresent()) {
                return userDAO.delete(user.get());
            }
            return false;
        }finally {
            userDAO.close();
        }
    }

    public User getById(int id){
        Optional<User> user;
        UserDAO userDAO = new UserDAO();
        try {
            user = userDAO.getEntityById(id);
            if (user.isPresent()) {
                return user.get();
            }
            else {
                return null;
            }
        }finally {
            userDAO.close();
        }
    }

    public User getByLogin(String login) {
        Optional<User> user;
        UserDAO userDAO = new UserDAO();
        try {
            user = userDAO.getEntityByLogin(login);
            if (user.isPresent()) {
                return user.get();
            }
            else {
                return null;
            }
        }finally {
            userDAO.close();
        }
    }
}
