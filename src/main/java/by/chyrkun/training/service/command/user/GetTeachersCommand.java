package by.chyrkun.training.service.command.user;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.resource.ConfigurationManager;
import by.chyrkun.training.service.util.RequestContentSetter;


public class GetTeachersCommand implements Command {

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult result = new CommandResult();
        RequestContentSetter.showTeachers(requestContent);
        result.setPage(ConfigurationManager.getProperty("fullpath.page.createcourse"));
        return result;
    }
}
