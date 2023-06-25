package org.java.dev.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.java.dev.util.StaticResources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@WebServlet("/images/*")
public class ServiceImage extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/gif");
        response.setStatus(HttpServletResponse.SC_OK);
        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = StaticResources.getImage(request.getPathInfo());
        try {
            copy(inputStream, outputStream);
        } finally {
            inputStream.close();
        }
        outputStream.close();
        response.getWriter().close();
    }

    private static void copy(final InputStream in, final OutputStream out) {
        byte[] buffer = new byte[1024];
        int count;
        try {
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
