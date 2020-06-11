package by.chyrkun.training.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(dispatcherTypes = {DispatcherType.FORWARD},
        urlPatterns = {"/roles"})
public class UrlForwardFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        switch(req.getRequestURI()){
            case "/training/roles":{
                RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/roles.jsp");
                dispatcher.forward(req, resp);
                break;
            }
            default:{
                resp.sendError(404, "Page is not found");
            }
        }
    }
}