package by.chyrkun.training.service.invoker;

import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.factory.CommandFactoryClient;

public class Invoker {

    public void invokeCommand(String content){
        Command command;
        command = CommandFactoryClient.getInstance().initCommand(content);
        command.execute();
    }
}
