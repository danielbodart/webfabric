package org.webfabric.sitemesh;

import com.opensymphony.module.sitemesh.HTMLPage;
import org.antlr.stringtemplate.StringTemplate;

public class StringTemplateDecorator {
    private final StringTemplate template;

    public StringTemplateDecorator(StringTemplate template) {
        this.template = template;
    }

    public String Decorate(HTMLPage html) {
        template.setAttribute("head", html.getHead());
        template.setAttribute("title", html.getTitle());
        template.setAttribute("body", html.getBody());
        return template.toString();
    }
}
