package by.chyrkun.training.service.validator;

import by.chyrkun.training.model.User;
import by.chyrkun.training.service.resource.MessageManager;

/**
 * Class for validating {@link User} objects.
 */
public class UserValidator {

    /**
     * Validates {@link User} object.
     *
     * @param user    the {@link User} object of user
     * @param message the StringBuffer of message
     * @param manager the {@link MessageManager} object to get message
     * @return the boolean
     */
    public static boolean isUserValid(User user, StringBuffer message, MessageManager manager) {
        if (!isLoginValid(user.getLogin())) {
            message.append(manager.getMessage("usernameIsNotValid"));
            return false;
        }
        if (!isNameValid(user.getFirstname()) || !isNameValid(user.getSecondname())) {
            message.append(manager.getMessage("nameIsNotValid"));
            return false;
        }
        return true;
    }

    /**
     * Validates {@link User} object's name.
     *
     * @param login the string of user name
     * @return {@code true} if name matches requirements
     */
    public static boolean isLoginValid(String login){
        return (login.matches("^[a-zA-Z0-9]{5,45}$"));
    }

    /**
     * Validates {@link User} object's login.
     *
     * @param password the string of password
     * @return {@code true} if login matches requirements
     */
    public static boolean isPasswordValid(String password) {
        return password.matches("^[a-zA-Z\\d_]{8,15}$");
    }

    /**
     * Validates {@link User} object's name.
     *
     * @param name the string of user name
     * @return {@code true} if name matches requirements
     */
    public static boolean isNameValid(String name) {
        return  ((name == null) || (name.matches("^[а-яёА-Яa-zA-Z]{1,45}$")));
    }

    /**
     * Checks if {@link User} object is teacher
     *
     * @param user the {@link User} object of user
     * @return {@code true} if user is teacher
     */
    public static boolean isTeacher(User user) {
        return user.getRole().getName().equals("teacher");
    }

    /**
     * Checks if {@link User} object is student
     *
     * @param user the {@link User} object of user
     * @return {@code true} if user is student
     */
    public static boolean isStudent(User user) {
        return user.getRole().getName().equals("student");
    }
}
