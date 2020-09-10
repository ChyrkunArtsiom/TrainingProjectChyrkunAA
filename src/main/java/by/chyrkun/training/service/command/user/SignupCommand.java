package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.exception.RoleNotFoundServiceException;
import by.chyrkun.training.service.receiver.RoleReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.util.RequestContentMapper;
import by.chyrkun.training.service.validator.UserValidator;

/**
 * The class-command for signing up. Implements {@link Command}.
 */
public class SignupCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_FIRSTNAME = "firstname";
    private static final String PARAM_NAME_SECONDNAME = "secondname";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String SIGN_UP_PAGE = PageManager.getPage("fullpath.page.signup");
    private static final int student_id = 3;
    private UserReceiver userReceiver;
    private RoleReceiver roleReceiver;
    private CommandResult result;

    /**
     * Instantiates a new Signup command.
     */
    public SignupCommand() {
        userReceiver = new UserReceiver();
        roleReceiver = new RoleReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        String password = requestContent.getRequestParameters().get("password")[0];
        User user = RequestContentMapper.getUserFromRequest(requestContent);
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
            if (!UserValidator.isPasswordValid(password)) {
                message.append(messages.getMessage("passwordIsNotValid"));
                requestContent.setRequestAttribute(ERROR_MESSAGE, message);
                result.setResponseType(CommandResult.ResponseType.FORWARD);
                result.setPage(SIGN_UP_PAGE);
                break first;
            }
            if (!UserValidator.isUserValid(user, message, messages)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, message);
                result.setResponseType(CommandResult.ResponseType.FORWARD);
                result.setPage(SIGN_UP_PAGE);
                break first;
            }
            if (userReceiver.create(user)) {
                User created = userReceiver.getByLogin(user.getLogin());
                user.setId(created.getId());
                RequestContentMapper.setUserInRequest(user, requestContent);
                result.setPage(PageManager.getPage("shortpath.page.main"));
                result.setResponseType(CommandResult.ResponseType.REDIRECT);
            }
            else {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("suchUserAlreadyExists"));
                result.setResponseType(CommandResult.ResponseType.FORWARD);
                result.setPage(SIGN_UP_PAGE);
            }

        }catch (RoleNotFoundServiceException ex) {
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
