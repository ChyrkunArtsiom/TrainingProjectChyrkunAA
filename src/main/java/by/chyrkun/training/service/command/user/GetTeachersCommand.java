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


public class GetTeachersCommand extends BaseCommand implements Command {
    private static final String TEACHERS = "teachers";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.valueOf(requestContent.getSessionAttributes().get("lang").toString());
        CommandResult result = new CommandResult();
        List<User> teachers;
        UserReceiver userReceiver = new UserReceiver();
        teachers = userReceiver.getTeachers();
        if (teachers == null) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("teachersNotFound"));
        }else {
            requestContent.setRequestAttribute(TEACHERS, teachers);
        }
        result.setPage(PageManager.getProperty("fullpath.page.createcourse"));
        return result;
    }
}
