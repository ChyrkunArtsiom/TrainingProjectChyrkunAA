package by.chyrkun.training.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "studentFilter", urlPatterns = {"/student/*"}, initParams = {@WebInitParam(name = "INDEX_PATH", value = "/")})
public class StudentFilter implements Filter {
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
        if (role == null || !role.toString().equals("student")) {
            resp.sendRedirect(req.getContextPath() + indexPath);
            return;
        }
        String path = req.getServletPath();
        if (path.contains("courses")) {
            req.setAttribute("chosen", "false");
            chain.doFilter(req, resp);
        } else if (path.contains("registered")) {
            req.setAttribute("chosen", "true");
            chain.doFilter(req, resp);
        } else {
            resp.sendError(404, "Page is not found");
        }

        /*
        switch(req.getRequestURI()) {
            case "/training/student": {
                dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "/training/student/courses": {
                req.setAttribute("command", "get_courses");
                req.setAttribute("chosen", "false");
                dispatcher = req.getRequestDispatcher("/app");
                dispatcher.forward(req, resp);
                break;
            }
            case "/training/student/registered": {
                req.setAttribute("command", "get_courses");
                req.setAttribute("chosen", "true");
                dispatcher = req.getRequestDispatcher("/app");
                dispatcher.forward(req, resp);
                break;
            }
            default: {
                resp.sendError(404, "Page is not found");
            }
        }*/
    }

    private String getCommand(HttpServletRequest request) {
        return request.getServletPath().
                substring(request.getServletPath().indexOf("/teacher/") + "/teacher/".length(), request.getServletPath().lastIndexOf("/"));
    }
}
