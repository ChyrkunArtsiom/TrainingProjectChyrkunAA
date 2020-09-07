package by.chyrkun.training.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The webfilter for authorization.
 */
@WebFilter(filterName = "loginFilter", urlPatterns = {"/signup", "/login", "/logout"})
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestDispatcher dispatcher;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String page = getPage(req);
         if (page.equals("logout")) {
            req.setAttribute("command", "logout");
            dispatcher = req.getRequestDispatcher("/app");
            dispatcher.forward(req, resp);
        } else {
            dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/" + page + ".jsp");
            dispatcher.forward(req, resp);
        }
    }

    private String getPage(HttpServletRequest request) {
        return (request).getRequestURI().
                substring((request).getContextPath().length()).
                replaceAll("/", "");
    }
}
