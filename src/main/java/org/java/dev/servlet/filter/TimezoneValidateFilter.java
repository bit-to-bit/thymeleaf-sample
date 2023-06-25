package org.java.dev.servlet.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.java.dev.util.ParameterHandler;

import java.io.IOException;

@WebFilter
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String timezone = request.getParameter("timezone");
        if (request.getParameterMap().size() > 0 && !ParameterHandler.isValidTimezone(timezone)) {
            sendInvalidParameter(response);
        } else {
            chain.doFilter(request, response);
        }
    }
    private static void sendInvalidParameter(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("Invalid timezone");
        response.getWriter().close();
    }
}