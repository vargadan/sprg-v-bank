package ch.hslu.sprg.vbank.filter;

import javax.servlet.*;
import javax.servlet.http.*;

@org.springframework.stereotype.Component
@org.springframework.core.annotation.Order(1)
public class CsrfFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {
        validate((HttpServletRequest) request, (HttpServletResponse) response);
        chain.doFilter(request, response);
        setToken((HttpServletRequest) request, (HttpServletResponse) response);
    }

    /**
     * Check the POST request if the container a valid _csrf parameter as csrf protection token
     *
     * @param request
     * @param response
     * @throws ServletException
     */
    private void validate(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if ("POST".equals(request.getMethod().toUpperCase())) {
            String csrfTokenIn = request.getParameter("csrfToken");
            String csrfTokenExp = (String) request.getSession().getAttribute("csrfTokenAttribute");
            if (!csrfTokenExp.equals(csrfTokenIn)) {
                throw new ServletException("Invalid CSRF token!");
            }
        }
    }

    /**
     * Sets the csrf token value as a session attribute with name 'csrfProtectionToken'
     *
     * @param request
     * @param response
     */
    private void setToken(HttpServletRequest request, HttpServletResponse response) {
        String csrfProtectionToken = (String) request.getSession(true).getAttribute("csrfTokenAttribute");
        if (csrfProtectionToken == null) {
            csrfProtectionToken = java.util.UUID.randomUUID().toString();
            request.getSession().setAttribute("csrfTokenAttribute", csrfProtectionToken);
        }
    }
}