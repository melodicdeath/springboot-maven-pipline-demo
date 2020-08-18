package com.cptmcp.cicd.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ResponseHeaderWebFilter extends OncePerRequestFilter {
    @Value("${app.version}")
    private String version;
    @Value("${app.build.time}")
    private String buildTime;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.addHeader("version", version);
        httpServletResponse.addHeader("Build-Time", buildTime);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
