package ru.otus.servlet;

import org.eclipse.jetty.util.StringUtil;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.service.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static org.eclipse.jetty.http.MimeTypes.Type.TEXT_HTML;

public class ClientServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.ftl";

    private final DBServiceClient clientService;

    private final TemplateProcessor templateProcessor;

    private final String adminRoleName;

    public ClientServlet(DBServiceClient clientService, TemplateProcessor templateProcessor, String adminRoleName) {
        this.clientService = clientService;
        this.templateProcessor = templateProcessor;
        this.adminRoleName = adminRoleName;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType(TEXT_HTML.asString());

        var data = new HashMap<String, Object>();
        data.put("isAdmin", request.isUserInRole(adminRoleName));
        data.put("clients", clientService.findAll());

        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, data));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType(TEXT_HTML.asString());

        if (request.isUserInRole(adminRoleName)) {

            var name = request.getParameter("name");
            var street = request.getParameter("address");
            var phones = Arrays.stream(request.getParameterMap().get("phones"))
                    .filter(phone -> !StringUtil.isBlank(phone))
                    .toList();

            var client = new Client(
                    null,
                    name,
                    new Address(null, street),
                    phones.stream().map(phone -> new Phone(null, phone)).toList()
            );
            clientService.saveClient(client);
        }

        response.sendRedirect("/clients");
    }
}
