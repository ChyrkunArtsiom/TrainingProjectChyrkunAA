package by.chyrkun.training.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(filterName = "encodingFilter", urlPatterns = "/*", initParams = {@WebInitParam(name = "encoding", value = "UTF-8")})
public class EncodingFilter implements Filter {
    private String code;
    /*
    static class FilteredRequest extends HttpServletRequestWrapper {
        private String[] dangerousParams = {"review", "css"};

        FilteredRequest(ServletRequest request) {
            super((HttpServletRequest)request);
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);
            if (value != null && Arrays.stream(dangerousParams).anyMatch(name::equals)) {
                value = sanitize(value);
            }
            return value;
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] values = super.getParameterValues(name);
            if (values != null && Arrays.stream(dangerousParams).anyMatch(name::equals)) {
                for (int index = 0; index < values.length; index++) {
                    values[index] = sanitize(values[index]);
                }
            }
            return values;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> map = new HashMap<>(super.getParameterMap());
            map.forEach((k, v) -> {
                if (v != null && Arrays.stream(dangerousParams).anyMatch(k::equals)) {
                    for (int index = 0; index < v.length; index++) {
                        v[index] = sanitize(v[index]);
                    }
                }
            });
            return Collections.unmodifiableMap(map);
        }

        @Override
        public Object getAttribute(String name) {
            Object value = super.getAttribute(name);
            if (value != null && Arrays.stream(dangerousParams).anyMatch(name::equals)) {
                value = sanitize(value.toString());
            }
            return value;
        }


        private String sanitize(String input) {
            if (input != null) {
                input = input.replace("<", "&lt;");
                input = input.replace(">", "&gt;");
            }
            return input;
        }
    }
*/


    @Override
    public void init(FilterConfig filterConfig) {
        code = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();
        if (!code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }
}
