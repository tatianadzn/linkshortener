package com.nordcodes.testassignment.linkshortener.config;

import com.nordcodes.testassignment.linkshortener.entity.User;
import com.nordcodes.testassignment.linkshortener.exceptions.TokenValidationException;
import com.nordcodes.testassignment.linkshortener.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CustomFilter extends GenericFilterBean {

    private static final String AUTHORIZATION_COOKIE = "Authorization";

    private final AuthenticationProvider authenticationProvider;
    private final AuthProperties authProperties;
    private final UserManager userManager;

    @Autowired
    public CustomFilter(final AuthenticationProvider authenticationProvider,
                        final AuthProperties authProperties,
                        final UserManager userManager) {
        this.authenticationProvider = authenticationProvider;
        this.authProperties = authProperties;
        this.userManager = userManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        final User user = getUser(request);
        if (user == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = getTokenFromRequest(request);

        if (token != null) {
            try {
                authenticationProvider.validateToken(token, request.getParameterMap(), user.getSecretKey());
            } catch (NoSuchAlgorithmException | TokenValidationException ex) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            Authentication auth =
                    new CustomAuthToken(user.getUsername(), new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private User getUser(HttpServletRequest request) {
        try {
            final long userId = Long.parseLong(request.getParameter("id"));
            return userManager.loadUserById(userId);
        } catch (NumberFormatException | NullPointerException ex) {
            return null;
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals(AUTHORIZATION_COOKIE))
                        .findFirst())
                .map(Cookie::getValue)
                .orElse(request.getHeader(HttpHeaders.AUTHORIZATION));

        final String prefix = authProperties.getAuthHeaderPrefix().trim();
        if (StringUtils.hasText(bearer) && bearer.startsWith(prefix + " ")) {
            return bearer.substring(prefix.length() + 1);
        }

        return null;
    }
}
