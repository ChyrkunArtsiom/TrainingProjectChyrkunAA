package by.chyrkun.training.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The webfilter for '/session' page.
 */
@WebFilter(urlPatterns = {"/session"})
public class SessionPageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestDispatcher dispatcher;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getHeader("referer") == null) {
            resp.sendError(404, "Page is not found");
        }
        else {
            dispatcher = req.getRequestDispatcher("/app");
            dispatcher.forward(req, resp);
        }
    }
}
