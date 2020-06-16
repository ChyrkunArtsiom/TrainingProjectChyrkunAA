package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.command.role.GetRolesCommand;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;
import by.chyrkun.training.service.receiver.RoleReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.validator.ParamValidator;
import by.chyrkun.training.service.validator.UserValidator;

public class CreateUserCommand extends BaseCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_FIRSTNAME = "firstname";
    private static final String PARAM_NAME_SECONDNAME = "secondname";
    private static final String PARAM_NAME_ROLE_ID = "role_id";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE = "message";
    private UserReceiver receiver = new UserReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.EN;
        CommandResult result = new CommandResult();
        String login = requestContent.getRequestParameters().get(PARAM_NAME_LOGIN)[0];
        String password = requestContent.getRequestParameters().get(PARAM_NAME_PASSWORD)[0];
        String firstname = requestContent.getRequestParameters().get(PARAM_NAME_FIRSTNAME)[0];
        String secondname = requestContent.getRequestParameters().get(PARAM_NAME_SECONDNAME)[0];
        String role_id_string;
        int role_id;
        boolean shouldShowRoles = true;
        boolean isAdmin = isAdmin(requestContent);
        first: try {
            if (isAdmin) {
                role_id_string = requestContent.getRequestParameters().get(PARAM_NAME_ROLE_ID)[0];
                if (!ParamValidator.isPresent(login, password, firstname, secondname, role_id_string)) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
                    result.setPage(PageManager.getProperty("fullpath.page.createuser"));
                    break first;
                }
                role_id = Integer.parseInt(role_id_string);
            }
            else {
                if (!ParamValidator.isPresent(login, password, firstname, secondname)) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
                    result.setPage(PageManager.getProperty("fullpath.page.createuser"));
                    break first;
                }
                role_id = 2;
            }
            RoleReceiver roleReceiver = new RoleReceiver();
            Role role = roleReceiver.getById(role_id);
            if (role == null) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("roleNotFound"));
                result.setPage(PageManager.getProperty("fullpath.page.createuser"));
                break first;
            }
            if (!UserValidator.isLoginValid(login)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("usernameIsNotValid"));
                result.setPage(PageManager.getProperty("fullpath.page.createuser"));
                break first;
            }
            if (!UserValidator.isPasswordValid(password)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("passwordIsNotValid"));
                result.setPage(PageManager.getProperty("fullpath.page.createuser"));
                break first;
            }
            if (!UserValidator.isNameValid(firstname) || !UserValidator.isNameValid(secondname)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("nameIsNotValid"));
                result.setPage(PageManager.getProperty("fullpath.page.createuser"));
                break first;
            }
            User user = new User(login, password, firstname, secondname, role);
            if (receiver.create(user)) {
                if (isAdmin) {
                    requestContent.setSessionAttribute(MESSAGE, messages.getMessage("userWasCreated"));
                    result.setPage(PageManager.getProperty("shortpath.page.admin.createuser"));
                    result.setResponseType(CommandResult.ResponseType.REDIRECT);
                }
                else{
                    requestContent.setSessionAttribute("user_id", user.getId());
                    requestContent.setSessionAttribute("userName", user.getLogin());
                    result.setPage(PageManager.getProperty("shortpath.page.main"));
                    result.setResponseType(CommandResult.ResponseType.REDIRECT);
                }
                shouldShowRoles = false;
            }
            else {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("suchUserAlreadyExists"));
                result.setPage(PageManager.getProperty("fullpath.page.createuser"));
            }

        }catch (EntityNotFoundServiceException ex) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("roleNotFound"));
            result.setPage(PageManager.getProperty("fullpath.page.createuser"));
        }
        if (shouldShowRoles) {
            setNext(new GetRolesCommand());
            next.execute(requestContent);
        }
        requestContent.setRequestAttribute(PARAM_NAME_LOGIN, login);
        requestContent.setRequestAttribute(PARAM_NAME_FIRSTNAME, firstname);
        requestContent.setRequestAttribute(PARAM_NAME_SECONDNAME, secondname);
        return result;
    }

    private boolean isAdmin(RequestContent requestContent) {
        if ((requestContent.getSessionAttributes().get("role")!= null) &&
                (requestContent.getSessionAttributes().get("role").equals("admin"))) {
            return true;
        }
        return false;
    }
}


