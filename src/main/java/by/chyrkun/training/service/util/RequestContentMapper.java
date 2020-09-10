package by.chyrkun.training.service.util;

import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.User;

/**
 * Class for getting different values from RequestContent and creating {@link by.chyrkun.training.model.Entity} objects
 * based on that.
 */
public class RequestContentMapper {
    /**
     * Gets user fields from RequestContent. Returns a {@link User} object.
     *
     * @param requestContent the RequestContent object
     * @return the {@link User} object
     */
    public static User getUserFromRequest(RequestContent requestContent) {
        String login = requestContent.getRequestParameters().get("login")[0];
        String password = requestContent.getRequestParameters().get("password")[0];
        String firstname = requestContent.getRequestParameters().get("firstname")[0];
        String secondname = requestContent.getRequestParameters().get("secondname")[0];
        byte[] hash = PasswordHasher.getHash(password);
        return new User(login, hash, firstname, secondname);
    }

    /**
     * Sets user fields in RequestContent.
     *
     * @param user           the user
     * @param requestContent the RequestContent object
     */
    public static void setUserInRequest(User user, RequestContent requestContent) {
        requestContent.setSessionAttribute("user_id", user.getId());
        requestContent.setSessionAttribute("userName", user.getLogin());
        requestContent.setSessionAttribute("role", user.getRole().getName());
        requestContent.setSessionAttribute("role_id", user.getRole().getId());
    }
}
