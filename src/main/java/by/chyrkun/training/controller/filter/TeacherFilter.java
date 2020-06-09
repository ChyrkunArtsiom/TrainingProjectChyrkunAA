package by.chyrkun.training.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(dispatcherTypes = {DispatcherType.REQUEST},
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
        if (role != null && !role.toString().equals("teacher")) {
            resp.sendRedirect(req.getContextPath() + indexPath);
            return;
        }
        switch(req.getRequestURI()){
            case "/training/teacher/createcourse": {
                dispatcher = req.getRequestDispatcher("/jsp/createCourse.jsp");
                dispatcher.forward(req, resp);
                return;
            }
            case "/training/teacher/session": {
                dispatcher = req.getRequestDispatcher("/app");
                dispatcher.forward(req, resp);
                return;
            }
            default:{
                resp.sendError(404, "Page is not found");
                break;
            }
        }
        chain.doFilter(request, response);
    }
}
