package org.webfabric.sitemesh;

import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.RequestConstants;
import org.antlr.stringtemplate.StringTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StringTemplateDecoratorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringTemplate template = getTemplate(request);
        template.setAttribute("request", request);
        template.setAttribute("response", response);

        HTMLPage html = getPage(request);

        StringTemplateDecorator templateDecorator = new StringTemplateDecorator(template);
        templateDecorator.Decorate(html, response.getWriter());
    }

    private StringTemplate getTemplate(HttpServletRequest request) {
        return new StringTemplate("body is $body$");
    }

    private HTMLPage getPage(HttpServletRequest request) {
        return (HTMLPage) request.getAttribute(RequestConstants.PAGE);
    }
}
