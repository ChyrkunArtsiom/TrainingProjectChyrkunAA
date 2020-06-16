package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.resource.PageManager;

public class LogoutCommand implements Command {
    private CommandResult result = new CommandResult();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        result.setPage(PageManager.getProperty("shortpath.page.main"));
        result.setResponseType(CommandResult.ResponseType.SESSION_INVALIDATE);
        return result;
    }
}