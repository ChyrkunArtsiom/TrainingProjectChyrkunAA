package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.ConfigurationManager;
import by.chyrkun.training.service.resource.MessageManager;

public class DeleteUserCommand implements Command {
    private static final String PARAM_NAME_USER_ID = "user_id";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String RESULT_MESSAGE = "resultMessage";
    private UserReceiver receiver = new UserReceiver();
    private CommandResult result = new CommandResult();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.EN;
        String user_id = requestContent.getRequestParameters().get(PARAM_NAME_USER_ID)[0];
        if (user_id != null) {
            if (receiver.delete(Integer.parseInt(user_id))) {
                requestContent.setRequestAttribute(RESULT_MESSAGE, messages.getMessage("userWasDeleted"));
                result.setPage(ConfigurationManager.getProperty("shortpath.page.admin.deleteuser"));
                result.setResponseType(CommandResult.ResponseType.REDIRECT);
            }
            else {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("userNotFound"));
                result.setPage(ConfigurationManager.getProperty("fullpath.page.createuser"));
            }
        }
        else {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
            result.setPage(ConfigurationManager.getProperty("fullpath.page.createuser"));
        }

        return result;
    }
}
