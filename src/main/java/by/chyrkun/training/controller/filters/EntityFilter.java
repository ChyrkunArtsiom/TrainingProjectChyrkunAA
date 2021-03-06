package by.chyrkun.training.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * The webfilter for pages that display any entity.
 */
@WebFilter(urlPatterns = {"/profile/*", "/course/*", "/task/*", "/exercise/*"})
public class EntityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestDispatcher dispatcher;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        try {
            String command = getCommand(req);
            String id = getId(req);
            if (((HttpServletRequest) request).getSession().getAttribute("user_id") != null) {
                req.setAttribute("id", id);
                req.setAttribute("command", command);
                dispatcher = req.getRequestDispatcher("/app");
                dispatcher.forward(req, resp);
            } else {
                chain.doFilter(request, response);
            }
        }catch (StringIndexOutOfBoundsException ex) {
            resp.sendError(404, "Page is not found");
        }
    }

    private String getCommand(HttpServletRequest request) {
        return request.getServletPath().
                substring(request.getServletPath().indexOf("/") + 1, request.getServletPath().lastIndexOf("/"));
    }

    private String getId(HttpServletRequest request) {
        return  request.getServletPath().
                substring(request.getServletPath().lastIndexOf("/") + 1);
    }
}
