package org.webfabric.sitemesh;

import org.junit.Test;
import org.junit.Assert;
import org.antlr.stringtemplate.StringTemplate;

public class StringTemplateDecoratorTest {
    @Test
    public void SupportsBodyTag() throws Exception{
        // setup
        String html = "<html><body>Test</body></html>";
        StringTemplate template = new StringTemplate("$body$");
        StringTemplateDecorator decorator = new StringTemplateDecorator(template);

        // execute
        String result = decorator.Decorate(html);

        // verify
        Assert.assertEquals("Test", result);

    }
}
