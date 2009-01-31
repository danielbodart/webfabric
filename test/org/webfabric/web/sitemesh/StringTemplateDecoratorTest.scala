package org.webfabric.web.sitemesh

import antlr.stringtemplate.StringTemplate
import java.io.StringWriter
import com.opensymphony.module.sitemesh.parser.HTMLPageParser
import com.opensymphony.module.sitemesh.HTMLPage
import org.junit.Test
import org.junit.Assert.assertEquals

class StringTemplateDecoratorTest {
  @Test
  def HandlesNullPages = {
    // setup
    var template = new StringTemplate("$head$")
    var decorator = new StringTemplateDecorator(template)

    // execute
    var result = GetResult(decorator, null)

    // verify
    assertEquals("", result)
  }

  @Test
  def SupportsPage = {
    // setup
    var html = createPage("")
    var template = new StringTemplate("$page$")
    var decorator = new StringTemplateDecorator(template)

    // execute
    var result = GetResult(decorator, html)

    // verify
    assertEquals(html.toString(), result)
  }

  @Test
  def SupportsHeadTag = {
    // setup
    var html = createPage("<html><head><script/></head></html>")
    var template = new StringTemplate("$head$")
    var decorator = new StringTemplateDecorator(template)

    // execute
    var result = GetResult(decorator, html)

    // verify
    assertEquals("<script/>", result)
  }

  @Test
  def SupportsBodyTag = {
    // setup
    var html = createPage("<html><body>Some text</body></html>")
    var template = new StringTemplate("$body$")
    var decorator = new StringTemplateDecorator(template)

    // execute
    var result = GetResult(decorator, html)

    // verify
    assertEquals("Some text", result)
  }

  @Test
  def SupportsTitleTag = {
    // setup
    var html = createPage("<html><head><title>Some title</title></head></html>")
    var template = new StringTemplate("$title$")
    var decorator = new StringTemplateDecorator(template)

    // execute
    var result = GetResult(decorator, html)

    // verify
    assertEquals("Some title", result)
  }

  def GetResult(decorator: StringTemplateDecorator, html: HTMLPage): String = {
    var writer = new StringWriter()
    decorator.Decorate(html, writer)
    writer.toString()
  }

  def createPage(html: String): HTMLPage = {
    var pageParser = new HTMLPageParser()
    pageParser.parse(html.toCharArray()).asInstanceOf[HTMLPage]
  }
}
