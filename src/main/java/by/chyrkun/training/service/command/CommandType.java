package by.chyrkun.training.service.command;

import by.chyrkun.training.service.command.course.*;
import by.chyrkun.training.service.command.course_registration.RegisterCourseCommand;
import by.chyrkun.training.service.command.exercise.DeleteExerciseCommand;
import by.chyrkun.training.service.command.exercise.GetExerciseCommand;
import by.chyrkun.training.service.command.exercise.RegisterTaskCommand;
import by.chyrkun.training.service.command.exercise.UpdateExerciseCommand;
import by.chyrkun.training.service.command.role.GetRolesCommand;
import by.chyrkun.training.service.command.task.CreateTaskCommand;
import by.chyrkun.training.service.command.task.DeleteTaskCommand;
import by.chyrkun.training.service.command.task.GetTaskCommand;
import by.chyrkun.training.service.command.user.*;

/**
 * Enumeration for creating {@link Command} objects.
 */
public enum CommandType {
    /** Object for user creation command. */
    CREATE_USER(new CreateUserCommand()),

    /** Object for user creation command. */
    DELETE_USER(new DeleteUserCommand()),

    /** Object for getting roles command. */
    GET_ROLES(new GetRolesCommand()),

    /** Object for getting an user command. */
    GET_USER(new GetUserCommand()),

    /** Object for getting users with role 'teacher' command. */
    GET_TEACHERS(new GetTeachersCommand()),

    /** Object for updating user command. */
    UPDATE_USER(new UpdateUserCommand()),

    /** Object for course creation command. */
    CREATE_COURSE(new CreateCourseCommand()),

    /** Object for course deletion command. */
    DELETE_COURSE(new DeleteCourseCommand()),

    /** Object for updating course command. */
    UPDATE_COURSE(new UpdateCourseCommand()),

    /** Object for getting courses command. */
    GET_COURSES(new GetCoursesCommand()),

    /** Object for creating course_registration command. */
    REGISTER_COURSE(new RegisterCourseCommand()),

    /** Object for getting a course command. */
    COURSE(new GetCourseCommand()),

    /** Object for getting a task command. */
    TASK(new GetTaskCommand()),

    /** Object for getting exercise command. */
    EXERCISE(new GetExerciseCommand()),

    /** Object for updating exercise command. */
    REVIEW_EXERCISE(new UpdateExerciseCommand()),

    /** Object for task creation command. */
    CREATE_TASK(new CreateTaskCommand()),

    /** Object for task deletion command. */
    DELETE_TASK(new DeleteTaskCommand()),

    /** Object for exercise creation command. */
    REGISTER_TASK(new RegisterTaskCommand()),

    /** Object for exercise deletion command. */
    UNREGISTER_TASK(new DeleteExerciseCommand()),

    /** Object for signing up command. */
    SIGNUP(new SignupCommand()),

    /** Object for login command. */
    LOGIN(new LogInCommand()),

    /** Object for logout command. */
    LOGOUT(new LogoutCommand()),

    /** Object for getting an user command. */
    PROFILE(new GetUserCommand());

    /** {@link Command} object. */
    private Command command;

    /**
     * Constructs a {@link CommandType} object.
     *
     * @param command the {@link Command} object
     */
    CommandType(Command command){
        this.command = command;
    }

    /**
     * Returns {@link Command} object.
     *
     * @return the {@link Command} object
     */
    public Command getCommand(){
        return command;
    }
}
