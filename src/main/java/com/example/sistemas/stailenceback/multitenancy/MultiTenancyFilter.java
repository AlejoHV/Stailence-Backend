package com.example.sistemas.stailenceback.multitenancy;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MultiTenancyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader("X-Business-Id");
            if (!StringUtils.hasText(header)) {
                String param = request.getParameter("businessId");
                header = param;
            }
            if (StringUtils.hasText(header)) {
                try {
                    Long id = Long.parseLong(header);
                    TenantContext.setCurrentTenant(id);
                } catch (NumberFormatException ex) {
                    // ignore invalid
                }
            }
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}

