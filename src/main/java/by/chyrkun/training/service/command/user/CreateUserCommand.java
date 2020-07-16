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
import by.chyrkun.training.service.util.GetterFromRequestContent;
import by.chyrkun.training.service.validator.UserValidator;

public class CreateUserCommand extends BaseCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "username";
    private static final String PARAM_NAME_FIRSTNAME = "firstname";
    private static final String PARAM_NAME_SECONDNAME = "secondname";
    private static final String PARAM_NAME_ROLE_ID = "role_id";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE = "message";
    private static final String CREATE_USER_PAGE = PageManager.getProperty("fullpath.page.createuser");
    private UserReceiver userReceiver;
    private RoleReceiver roleReceiver;
    private CommandResult result;

    public CreateUserCommand() {
        userReceiver = new UserReceiver();
        roleReceiver = new RoleReceiver();
        next = new GetRolesCommand();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        User user = GetterFromRequestContent.getUserFromRequest(requestContent);
        int role_id = Integer.parseInt(requestContent.getRequestParameters().get(PARAM_NAME_ROLE_ID)[0]);
        first: try {
            Role role = roleReceiver.getById(role_id);
            if (role == null) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("roleNotFound"));
                result.setPage(CREATE_USER_PAGE);
                break first;
            }
            else {
                user.setRole(role);
            }
            StringBuffer message = new StringBuffer();
            if (!UserValidator.isUserValid(user, message, messages)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, message);
                result.setPage(CREATE_USER_PAGE);
                break first;
            }
            else {
                if (userReceiver.create(user)) {
                    requestContent.setSessionAttribute(MESSAGE, messages.getMessage("userWasCreated"));
                    result.setPage(PageManager.getProperty("shortpath.page.admin.createuser"));
                    result.setResponseType(CommandResult.ResponseType.REDIRECT);
                }
                else {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("suchUserAlreadyExists"));
                    result.setPage(CREATE_USER_PAGE);
                }
            }
        }catch (EntityNotFoundServiceException ex) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("roleNotFound"));
            result.setPage(CREATE_USER_PAGE);
        }
        next.execute(requestContent);
        requestContent.setRequestAttribute(PARAM_NAME_LOGIN, user.getLogin());
        requestContent.setRequestAttribute(PARAM_NAME_FIRSTNAME, user.getFirstname());
        requestContent.setRequestAttribute(PARAM_NAME_SECONDNAME, user.getSecondname());
        return result;
    }
}


