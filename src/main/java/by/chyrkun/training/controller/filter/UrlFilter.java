package by.chyrkun.training.controller.filter;

import by.chyrkun.training.service.resource.ConfigurationManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/signup", "/session", "/login", "/userProfile", "/course"})
public class UrlFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestDispatcher dispatcher;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        switch(req.getRequestURI()) {
            case "/training/signup":{
                dispatcher = req.getRequestDispatcher("/jsp/createUser.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "/training/login":{
                dispatcher = req.getRequestDispatcher("/jsp/login.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "/training/userProfile":{
                dispatcher = req.getRequestDispatcher("/app");
                dispatcher.forward(req, resp);
                break;
            }
            case "/training/session": {
                if (req.getHeader("referer") == null) {
                    resp.sendRedirect(ConfigurationManager.getProperty("shortpath.page.login"));
                }
                else {
                    dispatcher = req.getRequestDispatcher("/app");
                    dispatcher.forward(req, resp);
                }
                break;
            }
            case "/training/course": {
                dispatcher = req.getRequestDispatcher("/app");
                dispatcher.forward(req, resp);
                break;
            }
            default:{
                resp.sendError(404, "Page is not found");
            }
        }
    }
}
