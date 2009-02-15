package org.webfabric.web.sitemesh

import _root_.org.webfabric.web.servlet.{QueryString, ContextPath}
import antlr.stringtemplate.StringTemplate
import java.io.StringWriter
import com.opensymphony.module.sitemesh.parser.HTMLPageParser
import com.opensymphony.module.sitemesh.HTMLPage
import org.junit.Test
import org.junit.Assert.assertEquals

class StringTemplateDecoratorTest {
  @Test
  def supportsBase(): Unit = {
    // setup
    var base = new ContextPath("/foo/")
    var template = new StringTemplate("$base$")
    var decorator = new StringTemplateDecorator(template)

    // execute
    var result = decorator.setBase(base).toString

    // verify
    assertEquals(base.toString(), result)
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
    decorator.setPage(html).toString()
  }

  def createPage(html: String): HTMLPage = {
    var pageParser = new DivCapturingPageParser()
    pageParser.parse(html)
  }
}
