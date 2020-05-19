package by.chyrkun.training.service.factory;

import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.command.CommandType;

public class CommandFactoryClient {
    private static final CommandFactoryClient INSTANCE = new CommandFactoryClient();

    public static CommandFactoryClient getInstance(){
        return INSTANCE;
    }

    public Command initCommand(String content){
        Command command = null;
        try {
            String commandName = content;
            CommandType type = CommandType.valueOf(commandName);
            command = type.getCommand();
        }catch (IllegalArgumentException ex){
            System.out.println("enum not found");
        }
        return command;
    }
}
