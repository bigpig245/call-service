package com.example.demo.config;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestTrackingFilter extends OncePerRequestFilter {
  private static final String TRACE_ID = "call-request-trace-id";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    ExecutionContextHolder.startRequest();
    String requestId = ExecutionContextHolder.getCurrentRequestId();
    response.addHeader(TRACE_ID, requestId);
    try {
      filterChain.doFilter(request, response);
    } finally {
      ExecutionContextHolder.endRequest();
    }
  }
}
