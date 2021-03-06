package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.command.user.GetTeachersCommand;
import by.chyrkun.training.service.exception.UserNotFoundServiceException;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.util.InputSanitizer;
import by.chyrkun.training.service.validator.CourseValidator;
import by.chyrkun.training.service.validator.ParamValidator;
import by.chyrkun.training.service.validator.UserValidator;

/**
 * The class-command for course creation. Extends {@link BaseCommand}, implements {@link Command}.
 */
public class CreateCourseCommand extends BaseCommand implements Command {
    private static final String PARAM_NAME = "name";
    private static final String PARAM_TEACHER_ID = "teacher_id";
    private static final String ERROR_MESSAGE = "errorMessage";
    private CourseReceiver courseReceiver;
    private UserReceiver userReceiver;
    private CommandResult result;

    /**
     * Instantiates a new Create course command.
     */
    public CreateCourseCommand() {
        courseReceiver = new CourseReceiver();
        userReceiver = new UserReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        String name = requestContent.getRequestParameters().get(PARAM_NAME)[0];
        String teacher_id = requestContent.getRequestParameters().get(PARAM_TEACHER_ID)[0];
        boolean showTeachers = true;
        first: try {
            if (!ParamValidator.isPresent(name, teacher_id)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
                result.setResponseType(CommandResult.ResponseType.FORWARD);
                result.setPage(PageManager.getPage("fullpath.page.createcourse"));
                break first;
            }
            else {
                name = InputSanitizer.sanitize(name);
                User teacher = userReceiver.getById(Integer.parseInt(teacher_id));
                if (teacher == null) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("userNotFound"));
                    result.setResponseType(CommandResult.ResponseType.FORWARD);
                    result.setPage(PageManager.getPage("fullpath.page.createcourse"));
                    break first;
                }
                else if (!UserValidator.isTeacher(teacher)) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("userIsNotTeacher"));
                    result.setResponseType(CommandResult.ResponseType.FORWARD);
                    result.setPage(PageManager.getPage("fullpath.page.createcourse"));
                    break first;
                }
                else if (!CourseValidator.isCourseNameValid(name)) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("nameIsNotValid"));
                    result.setResponseType(CommandResult.ResponseType.FORWARD);
                    result.setPage(PageManager.getPage("fullpath.page.createcourse"));
                    break first;
                }
                Course course = new Course(name, teacher);
                if (courseReceiver.create(course)) {
                    showTeachers = false;
                    result.setPage(PageManager.getPage("shortpath.page.createcourse"));
                    result.setResponseType(CommandResult.ResponseType.REDIRECT);
                }
            }
        }catch (UserNotFoundServiceException ex) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("userNotFound"));
            result.setResponseType(CommandResult.ResponseType.FORWARD);
            result.setPage(PageManager.getPage("fullpath.page.createcourse"));
        }finally {
            if (showTeachers) {
                setNext(new GetTeachersCommand());
                next.execute(requestContent);
            }
            requestContent.setRequestAttribute(PARAM_NAME, name);
            requestContent.setRequestAttribute(PARAM_TEACHER_ID, teacher_id);
        }
        return result;
    }
}
