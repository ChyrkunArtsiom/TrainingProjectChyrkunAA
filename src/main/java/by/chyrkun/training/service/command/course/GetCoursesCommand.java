package by.chyrkun.training.service.command.course;

import by.chyrkun.training.controller.CommandResult;
import by.chyrkun.training.controller.RequestContent;
import by.chyrkun.training.model.Course;
import by.chyrkun.training.service.command.BaseCommand;
import by.chyrkun.training.service.command.Command;
import by.chyrkun.training.service.receiver.CourseReceiver;
import by.chyrkun.training.service.resource.MessageManager;
import by.chyrkun.training.service.resource.PageManager;

import java.util.List;

/**
 * The class-command for getting courses. Extends {@link BaseCommand}, implements {@link Command}.
 */
public class GetCoursesCommand extends BaseCommand implements Command {
    private static final String COURSES = "courses";
    private CourseReceiver receiver;
    private CommandResult result;

    /**
     * Instantiates a new Get courses command.
     */
    public GetCoursesCommand() {
        receiver = new CourseReceiver();
        result = new CommandResult();
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        List<Course> courses = null;
        int id = (Integer)requestContent.getSessionAttributes().get("user_id");
        int page;
        int max_pages = 0;
        Object select = requestContent.getRequestAttributes().get("select");
        String role = requestContent.getSessionAttributes().get("role").toString();
        try {
            if (requestContent.getRequestAttributes().get("page") == null) {
                page = 0;
            }else {
                page = Integer.parseInt(requestContent.getRequestAttributes().get("page").toString());
            }
        }catch (NumberFormatException ex) {
            page = 1;
        }
        switch (role) {
            case "teacher": {
                courses = receiver.getByTeacher(id, page);
                max_pages = receiver.getPagesForTeacher(id);
                break;
            }
            case "student": {
                boolean chosen = Boolean.parseBoolean(requestContent.getRequestAttributes().get("chosen").toString());
                courses = receiver.getByStudent(id, chosen, page);
                max_pages = receiver.getPagesForStudent(id, chosen);
                break;
            }
            case "admin": {
                courses = receiver.getAll(page);
                max_pages = receiver.getPages();
            }
        }
        if (courses != null) {
            requestContent.setRequestAttribute(COURSES, courses);
            requestContent.setRequestAttribute("max_pages", max_pages);
        }
        if (select != null && select.equals("for_task")) {
            result.setPage(PageManager.getPage("fullpath.page.createtask"));
        } else {
            result.setPage(PageManager.getPage("fullpath.page.courses"));
        }
        return result;
    }
}
