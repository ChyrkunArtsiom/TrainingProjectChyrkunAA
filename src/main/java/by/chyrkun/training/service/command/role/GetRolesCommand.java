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

/**
 * The class-command for getting roles. Extends {@link BaseCommand}, implements {@link Command}.
 */
public class GetRolesCommand extends BaseCommand implements Command {
    private static final String PARAM_NAME_ROLES = "roles";
    private static final String ERROR_MESSAGE = "errorMessage";
    private CommandResult result;
    private  RoleReceiver roleReceiver;

    /**
     * Instantiates a new Get roles command.
     */
    public GetRolesCommand() {
        roleReceiver = new RoleReceiver();
        result = new CommandResult();
    }

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
        result.setPage(PageManager.getPage("fullpath.page.createuser"));
        return result;
    }
}
