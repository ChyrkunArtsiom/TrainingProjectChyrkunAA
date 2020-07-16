package by.chyrkun.training.service.command.course_registration;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.CourseRegistration;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.exception.CourseNotFoundServiceException;
import by.chyrkun.training.service.exception.UserNotFoundServiceException;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.receiver.CourseRegistrationReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

public class RegisterCourseCommand implements Command {
    private static final String MESSAGE = "message";
    private CourseRegistrationReceiver receiver;
    private UserReceiver userReceiver;
    private CourseReceiver courseReceiver;
    private CommandResult result;

    public RegisterCourseCommand() {
        receiver = new CourseRegistrationReceiver();
        userReceiver = new UserReceiver();
        courseReceiver = new CourseReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        String student_id = requestContent.getSessionAttributes().get("user_id").toString();
        String course_id = requestContent.getRequestParameters().get("course_id")[0];
        User student = userReceiver.getById(Integer.parseInt(student_id));
        Course course = courseReceiver.getById(Integer.parseInt(course_id));
        if (student == null) {
            requestContent.setSessionAttribute(MESSAGE, messages.getMessage("userIsNotStudent"));
        }
        else if (course == null) {
            requestContent.setSessionAttribute(MESSAGE, messages.getMessage("courseNotFound"));
        }
        else {
            CourseRegistration courseRegistration = new CourseRegistration(course, student);
            try {
                receiver.create(courseRegistration);
                /*
                if (receiver.create(courseRegistration)) {
                    requestContent.setSessionAttribute(MESSAGE, messages.getMessage("courseRegistrationWasCreated"));
                }*/
            }catch (UserNotFoundServiceException ex) {
                requestContent.setSessionAttribute(MESSAGE, messages.getMessage("userNotFound"));
            }catch (CourseNotFoundServiceException ex){
                requestContent.setSessionAttribute(MESSAGE, messages.getMessage("courseNotFound"));
            }
        }
        result.setPage(PageManager.getProperty("shortpath.page.course") + "/" + course_id);
        result.setResponseType(CommandResult.ResponseType.REDIRECT);
        return result;
    }
}
