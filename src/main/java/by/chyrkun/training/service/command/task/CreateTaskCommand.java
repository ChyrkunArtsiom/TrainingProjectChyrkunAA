package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.validator.ParamValidator;
import by.chyrkun.training.service.validator.TaskValidator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CreateTaskCommand implements Command {
    private static final String PARAM_NAME = "name";
    private static final String PARAM_COURSE_ID = "course_id";
    private static final String PARAM_STARTDATE = "startdate";
    private static final String PARAM_DEADLINE = "deadline";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE = "message";
    private TaskReceiver receiver = new TaskReceiver();

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = MessageManager.EN;
        CommandResult result = new CommandResult();
        String name = requestContent.getRequestParameters().get(PARAM_NAME)[0];
        String course_id = requestContent.getRequestParameters().get(PARAM_COURSE_ID)[0];
        String startdate = requestContent.getRequestParameters().get(PARAM_STARTDATE)[0];
        String deadline = requestContent.getRequestParameters().get(PARAM_DEADLINE)[0];
        first: try {
            if (!ParamValidator.isPresent(name, course_id, startdate)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
                result.setPage(PageManager.getProperty("fullpath.page.createtask"));
                break first;
            }
            else {
                CourseReceiver courseReceiver = new CourseReceiver();
                Course course = courseReceiver.getById(Integer.parseInt(course_id));
                if (course == null) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("courseNotFound"));
                    result.setPage(PageManager.getProperty("fullpath.page.createtask"));
                    break first;
                }
                if (!TaskValidator.isNameValid(name)) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("nameIsNotValid"));
                    result.setPage(PageManager.getProperty("fullpath.page.createtask"));
                    break first;
                }
                LocalDate start = LocalDate.parse(startdate);
                LocalDate end = LocalDate.parse(deadline);
                if (!deadline.isEmpty()) {
                    end = LocalDate.parse(deadline);
                }
                if (!TaskValidator.isDateValid(start, end)) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("dateIsNotValid"));
                    result.setPage(PageManager.getProperty("fullpath.page.createtask"));
                    break first;
                }
                Task task = new Task(name, start, end, course);
                if (receiver.create(task)) {
                    requestContent.setSessionAttribute(MESSAGE, messages.getMessage("taskWasCreated"));
                    result.setPage(PageManager.getProperty("shortpath.page.teacher.courses") + "?command=course&course_id=" + course_id);
                    result.setResponseType(CommandResult.ResponseType.REDIRECT);
                }
            }
        }
        catch (DateTimeParseException ex) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("dateIsNotValid"));
            result.setPage(PageManager.getProperty("fullpath.page.createtask"));
        }
        catch (EntityNotFoundServiceException ex) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("courseNotFound"));
            result.setPage(PageManager.getProperty("fullpath.page.createtask"));
        }
        requestContent.setRequestAttribute(PARAM_NAME, name);
        requestContent.setRequestAttribute(PARAM_COURSE_ID, course_id);
        requestContent.setRequestAttribute(PARAM_STARTDATE, startdate);
        requestContent.setRequestAttribute(PARAM_DEADLINE, deadline);
        return result;
    }
}
