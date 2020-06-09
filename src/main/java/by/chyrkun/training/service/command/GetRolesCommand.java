package by.chyrkun.training.service.command;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.service.receiver.RoleReceiver;

import java.util.List;

public class GetRolesCommand implements Command {
    private RoleReceiver receiver = new RoleReceiver();
    private CommandResult result = new CommandResult();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        List<Role> roles;
        roles = receiver.getAll();
        if (roles == null) {
            requestContent.setRequestAttribute("errorRolesMessage", "Roles not found");
        }else {
            requestContent.setRequestAttribute("roles", roles);
        }
        result.setPage("/jsp/roles.jsp");
        return result;
    }
}
