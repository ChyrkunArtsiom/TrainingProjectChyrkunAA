package by.chyrkun.training.service.command;

public enum CommandType {
    SIGN_UP(new SignUpCommand());
    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }
}
