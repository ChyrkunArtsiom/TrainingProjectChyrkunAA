package by.chyrkun.training.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(dispatcherTypes = {DispatcherType.FORWARD},
        urlPatterns = {"/roles", "/main", "/login", "/userProfile"})
public class UrlForwardFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        switch(req.getRequestURI()){
            case "/training/signup":{
                RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/createUser.jsp");
                dispatcher.forward(req, resp);
                return;
            }
            case "/training/roles":{
                RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/roles.jsp");
                dispatcher.forward(req, resp);
                return;
            }
            case "/training/main":{
                RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/main.jsp");
                dispatcher.forward(req, resp);
                return;
            }
            case "/training/login":{
                RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/login.jsp");
                dispatcher.forward(req, resp);
                return;
            }
            case "/training/userProfile":{
                RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/userProfile.jsp");
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