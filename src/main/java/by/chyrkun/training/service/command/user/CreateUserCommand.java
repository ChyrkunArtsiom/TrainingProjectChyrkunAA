package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.command.role.GetRolesCommand;
import by.chyrkun.training.service.exception.RoleNotFoundServiceException;
import by.chyrkun.training.service.receiver.RoleReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.util.GetterFromRequestContent;
import by.chyrkun.training.service.validator.UserValidator;

/**
 * The class-command for user creation. Extends {@link BaseCommand}, implements {@link Command}.
 */
public class CreateUserCommand extends BaseCommand implements Command {
    private static final String PARAM_NAME_ROLE_ID = "role_id";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE = "message";
    private static final String CREATE_USER_PAGE = PageManager.getPage("fullpath.page.createuser");
    private UserReceiver userReceiver;
    private RoleReceiver roleReceiver;
    private CommandResult result;

    /**
     * Instantiates a new Create user command.
     */
    public CreateUserCommand() {
        userReceiver = new UserReceiver();
        roleReceiver = new RoleReceiver();
        next = new GetRolesCommand();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        String password = requestContent.getRequestParameters().get("password")[0];
        User user = GetterFromRequestContent.getUserFromRequest(requestContent);
        int role_id = Integer.parseInt(requestContent.getRequestParameters().get(PARAM_NAME_ROLE_ID)[0]);
        first: try {
            Role role = roleReceiver.getById(role_id);
            if (role == null) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("roleNotFound"));
                result.setResponseType(CommandResult.ResponseType.FORWARD);
                result.setPage(CREATE_USER_PAGE);
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
                result.setPage(CREATE_USER_PAGE);
                break first;
            }
            if (!UserValidator.isUserValid(user, message, messages)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, message);
                result.setResponseType(CommandResult.ResponseType.FORWARD);
                result.setPage(CREATE_USER_PAGE);
                break first;
            }
            else {
                if (userReceiver.create(user)) {
                    requestContent.setSessionAttribute(MESSAGE, messages.getMessage("userWasCreated"));
                    result.setPage(PageManager.getPage("shortpath.page.admin.createuser"));
                    result.setResponseType(CommandResult.ResponseType.REDIRECT);
                }
                else {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("suchUserAlreadyExists"));
                    result.setResponseType(CommandResult.ResponseType.FORWARD);
                    result.setPage(CREATE_USER_PAGE);
                }
            }
        }catch (RoleNotFoundServiceException ex) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("roleNotFound"));
            result.setResponseType(CommandResult.ResponseType.FORWARD);
            result.setPage(CREATE_USER_PAGE);
        }
        next.execute(requestContent);
        requestContent.setRequestAttribute("login", user.getLogin());
        requestContent.setRequestAttribute("firstname", user.getFirstname());
        requestContent.setRequestAttribute("secondname", user.getSecondname());
        return result;
    }
}


