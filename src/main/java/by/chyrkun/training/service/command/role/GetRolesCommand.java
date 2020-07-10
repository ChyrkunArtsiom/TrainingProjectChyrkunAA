package by.chyrkun.training.service.command.role;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Role;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.RoleReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

import java.util.List;

public class GetRolesCommand extends BaseCommand implements Command {
    private static final String PARAM_NAME_ROLES = "roles";
    private static final String ERROR_MESSAGE = "errorMessage";
    private CommandResult result = new CommandResult();
    private  RoleReceiver roleReceiver = new RoleReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        List<Role> roles;
        roles = roleReceiver.getAll();
        if (roles == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("rolesNotFound"));
        }else {
            requestContent.setRequestAttribute(PARAM_NAME_ROLES, roles);
        }
        result.setPage(PageManager.getProperty("fullpath.page.createuser"));
        return result;
    }
}
