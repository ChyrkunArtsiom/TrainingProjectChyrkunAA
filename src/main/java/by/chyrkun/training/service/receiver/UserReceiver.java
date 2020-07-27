package by.chyrkun.training.service.receiver;

import by.chyrkun.training.dao.exception.EntityNotFoundDAOException;
import by.chyrkun.training.dao.impl.UserDAO;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.exception.RoleNotFoundServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The class-receiver which calls methods of {@link User} objects.
 */
public class UserReceiver {
    /**
     * Creates an user. Returns whether it was successful.
     *
     * @param user the {@link User} object to create
     * @return whether it was successful
     * @throws RoleNotFoundServiceException if role was not found
     */
    public boolean create(User user) throws RoleNotFoundServiceException {
        UserDAO userDAO = new UserDAO();
        try {
            return userDAO.create(user);
        }catch (EntityNotFoundDAOException ex) {
            throw new RoleNotFoundServiceException(ex.getMessage(), ex);
        }
        finally {
            userDAO.close();
        }
    }

    /**
     * Updates user. Takes user to update. Returns old user if successful and null if failed.
     *
     * @param user the user to update
     * @return the old user if successful and null if failed
     */
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

    /**
     * Deletes an user. Takes login. Returns whether it was successful.
     *
     * @param login the login
     * @return whether it was successful
     */
    public boolean delete(String login) {
        UserDAO userDAO = new UserDAO();
        try {
            Optional<User> user = userDAO.getUserByLogin(login);
            return user.map(userDAO::delete).orElse(false);
        }finally {
            userDAO.close();
        }
    }
    /**
     * Gets an user by id.
     *
     * @param id the user id
     * @return the Optional of {@link User}
     */
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

    /**
     * Gets an user by login.
     *
     * @param login the login
     * @return the Optional of {@link User}
     */
    public User getByLogin(String login) {
        Optional<User> user;
        UserDAO userDAO = new UserDAO();
        try {
            user = userDAO.getUserByLogin(login);
            return user.orElse(null);
        }finally {
            userDAO.close();
        }
    }

    /**
     * Gets list of users with role 'teacher'.
     *
     * @return the list of users
     */
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
