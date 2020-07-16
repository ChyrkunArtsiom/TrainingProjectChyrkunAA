package by.chyrkun.training.service.util;

import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.User;

public class GetterFromRequestContent {
    public static User getUserFromRequest(RequestContent requestContent) {
        String login = requestContent.getRequestParameters().get("username")[0];
        String password = requestContent.getRequestParameters().get("password")[0];
        String firstname = requestContent.getRequestParameters().get("firstname")[0];
        String secondname = requestContent.getRequestParameters().get("secondname")[0];
        return new User(login, password, firstname, secondname);
    }
}
