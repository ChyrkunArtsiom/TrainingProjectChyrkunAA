package by.chyrkun.training.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The webfilter for changing locale of page.
 */
@WebFilter(filterName = "sessionLocaleFilter", urlPatterns = {"/*"})
public class SessionLocaleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getParameter("sessionLocale") != null) {
            if (req.getParameter("sessionLocale").matches("[\\w]{2}_[\\w]{2}")) {
                req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
            }else {
                req.getSession().setAttribute("lang", "en_US");
            }
        }else {
            if (req.getSession().getAttribute("lang") == null) {
                req.getSession().setAttribute("lang", "en_US");
            }
        }
        chain.doFilter(request, response);
    }
}
