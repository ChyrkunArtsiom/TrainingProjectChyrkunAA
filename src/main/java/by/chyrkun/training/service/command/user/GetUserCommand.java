package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.ConfigurationManager;

public class GetUserCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String ERROR_MESSAGE = "errorMessage";
    UserReceiver receiver = new UserReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult result = new CommandResult();
        User user;
        String login = requestContent.getRequestParameters().get(PARAM_NAME_LOGIN)[0];
        user = receiver.getByLogin(login);
        if (user == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, "User not found");
        }else {
            requestContent.setRequestAttribute("user", user);
        }
        result.setPage(ConfigurationManager.getProperty("fullpath.page.profile"));
        return result;    }
}
