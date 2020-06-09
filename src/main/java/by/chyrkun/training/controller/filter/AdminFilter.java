package by.chyrkun.training.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(dispatcherTypes = {DispatcherType.REQUEST},
        urlPatterns = {"/admin/*"}, initParams = {@WebInitParam(name = "INDEX_PATH", value = "/")})
public class AdminFilter implements Filter {
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
        if (role != null && !role.toString().equals("admin")) {
            resp.sendRedirect(req.getContextPath() + indexPath);
            return;
        }
        switch(req.getRequestURI()){
            case "/training/admin": {
                dispatcher = req.getRequestDispatcher("/jsp/admin.jsp");
                dispatcher.forward(req, resp);
                return;
            }
            case "/training/admin/createuser": {
                dispatcher = req.getRequestDispatcher("/jsp/createUser.jsp");
                dispatcher.forward(req, resp);
                return;
            }
            case "/training/admin/updateuser": {
                req.setAttribute("command", "update_user");
                dispatcher = req.getRequestDispatcher("/app");
                dispatcher.forward(req, resp);
                return;
            }
            case "/training/admin/deleteuser": {
                dispatcher = req.getRequestDispatcher("/jsp/deleteUser.jsp");
                dispatcher.forward(req, resp);
                return;
            }
            case "/training/admin/createcourse": {
                dispatcher = req.getRequestDispatcher("/jsp/createCourse.jsp");
                dispatcher.forward(req, resp);
                return;
            }
            case "/training/admin/deletecourse": {
                dispatcher = req.getRequestDispatcher("/jsp/deleteCourse.jsp");
                dispatcher.forward(req, resp);
                return;
            }
            case "/training/admin/updatecourse": {
                dispatcher = req.getRequestDispatcher("/jsp/updateCourse.jsp");
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
