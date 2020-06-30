package by.chyrkun.training.service.command;

import by.chyrkun.training.service.command.course.*;
import by.chyrkun.training.service.command.course_registration.RegisterCourseCommand;
import by.chyrkun.training.service.command.role.GetRolesCommand;
import by.chyrkun.training.service.command.task.CreateTaskCommand;
import by.chyrkun.training.service.command.task.DeleteTaskCommand;
import by.chyrkun.training.service.command.task.GetTaskCommand;
import by.chyrkun.training.service.command.task_registration.DeleteTaskRegistrationCommand;
import by.chyrkun.training.service.command.task_registration.GetTaskRegistrationCommand;
import by.chyrkun.training.service.command.task_registration.RegisterTaskCommand;
import by.chyrkun.training.service.command.task_registration.UpdateTaskRegistrationCommand;
import by.chyrkun.training.service.command.user.*;

public enum CommandType {
    CREATE_USER(new CreateUserCommand()),
    DELETE_USER(new DeleteUserCommand()),
    GET_ROLES(new GetRolesCommand()),
    GET_USER(new GetUserCommand()),
    GET_TEACHERS(new GetTeachersCommand()),
    UPDATE_USER(new UpdateUserCommand()),
    CREATE_COURSE(new CreateCourseCommand()),
    DELETE_COURSE(new DeleteCourseCommand()),
    UPDATE_COURSE(new UpdateCourseCommand()),
    GET_COURSES(new GetCoursesCommand()),
    REGISTER_COURSE(new RegisterCourseCommand()),
    COURSE(new GetCourseCommand()),
    TASK(new GetTaskCommand()),
    EXERCISE(new GetTaskRegistrationCommand()),
    REVIEW_EXERCISE(new UpdateTaskRegistrationCommand()),
    CREATE_TASK(new CreateTaskCommand()),
    DELETE_TASK(new DeleteTaskCommand()),
    REGISTER_TASK(new RegisterTaskCommand()),
    UNREGISTER_TASK(new DeleteTaskRegistrationCommand()),
    SIGNUP(new SignupCommand()),
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
