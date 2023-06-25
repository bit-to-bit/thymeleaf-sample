package org.java.dev.configuration;

import jakarta.servlet.ServletConfig;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

public class ThymeleafConfiguration {
    public static TemplateEngine setup(ServletConfig config) {
        TemplateEngine engine = new TemplateEngine();
        JakartaServletWebApplication app = JakartaServletWebApplication.buildApplication(config.getServletContext());
        WebApplicationTemplateResolver resolver = new WebApplicationTemplateResolver(app);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
        return engine;
    }
}
