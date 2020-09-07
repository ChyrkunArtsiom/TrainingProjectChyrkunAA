package by.chyrkun.training.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The webfilter for admin pages.
 */
@WebFilter(urlPatterns = {"/admin/*"}, initParams = {@WebInitParam(name = "INDEX_PATH", value = "/")})
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
        if (role == null || !role.toString().equals("admin")) {
            resp.sendRedirect(req.getContextPath() + indexPath);
            return;
        }

        String path = req.getServletPath();
        if (path.contains("admin/courses")) {
            chain.doFilter(req, resp);
        } else if (path.contains("admin/createuser")) {
            req.setAttribute("command", "get_roles");
            dispatcher = req.getRequestDispatcher("/app");
            dispatcher.forward(req, resp);
        } else if (path.contains("admin/createcourse")) {
            req.setAttribute("command", "get_teachers");
            dispatcher = req.getRequestDispatcher("/app");
            dispatcher.forward(req, resp);
        }
        else {
            resp.sendError(404, "Page is not found");
        }
    }

    private String getCommand(HttpServletRequest request) {
        return request.getServletPath().
                substring(request.getServletPath().indexOf("/admin/") + "/admin/".length(), request.getServletPath().lastIndexOf("/"));
    }
}
