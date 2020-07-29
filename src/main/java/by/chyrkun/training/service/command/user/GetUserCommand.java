package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.command.role.GetRolesCommand;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

/**
 * The class-command for getting an user. Extends {@link BaseCommand}, Implements {@link Command}.
 */
public class GetUserCommand extends BaseCommand implements Command {
    private UserReceiver receiver;
    private CommandResult result;

    /**
     * Instantiates a new Get user command.
     */
    public GetUserCommand() {
        receiver = new UserReceiver();
        result = new CommandResult();
        next = new GetRolesCommand();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        int user_id = Integer.valueOf(requestContent.getRequestAttributes().get("id").toString());
        String role = requestContent.getSessionAttributes().get("role").toString();
        User user = receiver.getById(user_id);
        if (user != null) {
            requestContent.setRequestAttribute("user", user);
        }

        if (role.equals("admin")) {
            next.execute(requestContent);
        }
        result.setPage(PageManager.getPage("fullpath.page.profile"));
        return result;
    }
}
