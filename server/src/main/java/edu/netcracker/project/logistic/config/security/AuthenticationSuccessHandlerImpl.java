package edu.netcracker.project.logistic.config.security;

import edu.netcracker.project.logistic.controllers.RegistrationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Configuration
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()){
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isEmployee = false;
        Collection<? extends GrantedAuthority> authorities =
                authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities){

            String authority = grantedAuthority.getAuthority();

            if (authority.equals("ROLE_USER")){
                isUser = true;
                break;
            } else if (authority.equals("ROLE_ADMIN") || authority.equals("ROLE_MANAGER") ||
                    authority.equals("ROLE_COURIER") || authority.equals("ROLE_CALL_CENTER_AGENT")){
                isEmployee = true;
                break;
            }
        }

        if (isUser) {
            return "user";
        } else if (isEmployee) {
            return "employee";
        } else
            throw new IllegalStateException();
        }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null){
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
