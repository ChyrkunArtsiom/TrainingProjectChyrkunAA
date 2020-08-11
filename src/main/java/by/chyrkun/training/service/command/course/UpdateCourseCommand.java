package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.User;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.receiver.UserReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.util.InputSanitizer;
import by.chyrkun.training.service.validator.CourseValidator;
import by.chyrkun.training.service.validator.ParamValidator;

/**
 * The class-command for updating course. Implements {@link Command}.
 */
public class UpdateCourseCommand implements Command {
    private static final String ERROR_MESSAGE = "errorMessage";
    private CourseReceiver courseReceiver;
    private UserReceiver userReceiver;
    private CommandResult result;

    /**
     * Instantiates a new Update course command.
     */
    public UpdateCourseCommand() {
        courseReceiver = new CourseReceiver();
        userReceiver = new UserReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        String course_id = requestContent.getRequestParameters().get("course_id")[0];
        String course_name = requestContent.getRequestParameters().get("course_name")[0];
        String teacher_id = requestContent.getRequestParameters().get("teacher_id")[0];

        if (!ParamValidator.isPresent(course_name, teacher_id)) {
            requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
        }
        else {
            Course course = courseReceiver.getById(Integer.parseInt(course_id));
            if (course == null) {
                requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("courseNotFound"));
            }
            else {
                User teacher = userReceiver.getById(Integer.parseInt(teacher_id));
                if (teacher == null) {
                    requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("userNotFound"));
                }
                else if (!teacher.getRole().getName().equals("teacher")) {
                    requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("userIsNotTeacher"));
                }
                else if (!CourseValidator.isCourseNameValid(course_name)) {
                    requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("nameIsNotValid"));
                }
                course_name = InputSanitizer.sanitize(course_name);
                course = new Course(Integer.parseInt(course_id), course_name, teacher);
                if (courseReceiver.update(course) == null) {
                    requestContent.setSessionAttribute(ERROR_MESSAGE, messages.getMessage("courseIsNotUpdated"));
                }
            }
        }

        result.setResponseType(CommandResult.ResponseType.REDIRECT);
        result.setPage(PageManager.getPage("shortpath.page.course") + "/" + course_id);
        return result;
    }
}
