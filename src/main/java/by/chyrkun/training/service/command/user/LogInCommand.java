package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.util.InputSanitizer;
import by.chyrkun.training.service.util.PasswordHasher;
import by.chyrkun.training.service.util.RequestContentMapper;
import by.chyrkun.training.service.validator.ParamValidator;

import java.util.Arrays;

/**
 * The class-command for login. Implements {@link Command}.
 */
public class LogInCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String ERROR_MESSAGE = "errorMessage";
    private UserReceiver receiver;
    private CommandResult result;

    /**
     * Instantiates a new Log in command.
     */
    public LogInCommand() {
        receiver = new UserReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        String login = requestContent.getRequestParameters().get(PARAM_NAME_LOGIN)[0];
        String password = requestContent.getRequestParameters().get("password")[0];
        if (!ParamValidator.isPresent(login, password)) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
            result.setResponseType(CommandResult.ResponseType.FORWARD);
            result.setPage(PageManager.getPage("fullpath.page.login"));
        }
        else {
            login = InputSanitizer.sanitize(login);
            User user = receiver.getByLogin(login);
            if ((user == null) || (!Arrays.equals(user.getPassword(), PasswordHasher.getHash(password)))) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("loginDataIsNotValid"));
                result.setResponseType(CommandResult.ResponseType.FORWARD);
                result.setPage(PageManager.getPage("fullpath.page.login"));
            }
            else {
                RequestContentMapper.setUserInRequest(user, requestContent);
                result.setPage(PageManager.getPage("shortpath.page.main"));
                result.setResponseType(CommandResult.ResponseType.REDIRECT);
            }
        }
        requestContent.setRequestAttribute(PARAM_NAME_LOGIN, login);
        return result;
    }
}
