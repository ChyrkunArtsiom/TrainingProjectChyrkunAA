package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.command.user.GetTeachersCommand;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.ConfigurationManager;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.validator.CourseValidator;
import by.chyrkun.training.service.validator.ParamValidator;

public class CreateCourseCommand extends BaseCommand implements Command {
    private static final String PARAM_NAME = "name";
    private static final String PARAM_TEACHER_ID = "teacher_id";
    private static final String ERROR_MESSAGE = "errorMessage";
    private CourseReceiver receiver = new CourseReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.EN;
        CommandResult result = new CommandResult();
        String name = requestContent.getRequestParameters().get(PARAM_NAME)[0];
        String teacher_id = requestContent.getRequestParameters().get(PARAM_TEACHER_ID)[0];
        boolean showTeachers = true;
        first: try{
            if (!ParamValidator.isPresent(name, teacher_id)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
                result.setPage(ConfigurationManager.getProperty("fullpath.page.createcourse"));
                break first;
            }
            else{
                UserReceiver userReceiver = new UserReceiver();
                User teacher = userReceiver.getById(Integer.parseInt(teacher_id));
                if (teacher == null) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("userNotFound"));
                    result.setPage(ConfigurationManager.getProperty("fullpath.page.createcourse"));
                    break first;
                }
                if (!teacher.getRole().getName().equals("teacher")) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("userIsNotTeacher"));
                    result.setPage(ConfigurationManager.getProperty("fullpath.page.createcourse"));
                    break first;
                }
                if (!CourseValidator.isCourseNameValid(name)) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("nameIsNotValid"));
                    result.setPage(ConfigurationManager.getProperty("fullpath.page.createcourse"));
                    break first;
                }
                Course course = new Course(name, teacher);
                if (receiver.create(course)) {
                    showTeachers = false;
                    result.setPage(ConfigurationManager.getProperty("shortpath.page.createcourse"));
                    result.setResponseType(CommandResult.ResponseType.REDIRECT);
                }
            }
        }catch (EntityNotFoundServiceException ex) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("userNotFound"));
            result.setPage(ConfigurationManager.getProperty("fullpath.page.createcourse"));
        }finally {
            if (showTeachers) {
                setNext(new GetTeachersCommand());
                next.execute(requestContent);
            }
            requestContent.setRequestAttribute(PARAM_NAME, name);
            requestContent.setRequestAttribute(PARAM_TEACHER_ID, teacher_id);
            return result;
        }
    }
}
