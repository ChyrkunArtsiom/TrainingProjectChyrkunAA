package by.chyrkun.training.controller.filter;

import by.chyrkun.training.service.resource.PageManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/userProfile", "/course", "/task", "/exercise"})
public class UrlFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestDispatcher dispatcher;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (((HttpServletRequest) request).getSession().getAttribute("user_id") != null) {
            switch(req.getRequestURI()) {
                case "/training/userProfile": {
                    dispatcher = req.getRequestDispatcher("/app");
                    dispatcher.forward(req, resp);
                    break;
                }
                case "/training/task": {
                    dispatcher = req.getRequestDispatcher("/app");
                    dispatcher.forward(req, resp);
                    break;
                }
                case "/training/course": {
                    dispatcher = req.getRequestDispatcher("/app");
                    dispatcher.forward(req, resp);
                    break;
                }
                case "/training/exercise": {
                    dispatcher = req.getRequestDispatcher("/app");
                    dispatcher.forward(req, resp);
                    break;
                }
                default:{
                    resp.sendError(404, "Page is not found");
                }
            }
        }else {
            chain.doFilter(request, response);
        }
    }
}
