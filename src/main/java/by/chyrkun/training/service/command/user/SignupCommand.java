package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;
import by.chyrkun.training.service.receiver.RoleReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.validator.UserValidator;

public class SignupCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_FIRSTNAME = "firstname";
    private static final String PARAM_NAME_SECONDNAME = "secondname";
    private static final String PARAM_NAME_ROLE_ID = "role_id";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String SIGN_UP_PAGE = PageManager.getProperty("fullpath.page.signup");
    private static final int student_id = 3;
    private UserReceiver receiver = new UserReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.valueOf(requestContent.getSessionAttributes().get("lang").toString());
        CommandResult result = new CommandResult();
        User user = getUserFromRequest(requestContent);
        first: try {
            RoleReceiver roleReceiver = new RoleReceiver();
            Role role = roleReceiver.getById(student_id);
            if (role == null) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("roleNotFound"));
                result.setPage(SIGN_UP_PAGE);
                break first;
            }
            else {
                user.setRole(role);
            }
            StringBuffer message = new StringBuffer();
            if (!UserValidator.isUserValid(user, message)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, message);
                result.setPage(SIGN_UP_PAGE);
                break first;
            }
            if (receiver.create(user)) {
                    requestContent.setSessionAttribute("user_id", user.getId());
                    requestContent.setSessionAttribute("userName", user.getLogin());
                    result.setPage(PageManager.getProperty("shortpath.page.main"));
                    result.setResponseType(CommandResult.ResponseType.REDIRECT);
            }
            else {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("suchUserAlreadyExists"));
                result.setPage(SIGN_UP_PAGE);
            }

        }catch (EntityNotFoundServiceException ex) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("roleNotFound"));
            result.setPage(SIGN_UP_PAGE);
        }
        requestContent.setRequestAttribute(PARAM_NAME_LOGIN, user.getLogin());
        requestContent.setRequestAttribute(PARAM_NAME_FIRSTNAME, user.getFirstname());
        requestContent.setRequestAttribute(PARAM_NAME_SECONDNAME, user.getSecondname());
        return result;
    }

    private User getUserFromRequest(RequestContent requestContent) {
        String login = requestContent.getRequestParameters().get(PARAM_NAME_LOGIN)[0];
        String password = requestContent.getRequestParameters().get(PARAM_NAME_PASSWORD)[0];
        String firstname = requestContent.getRequestParameters().get(PARAM_NAME_FIRSTNAME)[0];
        String secondname = requestContent.getRequestParameters().get(PARAM_NAME_SECONDNAME)[0];
        String role_id_string = requestContent.getRequestParameters().get(PARAM_NAME_ROLE_ID)[0];
        return new User(login, password, firstname, secondname);
    }
}
