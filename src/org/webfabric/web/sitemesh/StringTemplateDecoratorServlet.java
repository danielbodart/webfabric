package org.webfabric.web.sitemesh;

import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.RequestConstants;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.webfabric.io.Path;
import org.webfabric.web.servlet.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import fj.data.List;
import static fj.data.List.list;
import fj.F;

public class StringTemplateDecoratorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringTemplate template = getTemplate(request);
        template.setAttribute("params", request.getParameterMap());
        template.setAttribute("base", ContextPath.create(request));

        HTMLPage html = getPage(request);

        StringTemplateDecorator templateDecorator = new StringTemplateDecorator(template);
        templateDecorator.Decorate(html, response.getWriter());
    }

    private StringTemplate getTemplate(final HttpServletRequest request) {
        List<Path> possiblePaths = list(OriginalServletPath.create(request),
                OriginalPathInfo.create(request),
                ServletPath.create(request),
                PathInfo.create(request));

        Path validPath = possiblePaths.dropWhile(new F<Path, Boolean>() {
            public Boolean f(Path path1) {
                return path1.value() == null;
            }
        }).head();
        String name = removeExtension(validPath.value());

        StringTemplateGroup groups = new StringTemplateGroup("decorators", getRootPath(), DefaultTemplateLexer.class);
        return groups.getInstanceOf(name);
    }

    private String getRootPath() {
        return getServletContext().getRealPath("");
    }

    private String removeExtension(String path) {
        return path.replace(".st", "");
    }

    private HTMLPage getPage(HttpServletRequest request) {
        return (HTMLPage) request.getAttribute(RequestConstants.PAGE);
    }

}
