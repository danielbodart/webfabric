package org.webfabric.sitemesh.v2

import org.junit.Test
import org.webfabric.sitemesh.PropertyMap;
import org.junit.Assert.assertEquals;

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
    var page:PropertyMap = pageParser.parse(html);

    // verify
    assertEquals(body, page.get("body"));
    assertEquals(outer, page.get("div.outer"));
    assertEquals(inner, page.get("div.inner"));
  }

  @Test
  def doesNotConsumeDivWhenExtracting() {
    // setup
    var html = "<html><body><div id='target'>content</div></body></html>";
    var pageParser = new DivCapturingPageParser();

    // execute
    var page = pageParser.parse(html);

    // verify
    assertEquals("<div id='target'>content</div>", page.get("body"));
  }

  @Test
  def extractsDivsWithIds() {
    // setup
    var html = "<html><body><div id='target'>content</div></body></html>";
    var pageParser = new DivCapturingPageParser();

    // execute
    var page = pageParser.parse(html);

    // verify
    assertEquals("content", page.get("div.target"));
  }
}
