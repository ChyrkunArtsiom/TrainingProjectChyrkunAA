package by.chyrkun.training.service.command;

import by.chyrkun.training.service.command.course.CreateCourseCommand;
import by.chyrkun.training.service.command.course.DeleteCourseCommand;
import by.chyrkun.training.service.command.course.UpdateCourseCommand;
import by.chyrkun.training.service.command.user.CreateUserCommand;
import by.chyrkun.training.service.command.user.DeleteUserCommand;
import by.chyrkun.training.service.command.user.GetUserCommand;
import by.chyrkun.training.service.command.user.UpdateUserCommand;

public enum CommandType {
    CREATE_USER(new CreateUserCommand()),
    DELETE_USER(new DeleteUserCommand()),
    GET_ROLES(new GetRolesCommand()),
    GET_USER(new GetUserCommand()),
    UPDATE_USER(new UpdateUserCommand()),
    CREATE_COURSE(new CreateCourseCommand()),
    DELETE_COURSE(new DeleteCourseCommand()),
    UPDATE_COURSE(new UpdateCourseCommand()),
    LOGIN(new LogInCommand()),
    LOGOUT(new LogoutCommand()),
    PROFILE(new GetUserCommand());
    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }
}
