package by.chyrkun.training.service.factory;

import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.command.CommandType;
import by.chyrkun.training.service.exception.CommandNotFoundException;

import javax.servlet.http.HttpServletRequest;

public class CommandFactoryClient {
    private static final CommandFactoryClient INSTANCE = new CommandFactoryClient();

    public static CommandFactoryClient getInstance(){
        return INSTANCE;
    }

    public Command initCommand(HttpServletRequest req) throws CommandNotFoundException {
        Command command;
        try {
            String action = req.getParameter("command");
            if (action == null) {
                action = req.getAttribute("command").toString();
            }
            if (action == null) {
                throw new CommandNotFoundException("Command not found");
            }
            CommandType type = CommandType.valueOf(action.toUpperCase());
            command = type.getCommand();
        }catch (IllegalArgumentException ex) {
            throw new CommandNotFoundException("Command not found");
        }
        return command;
    }
}
