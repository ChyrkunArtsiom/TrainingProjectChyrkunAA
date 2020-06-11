package by.chyrkun.training.controller.filter;

import by.chyrkun.training.service.resource.ConfigurationManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/signup", "/session", "/login", "/userProfile"})
public class UrlFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        switch(req.getRequestURI()) {
            case "/training/signup":{
                RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/createUser.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "/training/login":{
                RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/login.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            case "/training/userProfile":{
                RequestDispatcher dispatcher = req.getRequestDispatcher("/app");
                dispatcher.forward(req, resp);
                break;
            }
            case "/training/session": {
                if (req.getHeader("referer") == null) {
                    resp.sendRedirect(ConfigurationManager.getProperty("shortpath.page.login"));
                }
                else {
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/app");
                    dispatcher.forward(req, resp);
                }
                break;
            }
            default:{
                resp.sendError(404, "Page is not found");
            }
        }
    }
}
