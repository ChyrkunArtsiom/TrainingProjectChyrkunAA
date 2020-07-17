package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.RoleReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.validator.ParamValidator;
import by.chyrkun.training.service.validator.UserValidator;

public class UpdateUserCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_FIRSTNAME = "firstname";
    private static final String PARAM_NAME_SECONDNAME = "secondname";
    private static final String PARAM_NAME_ROLE_ID = "role_id";
    private static final String ERROR_MESSAGE = "errorMessage";
    private UserReceiver receiver = new UserReceiver();
    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        CommandResult result = new CommandResult();
        String login;
        String password = requestContent.getRequestParameters().get(PARAM_NAME_PASSWORD)[0];
        String firstname = requestContent.getRequestParameters().get(PARAM_NAME_FIRSTNAME)[0];
        String secondname = requestContent.getRequestParameters().get(PARAM_NAME_SECONDNAME)[0];
        String role_id_string;
        int role_id;
        boolean admin = isAdmin(requestContent);
        if (admin) {
            login = requestContent.getRequestParameters().get(PARAM_NAME_LOGIN)[0];
            role_id_string = requestContent.getRequestParameters().get(PARAM_NAME_ROLE_ID)[0];
            if (!ParamValidator.isPresent(login, password, firstname, secondname, role_id_string)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
                result.setPage(PageManager.getProperty("fullpath.page.updateuser"));
            }
            role_id = Integer.parseInt(role_id_string);
        }
        else {
            login = requestContent.getSessionAttributes().get("userName").toString();
            if (!ParamValidator.isPresent(login, password, firstname, secondname)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
                result.setPage(PageManager.getProperty("fullpath.page.updateuser"));
            }
            role_id = 2;
        }
        RoleReceiver roleReceiver = new RoleReceiver();
        Role role = roleReceiver.getById(role_id);
        if (role == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("roleNotFound"));
            result.setPage(PageManager.getProperty("fullpath.page.updateuser"));
        }
        else if (!UserValidator.isLoginValid(login)) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("usernameIsNotValid"));
            result.setPage(PageManager.getProperty("fullpath.page.updateuser"));
        }
        else if (!UserValidator.isPasswordValid(password)) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("passwordIsNotValid"));
            result.setPage(PageManager.getProperty("fullpath.page.updateuser"));
        }
        else if (!UserValidator.isNameValid(firstname) || !UserValidator.isNameValid(secondname)) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("nameIsNotValid"));
            result.setPage(PageManager.getProperty("fullpath.page.updateuser"));
        }
        User user = new User(login, password, firstname, secondname, role);
        if (receiver.update(user) != null) {
            if (admin) {
                result.setPage(PageManager.getProperty("shortpath.page.admin.updateuser"));
                result.setResponseType(CommandResult.ResponseType.REDIRECT);
            }
            else {
                result.setPage(PageManager.getProperty("shortpath.page.updateuser"));
                result.setResponseType(CommandResult.ResponseType.REDIRECT);
            }
        }
        return result;
    }

    boolean isAdmin(RequestContent requestContent) {
        if ((requestContent.getSessionAttributes().get("role")!= null) &&
                (requestContent.getSessionAttributes().get("role").equals("admin"))) {
            return true;
        }
        return false;
    }
}
