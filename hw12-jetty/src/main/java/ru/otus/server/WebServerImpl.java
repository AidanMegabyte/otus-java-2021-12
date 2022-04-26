package ru.otus.server;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.service.TemplateProcessor;
import ru.otus.servlet.ClientServlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Страница клиентов
// http://localhost:8080/clients

public class WebServerImpl implements WebServer {

    private static final String ROLE_NAME_USER = "user";

    private static final String ROLE_NAME_ADMIN = "admin";

    private static final String CONSTRAINT_NAME = "auth";

    private final LoginService loginService;

    private final DBServiceClient clientService;

    private final TemplateProcessor templateProcessor;

    private final Server server;

    public WebServerImpl(int port,
                         LoginService loginService,
                         DBServiceClient clientService,
                         TemplateProcessor templateProcessor) {
        this.loginService = loginService;
        this.clientService = clientService;
        this.templateProcessor = templateProcessor;
        server = new Server(port);
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {

        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/clients"));

        server.setHandler(handlers);

        return server;
    }

    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {

        Constraint constraint = new Constraint();
        constraint.setName(CONSTRAINT_NAME);
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{ROLE_NAME_USER, ROLE_NAME_ADMIN});

        List<ConstraintMapping> constraintMappings = new ArrayList<>();
        Arrays.stream(paths).forEachOrdered(path -> {
            ConstraintMapping mapping = new ConstraintMapping();
            mapping.setPathSpec(path);
            mapping.setConstraint(constraint);
            constraintMappings.add(mapping);
        });

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        security.setAuthenticator(new BasicAuthenticator());

        security.setLoginService(loginService);
        security.setConstraintMappings(constraintMappings);
        security.setHandler(new HandlerList(servletContextHandler));

        return security;
    }

    private ResourceHandler createResourceHandler() {

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);

        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(
                new ServletHolder(new ClientServlet(clientService, templateProcessor, ROLE_NAME_ADMIN)),
                "/clients"
        );

        return servletContextHandler;
    }
}
