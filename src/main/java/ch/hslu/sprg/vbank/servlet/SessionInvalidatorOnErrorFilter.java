package ch.hslu.sprg.vbank.servlet;

import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@Component
//@Order(1)
public class SessionInvalidatorOnErrorFilter implements Filter {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SessionInvalidatorOnErrorFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            log.warn("There seems to have been an exception; invalidating session : " + e.getMessage());
            HttpSession session = ((HttpServletRequest) request).getSession(false);
            if (session != null) {
                session.invalidate();
            }
            throw e;
        }
    }

}
