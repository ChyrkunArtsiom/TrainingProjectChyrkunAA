package by.chyrkun.training.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "sessionLocaleFilter", urlPatterns = {"/*"})
public class SessionLocaleFIlter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestDispatcher dispatcher;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getParameter("sessionLocale") != null) {
            if (req.getSession().getAttribute("lang") != null) {
                if (req.getParameter("sessionLocale").matches("[\\w]{2}_[\\w]{2}")) {
                    req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
                }else {
                    req.getSession().setAttribute("lang", "en_US");
                }
            }else {
                if (req.getParameter("sessionLocale").matches("[\\w]{2}_[\\w]{2}")) {
                    req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
                }else {
                    req.getSession().setAttribute("lang", "en_US");
                }
            }
        }else {
            if (req.getSession().getAttribute("lang") == null) {
                req.getSession().setAttribute("lang", "en_US");
            }
        }

        chain.doFilter(request, response);
    }
}
