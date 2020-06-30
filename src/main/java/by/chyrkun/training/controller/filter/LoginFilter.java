package by.chyrkun.training.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(urlPatterns = {"/*"})
public class LoginFilter implements Filter {
    private static final List<String> URIS = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        URIS.add("login");
        URIS.add("signup");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestDispatcher dispatcher;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String page = getPage(req);
        if (URIS.contains(page)) {
            dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/" + page + ".jsp");
            dispatcher.forward(req, resp);
        }else {
            chain.doFilter(request, response);
        }
    }

    private String getPage(HttpServletRequest request) {
        return (request).getRequestURI().
                substring((request).getContextPath().length()).
                replaceAll("/", "");
    }
}
