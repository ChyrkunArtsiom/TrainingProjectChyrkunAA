package by.chyrkun.training.service.validator;

import by.chyrkun.training.model.User;

public class UserValidator {

    public static boolean isUserValid(User user) {
        return ((isLoginValid(user.getLogin())) && (isNameValid(user.getFirstname()))
                && (isNameValid(user.getSecondname())) && (isPasswordValid(user.getPassword())));
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
}
