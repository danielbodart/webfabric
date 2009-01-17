package org.webfabric.sitemesh;

import com.opensymphony.module.sitemesh.HTMLPage;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.NoIndentWriter;

import java.io.Writer;
import java.io.IOException;

public class StringTemplateDecorator {
    private final StringTemplate template;

    public StringTemplateDecorator(StringTemplate template) {
        this.template = template;
    }

    public void Decorate(HTMLPage html, Writer writer) throws IOException {
        template.setAttribute("page", html);
        template.setAttribute("head", html.getHead());
        template.setAttribute("title", html.getTitle());
        template.setAttribute("body", html.getBody());
        template.write(new NoIndentWriter(writer));
    }
}
