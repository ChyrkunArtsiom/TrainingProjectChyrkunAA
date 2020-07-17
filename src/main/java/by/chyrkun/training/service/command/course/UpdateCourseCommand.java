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
import by.chyrkun.training.service.validator.CourseValidator;
import by.chyrkun.training.service.validator.ParamValidator;

public class UpdateCourseCommand implements Command {
    private static final String PARAM_ID = "course_id";
    private static final String PARAM_COURSE_NAME = "course_name";
    private static final String PARAM_TEACHER_ID = "teacher_id";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE = "message";
    private CourseReceiver receiver = new CourseReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        CommandResult result = new CommandResult();
        String id = requestContent.getRequestParameters().get(PARAM_ID)[0];
        String name = requestContent.getRequestParameters().get(PARAM_COURSE_NAME)[0];
        String teacher_id = requestContent.getRequestParameters().get(PARAM_TEACHER_ID)[0];
        if (!ParamValidator.isPresent(name, teacher_id)) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
            result.setPage(PageManager.getProperty("fullpath.page.updatecourse"));
        }
        else {
            Course course = receiver.getById(Integer.parseInt(id));
            if (course == null) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("courseNotFound"));
                result.setPage(PageManager.getProperty("fullpath.page.updatecourse"));
            }
            else {
                UserReceiver userReceiver = new UserReceiver();
                User teacher = userReceiver.getById(Integer.parseInt(teacher_id));
                if (teacher == null) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("userNotFound"));
                    result.setPage(PageManager.getProperty("fullpath.page.updatecourse"));
                }
                else if (!teacher.getRole().getName().equals("teacher")) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("userIsNotTeacher"));
                    result.setPage(PageManager.getProperty("fullpath.page.updatecourse"));
                }
                else if (!CourseValidator.isCourseNameValid(name)) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("nameIsNotValid"));
                    result.setPage(PageManager.getProperty("fullpath.page.updatecourse"));
                }
                course = new Course(Integer.parseInt(id), name, teacher);
                if (receiver.update(course) != null) {
                    result.setPage(PageManager.getProperty("shortpath.page.updatecourse"));
                    requestContent.setSessionAttribute(MESSAGE, messages.getMessage("courseIsUpdated"));
                    result.setResponseType(CommandResult.ResponseType.REDIRECT);
                }
                else {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, "Course wasn't deleted for some reason");
                    result.setPage(PageManager.getProperty("fullpath.page.updatecourse"));
                }
            }
        }
        requestContent.setRequestAttribute(PARAM_ID, id);
        requestContent.setRequestAttribute(PARAM_COURSE_NAME, name);
        requestContent.setRequestAttribute(PARAM_TEACHER_ID, teacher_id);
        return result;
    }
}
