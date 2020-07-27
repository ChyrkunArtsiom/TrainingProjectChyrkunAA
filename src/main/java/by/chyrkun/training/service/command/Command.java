package by.chyrkun.training.service.command;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.service.resource.MessageManager;

/**
 * The interface gives to the user ability to execute different commands to interact with database
 * depending on content of {@link RequestContent} object.
 */
public interface Command {
    /**
     * Executes a command. Gets {@link RequestContent} object. Returns {@link CommandResult} object.
     *
     * @param requestContent @link RequestContent} object
     * @return the {@link CommandResult} object
     */
    CommandResult execute(RequestContent requestContent);

    /**
     * Sets lang.
     *
     * @param requestContent the request content
     * @return the lang
     */
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
