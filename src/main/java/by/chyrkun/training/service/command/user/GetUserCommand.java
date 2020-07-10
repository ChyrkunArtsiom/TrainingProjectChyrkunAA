package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

public class GetUserCommand implements Command {
    private UserReceiver receiver = new UserReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        CommandResult result = new CommandResult();
        int user_id = Integer.valueOf(requestContent.getRequestAttributes().get("id").toString());
        User user = receiver.getById(user_id);
        if (user == null) {
            requestContent.setRequestAttribute("errorMessage", messages.getMessage("userNotFound"));
        }else {
            requestContent.setRequestAttribute("user", user);
        }
        result.setPage(PageManager.getProperty("fullpath.page.profile"));
        return result;
    }
}
