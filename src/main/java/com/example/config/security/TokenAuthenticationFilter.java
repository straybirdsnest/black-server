package com.example.config.security;

import com.example.services.TokenService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义的 Token 验证过滤器
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    public static final String TOKEN_HEADER = "X-Token";
    private final TokenService tokenService;

    public TokenAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getHeader(TOKEN_HEADER);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        UserAuthentication auth = (UserAuthentication)tokenService.getObject(token);
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }
}
