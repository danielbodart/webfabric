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
        HTMLPage html = (HTMLPage) request.getAttribute(RequestConstants.PAGE);

        StringTemplate template = getTemplate(request);
        StringTemplateDecorator templateDecorator = new StringTemplateDecorator(template);
        String result = templateDecorator.Decorate(html);
        response.getWriter().write(result);
    }

    private StringTemplate getTemplate(HttpServletRequest request) {
        return new StringTemplate("body is $body$");
    }
}
