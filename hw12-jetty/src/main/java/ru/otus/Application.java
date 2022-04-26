package ru.otus;

import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.server.WebServer;
import ru.otus.server.WebServerImpl;
import ru.otus.service.TemplateProcessorImpl;

public class Application {

    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    private static final int WEB_SERVER_PORT = 8080;

    private static final String TEMPLATES_DIR = "/pages/";

    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";

    private static final String REALM_NAME = "AnyRealm";

    public static void main(String[] args) throws Exception {

        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(
                configuration,
                Client.class, Address.class, Phone.class
        );
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);

        WebServer webServer = new WebServerImpl(
                WEB_SERVER_PORT,
                loginService,
                dbServiceClient,
                new TemplateProcessorImpl(TEMPLATES_DIR)
        );

        webServer.start();
        webServer.join();
    }
}
