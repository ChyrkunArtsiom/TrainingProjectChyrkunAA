package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

import java.util.List;

/**
 * The class-command for getting users with role 'teacher'. Extends {@link BaseCommand}, implements {@link Command}.
 */
public class GetTeachersCommand extends BaseCommand implements Command {
    private static final String TEACHERS = "teachers";
    private static final String ERROR_MESSAGE = "errorMessage";
    private UserReceiver userReceiver;
    private CommandResult result;

    /**
     * Instantiates a new Get teachers command.
     */
    public GetTeachersCommand() {
        userReceiver = new UserReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        List<User> teachers;
        teachers = userReceiver.getTeachers();
        if (teachers == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("teachersNotFound"));
        }else {
            requestContent.setRequestAttribute(TEACHERS, teachers);
        }
        result.setPage(PageManager.getPage("fullpath.page.createcourse"));
        return result;
    }
}
