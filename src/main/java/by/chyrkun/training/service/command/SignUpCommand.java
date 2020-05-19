package by.chyrkun.training.service.command;

import by.chyrkun.training.service.receiver.UserReceiver;

public class SignUpCommand implements Command {
    private UserReceiver receiver = new UserReceiver();
    @Override
    public void execute() {
        //Validation
        receiver.signUp();
    }
}
