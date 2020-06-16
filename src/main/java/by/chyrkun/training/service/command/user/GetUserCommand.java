package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.PageManager;

public class GetUserCommand implements Command {
    private UserReceiver receiver = new UserReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult result = new CommandResult();
        String login = requestContent.getRequestParameters().get("login")[0];
        User user = receiver.getByLogin(login);
        if (user == null) {
            requestContent.setRequestAttribute("errorMessage", "User not found");
        }else {
            requestContent.setRequestAttribute("user", user);
        }
        result.setPage(PageManager.getProperty("fullpath.page.profile"));
        return result;
    }
}
