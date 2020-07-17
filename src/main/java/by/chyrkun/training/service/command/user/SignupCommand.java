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
import by.chyrkun.training.service.util.GetterFromRequestContent;
import by.chyrkun.training.service.validator.UserValidator;

public class SignupCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_FIRSTNAME = "firstname";
    private static final String PARAM_NAME_SECONDNAME = "secondname";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String SIGN_UP_PAGE = PageManager.getProperty("fullpath.page.signup");
    private static final int student_id = 3;
    private UserReceiver userReceiver;
    private RoleReceiver roleReceiver;
    private CommandResult result;

    public SignupCommand() {
        userReceiver = new UserReceiver();
        roleReceiver = new RoleReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        User user = GetterFromRequestContent.getUserFromRequest(requestContent);
        first: try {
            Role role = roleReceiver.getById(student_id);
            if (role == null) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("roleNotFound"));
                result.setResponseType(CommandResult.ResponseType.FORWARD);
                result.setPage(SIGN_UP_PAGE);
                break first;
            }
            else {
                user.setRole(role);
            }
            StringBuffer message = new StringBuffer();
            if (!UserValidator.isUserValid(user, message, messages)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, message);
                result.setResponseType(CommandResult.ResponseType.FORWARD);
                result.setPage(SIGN_UP_PAGE);
                break first;
            }
            if (userReceiver.create(user)) {
                    requestContent.setSessionAttribute("user_id", user.getId());
                    requestContent.setSessionAttribute("userName", user.getLogin());
                    result.setPage(PageManager.getProperty("shortpath.page.main"));
                    result.setResponseType(CommandResult.ResponseType.REDIRECT);
            }
            else {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("suchUserAlreadyExists"));
                result.setResponseType(CommandResult.ResponseType.FORWARD);
                result.setPage(SIGN_UP_PAGE);
            }

        }catch (EntityNotFoundServiceException ex) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("roleNotFound"));
            result.setResponseType(CommandResult.ResponseType.FORWARD);
            result.setPage(SIGN_UP_PAGE);
        }
        requestContent.setRequestAttribute(PARAM_NAME_LOGIN, user.getLogin());
        requestContent.setRequestAttribute(PARAM_NAME_FIRSTNAME, user.getFirstname());
        requestContent.setRequestAttribute(PARAM_NAME_SECONDNAME, user.getSecondname());
        return result;
    }
}
