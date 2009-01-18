package org.webfabric.web.sitemesh;

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
        template.setAttribute("head", html != null ? html.getHead() : null );
        template.setAttribute("title", html != null ? html.getTitle() : null);
        template.setAttribute("body", html != null ? html.getBody() : null);
        template.write(new NoIndentWriter(writer));
    }
}
