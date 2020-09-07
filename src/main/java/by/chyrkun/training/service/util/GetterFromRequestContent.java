package by.chyrkun.training.service.util;

import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.User;

/**
 * Class for getting different values from RequestContent and creating {@link by.chyrkun.training.model.Entity} objects
 * based on that.
 */
public class GetterFromRequestContent {
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
}
