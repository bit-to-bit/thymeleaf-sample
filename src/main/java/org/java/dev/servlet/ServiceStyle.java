package org.java.dev.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.java.dev.util.StaticResources;

import java.io.IOException;

@Slf4j
@WebServlet("/styles/*")
public class ServiceStyle extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        String message = StaticResources.getCss(request.getPathInfo());
        resp.setContentType("text/css; charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(message);
        resp.getWriter().close();
    }
}
