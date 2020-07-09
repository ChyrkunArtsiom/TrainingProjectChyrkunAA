package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.util.InputSanitizer;

public class DeleteUserCommand implements Command {
    private static final String PARAM_NAME_USER_LOGIN = "login";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE = "message";
    private UserReceiver receiver = new UserReceiver();
    private CommandResult result = new CommandResult();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.valueOf(requestContent.getSessionAttributes().get("lang").toString());
        String login = requestContent.getRequestParameters().get(PARAM_NAME_USER_LOGIN)[0];
        if (login != null) {
            login = InputSanitizer.sanitize(login);
            if (receiver.delete(login)) {
                result.setPage(PageManager.getProperty("shortpath.page.admin.deleteuser"));
                result.setResponseType(CommandResult.ResponseType.REDIRECT);
            }
            else {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("userNotFound"));
                result.setPage(PageManager.getProperty("fullpath.page.admin.deleteuser"));
            }
        }
        else {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
            result.setPage(PageManager.getProperty("fullpath.page.admin.deleteuser"));
        }
        requestContent.setRequestAttribute(PARAM_NAME_USER_LOGIN, login);
        return result;
    }
}
