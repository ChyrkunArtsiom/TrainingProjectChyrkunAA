package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.validator.ParamValidator;

public class LogInCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String ERROR_MESSAGE = "errorMessage";
    private UserReceiver receiver = new UserReceiver();


    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult result = new CommandResult();
        MessageManager messages = MessageManager.EN;
        String login = requestContent.getRequestParameters().get(PARAM_NAME_LOGIN)[0];
        String password = requestContent.getRequestParameters().get(PARAM_NAME_PASSWORD)[0];
        if (!ParamValidator.isPresent(login, password)) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
            result.setPage(PageManager.getProperty("fullpath.page.login"));
        }
        else {
            User user = receiver.getByLogin(login);
            if ((receiver.getByLogin(login) == null) || (!user.getPassword().equals(password))) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("loginDataIsNotValid"));
                result.setPage(PageManager.getProperty("fullpath.page.login"));
            }
            else {
                requestContent.setSessionAttribute("user_id", user.getId());
                requestContent.setSessionAttribute("userName", user.getLogin());
                requestContent.setSessionAttribute("role", user.getRole().getName());
                result.setPage(PageManager.getProperty("shortpath.page.main"));
                result.setResponseType(CommandResult.ResponseType.REDIRECT);
            }
        }
        requestContent.setRequestAttribute(PARAM_NAME_LOGIN, login);
        return result;
    }
}
