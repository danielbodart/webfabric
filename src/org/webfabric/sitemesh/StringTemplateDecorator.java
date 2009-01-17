package org.webfabric.sitemesh;

import org.antlr.stringtemplate.StringTemplate;
import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.parser.HTMLPageParser;

import java.io.IOException;

public class StringTemplateDecorator {
    private final StringTemplate template;

    public StringTemplateDecorator(StringTemplate template) {
        this.template = template;
    }

    public String Decorate(String html) {
        try {
            HTMLPageParser pageParser = new HTMLPageParser();
            Page page = pageParser.parse(html.toCharArray());
            template.setAttribute("body", page.getBody());
            return template.toString();
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
