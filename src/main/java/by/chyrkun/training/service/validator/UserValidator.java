package by.chyrkun.training.service.validator;

import by.chyrkun.training.model.User;
import by.chyrkun.training.service.resource.MessageManager;

public class UserValidator {

    public static boolean isUserValid(User user, StringBuffer message, MessageManager manager) {
        if (!isLoginValid(user.getLogin())) {
            message.append(manager.getMessage("usernameIsNotValid"));
            return false;
        }
        if (!isNameValid(user.getFirstname()) || !isNameValid(user.getSecondname())) {
            message.append(manager.getMessage("nameIsNotValid"));
            return false;
        }
        if (!isPasswordValid(user.getPassword())) {
            message.append(manager.getMessage("passwordIsNotValid"));
            return false;
        }
        return true;
    }

    public static boolean isLoginValid(String login){
        return (login.matches("^[a-zA-Z0-9]{5,45}$"));
    }

    public static boolean isPasswordValid(String password) {
        return password.matches("^[a-zA-Z\\d_]{8,15}$");
    }

    public static boolean isNameValid(String name) {
        return  ((name == null) || (name.matches("^[а-яёА-Яa-zA-Z]{1,45}$")));
    }

    public static boolean isTecaher(User user) {
        return user.getRole().getName().equals("teacher");
    }

    public static boolean isStudent(User user) {
        return user.getRole().getName().equals("student");
    }
}
