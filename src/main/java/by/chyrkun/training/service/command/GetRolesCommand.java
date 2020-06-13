package by.chyrkun.training.service.command;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.service.receiver.RoleReceiver;
import by.chyrkun.training.service.util.RequestContentSetter;

import java.util.List;

public class GetRolesCommand implements Command {
    private CommandResult result = new CommandResult();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        RequestContentSetter.showRoles(requestContent);
        result.setPage("/jsp/createUser.jsp");
        return result;
    }
}
