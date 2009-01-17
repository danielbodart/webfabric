package org.webfabric.sitemesh;

import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.parser.HTMLPageParser;
import org.antlr.stringtemplate.StringTemplate;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

public class StringTemplateDecoratorTest {
    @Test
    public void SupportsPage() throws Exception{
        // setup
        HTMLPage html = createPage("");
        StringTemplate template = new StringTemplate("$page$");
        StringTemplateDecorator decorator = new StringTemplateDecorator(template);

        // execute
        String result = GetResult(decorator, html);

        // verify
        Assert.assertEquals(html.toString(), result);
    }

    @Test
    public void SupportsHeadTag() throws Exception{
        // setup
        HTMLPage html = createPage("<html><head><script/></head></html>");
        StringTemplate template = new StringTemplate("$head$");
        StringTemplateDecorator decorator = new StringTemplateDecorator(template);

        // execute
        String result = GetResult(decorator, html);

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
        String result = GetResult(decorator, html);

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
        String result = GetResult(decorator, html);

        // verify
        Assert.assertEquals("Some title", result);
    }

    private String GetResult(StringTemplateDecorator decorator, HTMLPage html) throws IOException {
        StringWriter writer = new StringWriter();
        decorator.Decorate(html, writer);
        return writer.toString();
    }

    private HTMLPage createPage(String html) throws IOException {
        HTMLPageParser pageParser = new HTMLPageParser();
        return (HTMLPage) pageParser.parse(html.toCharArray());
    }
}
