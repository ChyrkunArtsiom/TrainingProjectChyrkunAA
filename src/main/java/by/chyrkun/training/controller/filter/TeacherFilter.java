package by.chyrkun.training.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        urlPatterns = {"/teacher/*"}, initParams = {@WebInitParam(name = "INDEX_PATH", value = "/")})
public class TeacherFilter implements Filter {
    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) {
        indexPath = filterConfig.getInitParameter("INDEX_PATH");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        RequestDispatcher dispatcher;
        Object role = ((HttpServletRequest) request).getSession().getAttribute("role");
        if (role == null || !role.toString().equals("teacher")) {
            resp.sendRedirect(req.getContextPath() + indexPath);
            return;
        }
        switch(req.getRequestURI()){
            case "/training/teacher": {
                dispatcher = req.getRequestDispatcher("/jsp/teacher.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "/training/teacher/createtask": {
                dispatcher = req.getRequestDispatcher("/jsp/createTask.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "/training/teacher/deletetask": {
                dispatcher = req.getRequestDispatcher("/jsp/deleteTask.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "/training/teacher/opentask": {
                dispatcher = req.getRequestDispatcher("/jsp/openTask.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "/training/teacher/courses": {
                req.setAttribute("command", "get_courses");
                dispatcher = req.getRequestDispatcher("/app");
                dispatcher.forward(req, resp);
                break;
            }
            case "/training/teacher/session": {
                dispatcher = req.getRequestDispatcher("/app");
                dispatcher.forward(req, resp);
                break;
            }
            default: {
                resp.sendError(404, "Page is not found");
            }
        }
    }
}
