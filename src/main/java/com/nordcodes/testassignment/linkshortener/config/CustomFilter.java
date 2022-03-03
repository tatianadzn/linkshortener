package com.nordcodes.testassignment.linkshortener.config;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class CustomFilter extends GenericFilterBean {

    private static final String AUTHORIZATION_COOKIE = "Authorization";

    private final AuthenticationProvider authenticationProvider;
    private final AuthProperties authProperties;

    @Autowired
    public CustomFilter(final AuthenticationProvider authenticationProvider,
                        final AuthProperties authProperties) {
        this.authenticationProvider = authenticationProvider;
        this.authProperties = authProperties;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

//        System.out.println(request.getParameterMap().keySet());
//        for (String[] arr : request.getParameterMap().values()) {
//            System.out.println(Arrays.toString(arr));
//        }


        if (!request.getServletPath().contains("/h2-console")) {
            String token = getTokenFromRequest(request);
            if (token != null) {
                authenticationProvider.validateToken(token, request.getParameterMap());

                Authentication auth =
                        new CustomAuthToken("usersname", new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
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
