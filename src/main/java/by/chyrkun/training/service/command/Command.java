package by.chyrkun.training.service.command;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.resource.MessageManager;

public interface Command {
    CommandResult execute(RequestContent requestContent);

    default MessageManager setLang(RequestContent requestContent) {
        MessageManager manager;
        try {
            manager = MessageManager.valueOf(requestContent.getSessionAttributes().get("lang").toString());
        } catch (IllegalArgumentException ex) {
            manager = MessageManager.en_US;
        }
        return manager;
    }
}
