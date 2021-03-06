package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.util.InputSanitizer;

/**
 * The class-command for user deletion. Implements {@link Command}.
 */
public class DeleteUserCommand implements Command {
    private static final String PARAM_NAME_USER_LOGIN = "login";
    private static final String ERROR_MESSAGE = "errorMessage";
    private UserReceiver receiver;
    private CommandResult result;

    /**
     * Instantiates a new Delete user command.
     */
    public DeleteUserCommand() {
        receiver = new UserReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        String login = requestContent.getRequestParameters().get(PARAM_NAME_USER_LOGIN)[0];
        if (login != null) {
            login = InputSanitizer.sanitize(login);
            if (receiver.delete(login)) {
                result.setPage(PageManager.getPage("shortpath.page.admin.deleteuser"));
                result.setResponseType(CommandResult.ResponseType.REDIRECT);
            }
            else {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("userNotFound"));
                result.setResponseType(CommandResult.ResponseType.FORWARD);
                result.setPage(PageManager.getPage("fullpath.page.admin.deleteuser"));
            }
        }
        else {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
            result.setResponseType(CommandResult.ResponseType.FORWARD);
            result.setPage(PageManager.getPage("fullpath.page.admin.deleteuser"));
        }
        requestContent.setRequestAttribute(PARAM_NAME_USER_LOGIN, login);
        return result;
    }
}
