package org.webfabric.sitemesh2

import org.junit.Test
import org.junit.Assert.assertEquals

class CrossSiteRequestForgeryParserTest {
  @Test
  def capturesTheTokenAndTheAction() {
    // setup
    val html = "<html><body><form method='post' action='/blah'>content</form></body></html>";
    val token = "someToken"
    val tokenProvider = new TokenProvider{
      def getToken(path:String) = token
    }
    val pageParser = new CrossSiteRequestForgeryParser(tokenProvider)

    // execute
    val page = pageParser.parse(html);

    // verify
    assertEquals(token, page.getProperty("csrf.token:/blah"))
  }

  @Test
  def insertsHiddenTokenIntoFormsThatPost() {
    // setup
    var html = "<html><body><form method='post'>content</form></body></html>";
    val token = "someToken"
    val tokenProvider = new TokenProvider{
      def getToken(path:String) = token
    }
    val pageParser = new CrossSiteRequestForgeryParser(tokenProvider)

    // execute
    var page = pageParser.parse(html);

    // verify
    val hiddenInput = "<input type=\"hidden\" name=\"csrf.token\" value=\"" + token + "\"/>"
    assertEquals("<form method='post'>" + hiddenInput + "content</form>", page.getBody);
  }
}