package org.webfabric.sitemesh;

import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.parser.HTMLPageParser;
import org.antlr.stringtemplate.StringTemplate;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class StringTemplateDecoratorTest {
    @Test
    public void SupportsPage() throws Exception{
        // setup
        HTMLPage html = createPage("");
        StringTemplate template = new StringTemplate("$page$");
        StringTemplateDecorator decorator = new StringTemplateDecorator(template);

        // execute
        String result = decorator.Decorate(html);

        // verify
        Assert.assertTrue(result.contains("HTMLPage"));
    }

    @Test
    public void SupportsHeadTag() throws Exception{
        // setup
        HTMLPage html = createPage("<html><head><script/></head></html>");
        StringTemplate template = new StringTemplate("$head$");
        StringTemplateDecorator decorator = new StringTemplateDecorator(template);

        // execute
        String result = decorator.Decorate(html);

        // verify
        Assert.assertEquals("<script/>", result);
    }

    @Test
    public void SupportsBodyTag() throws Exception{
        // setup
        HTMLPage html = createPage("<html><body>Some text</body></html>");
        StringTemplate template = new StringTemplate("$body$");
        StringTemplateDecorator decorator = new StringTemplateDecorator(template);

        // execute
        String result = decorator.Decorate(html);

        // verify
        Assert.assertEquals("Some text", result);
    }

    @Test
    public void SupportsTitleTag() throws Exception{
        // setup
        HTMLPage html = createPage("<html><head><title>Some title</title></head></html>");
        StringTemplate template = new StringTemplate("$title$");
        StringTemplateDecorator decorator = new StringTemplateDecorator(template);

        // execute
        String result = decorator.Decorate(html);

        // verify
        Assert.assertEquals("Some title", result);
    }

    private HTMLPage createPage(String html) throws IOException {
        HTMLPageParser pageParser = new HTMLPageParser();
        return (HTMLPage) pageParser.parse(html.toCharArray());
    }
}
