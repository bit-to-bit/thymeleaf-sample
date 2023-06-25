package org.java.dev.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.java.dev.configuration.AppProperties;
import org.java.dev.configuration.LoggingConfiguration;
import org.java.dev.configuration.ThymeleafConfiguration;
import org.java.dev.util.ParameterHandler;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.java.dev.util.Constants.THYMELEAF;

@Slf4j
@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;
    private static final String PATTERN_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void init(ServletConfig config) throws ServletException {

        AppProperties env = AppProperties.load();
        new LoggingConfiguration().setup();

        AppProperties appProperties = AppProperties.load();
        engine = ThymeleafConfiguration.setup(config);

        config.getServletContext().setAttribute(THYMELEAF, engine);
        log.info("INIT TimeServlet");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryTime = null;
        String timezone = null;
        String cookieTimezone = getCookieTimezone(request);
        if (request.getParameterMap().size() == 0) {
            timezone  = (null == cookieTimezone) ? "UTC" : cookieTimezone;
        } else {
            timezone = ParameterHandler.formatInputTimezone(request.getParameter("timezone"));
        }
        queryTime = getRequestTime(timezone);

        response.setContentType("text/html");
        response.addCookie(new Cookie("timezone", timezone));

        Map<String, Object> params = new LinkedHashMap<>();
        params.put("time", queryTime);

        Context simpleContext = new Context(
                request.getLocale(),
                Map.of("timeParams", params)
        );

        engine.process("time", simpleContext, response.getWriter());
        response.getWriter().close();
        log.info("doGet TimeServlet");
    }
    private static String getCookieTimezone(HttpServletRequest request){
        String timezone = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("timezone")){
                timezone = cookie.getValue();
            }
        }
        return timezone;
    }
    private static String getRequestTime(String timezone) {
        String result = "";
        String formattedTimezone = ParameterHandler.formatInputTimezone(timezone);
        Instant instant = Instant.now();
        try {
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern(PATTERN_FORMAT)
                    .withZone(ZoneId.of(formattedTimezone));
            result = formatter.format(instant) + " " + formattedTimezone;
        } catch (Exception е) {
            result = "You specified an incorrect time zone in the request: " + formattedTimezone;
        }
        return result;
    }
}