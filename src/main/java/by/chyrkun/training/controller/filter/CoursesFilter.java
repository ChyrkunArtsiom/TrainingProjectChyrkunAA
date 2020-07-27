package by.chyrkun.training.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The webfilter for pages that display courses.
 */
@WebFilter(filterName = "coursesFilter", urlPatterns = {"/teacher/courses/*", "/student/courses/*", "/student/registered/*"})
public class CoursesFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        RequestDispatcher dispatcher;
        int page = getPageNumber(req);
        req.setAttribute("command", "get_courses");
        req.setAttribute("page", page);
        dispatcher = req.getRequestDispatcher("/app");
        dispatcher.forward(req, resp);
    }

    private int getPageNumber(HttpServletRequest request) {
        String URL = request.getServletPath();
        if (URL.lastIndexOf("courses/") != -1) {
            try {
                int num = Integer.parseInt(URL.substring(URL.lastIndexOf("courses/") + "courses/".length()));
                if (num < 1) {
                    return 1;
                }else {
                    return num;
                }
            }catch (NumberFormatException ex) {
                return 1;
            }
        }else {
            return 1;
        }
    }
}
