package by.chyrkun.training.service.command.task;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.model.Task;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.command.course.GetCoursesCommand;
import by.chyrkun.training.service.exception.EntityNotFoundServiceException;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.receiver.TaskReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;
import by.chyrkun.training.service.util.InputSanitizer;
import by.chyrkun.training.service.validator.ParamValidator;
import by.chyrkun.training.service.validator.TaskValidator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CreateTaskCommand extends BaseCommand implements Command {
    private static final String PARAM_NAME = "name";
    private static final String PARAM_COURSE_ID = "course_id";
    private static final String PARAM_STARTDATE = "startdate";
    private static final String PARAM_DEADLINE = "deadline";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE = "message";
    private TaskReceiver taskReceiver;
    private CourseReceiver courseReceiver;
    private CommandResult result;

    public CreateTaskCommand() {
        taskReceiver = new TaskReceiver();
        courseReceiver = new CourseReceiver();
        result = new CommandResult();
        next = new GetCoursesCommand();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        MessageManager messages = setLang(requestContent);
        String name = requestContent.getRequestParameters().get(PARAM_NAME)[0];
        String course_id = requestContent.getRequestParameters().get(PARAM_COURSE_ID)[0];
        String startdate = requestContent.getRequestParameters().get(PARAM_STARTDATE)[0];
        String deadline = requestContent.getRequestParameters().get(PARAM_DEADLINE)[0];
        first: try {
            if (!ParamValidator.isPresent(name, course_id, startdate)) {
                requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("lineIsEmpty"));
                result.setResponseType(CommandResult.ResponseType.FORWARD);
                result.setPage(PageManager.getProperty("fullpath.page.createtask"));
                break first;
            }
            else {
                Course course = courseReceiver.getById(Integer.parseInt(course_id));
                if (course == null) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("courseNotFound"));
                    result.setResponseType(CommandResult.ResponseType.FORWARD);
                    result.setPage(PageManager.getProperty("fullpath.page.createtask"));
                    break first;
                }
                if (!TaskValidator.isNameValid(name)) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("nameIsNotValid"));
                    result.setResponseType(CommandResult.ResponseType.FORWARD);
                    result.setPage(PageManager.getProperty("fullpath.page.createtask"));
                    break first;
                }
                name = InputSanitizer.sanitize(name);
                LocalDate start = LocalDate.parse(startdate);
                LocalDate end = LocalDate.parse(deadline);
                if (!deadline.isEmpty()) {
                    end = LocalDate.parse(deadline);
                }
                if (!TaskValidator.isDateValid(start, end)) {
                    requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("dateIsNotValid"));
                    result.setResponseType(CommandResult.ResponseType.FORWARD);
                    result.setPage(PageManager.getProperty("fullpath.page.createtask"));
                    break first;
                }
                Task task = new Task(name, start, end, course);
                if (taskReceiver.create(task)) {
                    requestContent.setSessionAttribute(MESSAGE, messages.getMessage("taskWasCreated"));
                    result.setPage(PageManager.getProperty("shortpath.page.createtask"));
                    result.setResponseType(CommandResult.ResponseType.REDIRECT);
                }
            }
        }
        catch (DateTimeParseException ex) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("dateIsNotValid"));
            result.setResponseType(CommandResult.ResponseType.FORWARD);
            result.setPage(PageManager.getProperty("fullpath.page.createtask"));
        }
        catch (EntityNotFoundServiceException ex) {
            requestContent.setRequestAttribute(ERROR_MESSAGE, messages.getMessage("courseNotFound"));
            result.setResponseType(CommandResult.ResponseType.FORWARD);
            result.setPage(PageManager.getProperty("fullpath.page.createtask"));
        }
        requestContent.setRequestAttribute("select", "for_task");
        next.execute(requestContent);
        requestContent.setRequestAttribute(PARAM_NAME, name);
        requestContent.setRequestAttribute(PARAM_COURSE_ID, course_id);
        requestContent.setRequestAttribute(PARAM_STARTDATE, startdate);
        requestContent.setRequestAttribute(PARAM_DEADLINE, deadline);
        return result;
    }
}
