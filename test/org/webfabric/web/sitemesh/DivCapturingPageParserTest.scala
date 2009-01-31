package org.webfabric.web.sitemesh

import org.junit.Test;
import org.junit.Assert.assertEquals;
import com.opensymphony.module.sitemesh.Page;

class DivCapturingPageParserTest {
  @Test
  def handlesNestedDivs() {
    // setup
    var inner = "<div>inner</div>";
    var outer = "content<div id='inner'>" + inner + "</div>";
    var body = "<div><div id='outer'>" + outer + "</div></div>";
    var html = "<html><body>" + body + "</body></html>";
    var pageParser = new DivCapturingPageParser();

    // execute
    var page = pageParser.parse(html.toCharArray());

    // verify
    assertEquals(body, page.getBody());
    assertEquals(outer, page.getProperty("div.outer"));
    assertEquals(inner, page.getProperty("div.inner"));
  }

  @Test
  def doesNotConsumeDivWhenExtracting() {
    // setup
    var html = "<html><body><div id='target'>content</div></body></html>";
    var pageParser = new DivCapturingPageParser();

    // execute
    var page = pageParser.parse(html.toCharArray());

    // verify
    assertEquals("<div id='target'>content</div>", page.getBody());
  }

  @Test
  def extractsDivsWithIds() {
    // setup
    var html = "<html><body><div id='target'>content</div></body></html>";
    var pageParser = new DivCapturingPageParser();

    // execute
    var page = pageParser.parse(html.toCharArray());

    // verify
    assertEquals("content", page.getProperty("div.target"));
  }
}
