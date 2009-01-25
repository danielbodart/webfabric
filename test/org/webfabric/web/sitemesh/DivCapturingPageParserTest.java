package org.webfabric.web.sitemesh;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.opensymphony.module.sitemesh.Page;

public class DivCapturingPageParserTest {
    @Test
    public void handlesNestedDivs() throws Exception{
        // setup
        String inner ="<div>inner</div>";
        String outer = "content<div id='inner'>" + inner + "</div>";
        String body = "<div><div id='outer'>" + outer + "</div></div>";
        String html = "<html><body>" + body + "</body></html>";
        DivCapturingPageParser pageParser = new DivCapturingPageParser();

        // execute
        Page page = pageParser.parse(html.toCharArray());

        // verify
        assertEquals(body, page.getBody());
        assertEquals(outer, page.getProperty("div.outer"));
        assertEquals(inner, page.getProperty("div.inner"));
    }

    @Test
    public void doesNotConsumeDivWhenExtracting() throws Exception{
        // setup
        String html = "<html><body><div id='target'>content</div></body></html>";
        DivCapturingPageParser pageParser = new DivCapturingPageParser();

        // execute
        Page page = pageParser.parse(html.toCharArray());

        // verify
        assertEquals("<div id='target'>content</div>", page.getBody());
    }

    @Test
    public void extractsDivsWithIds() throws Exception{
        // setup
        String html = "<html><body><div id='target'>content</div></body></html>";
        DivCapturingPageParser pageParser = new DivCapturingPageParser();

        // execute
        Page page = pageParser.parse(html.toCharArray());

        // verify
        assertEquals("content", page.getProperty("div.target"));
    }
}
