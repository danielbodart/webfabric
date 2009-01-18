package org.webfabric.sitemesh;

import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.RequestConstants;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

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
        template.setAttribute("request", request);
        template.setAttribute("response", response);

        HTMLPage html = getPage(request);

        StringTemplateDecorator templateDecorator = new StringTemplateDecorator(template);
        templateDecorator.Decorate(html, response.getWriter());
    }

    private StringTemplate getTemplate(final HttpServletRequest request) {
        List<Path> possiblePaths = list(new OriginalServletPath(request),
                new OriginalPathInfo(request),
                new ServletPath(request),
                new PathInfo(request));

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
