package by.chyrkun.training.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "teacherFilter", urlPatterns = {"/teacher/*"}, initParams = {@WebInitParam(name = "INDEX_PATH", value = "/")})
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
        try {
            String command = getCommand(req);
            switch(command){
                case "createtask": {
                    req.setAttribute("command", "get_courses");
                    req.setAttribute("select", "for_task");
                    dispatcher = req.getRequestDispatcher("/app");
                    dispatcher.forward(req, resp);
                    break;
                }
                case "courses": {
                    chain.doFilter(req, resp);
                    break;
                }
                default: {
                    resp.sendError(404, "Page is not found");
                }
            }
        }catch (StringIndexOutOfBoundsException ex) {
            resp.sendError(404, "Page is not found");
        }
    }

    private String getCommand(HttpServletRequest request) {
        return request.getServletPath().
                substring(request.getServletPath().indexOf("/teacher/") + "/teacher/".length(), request.getServletPath().lastIndexOf("/"));
    }
}
