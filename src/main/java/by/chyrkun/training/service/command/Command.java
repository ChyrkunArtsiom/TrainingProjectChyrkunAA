package by.chyrkun.training.service.command;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;

public interface Command {
    CommandResult execute(RequestContent requestContent);
}
